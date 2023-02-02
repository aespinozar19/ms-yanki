package com.bootcamp.java.yanki.service.transaction;

import com.bootcamp.java.yanki.common.Constantes;
import com.bootcamp.java.yanki.common.Funciones;
import com.bootcamp.java.yanki.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.yanki.converter.KafkaConvert;
import com.bootcamp.java.yanki.converter.ProductClientConvert;
import com.bootcamp.java.yanki.converter.TransactionConvert;
import com.bootcamp.java.yanki.dto.*;
import com.bootcamp.java.yanki.entity.ProductClient;
import com.bootcamp.java.yanki.entity.Transaction;
import com.bootcamp.java.yanki.kafka.KafkaProducer;
import com.bootcamp.java.yanki.repository.ProductClientRepository;
import com.bootcamp.java.yanki.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConvert kafkaConvert;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    TransactionConvert transactionConverter;

    @Autowired
    ProductClientConvert productClientConvert;


    @Autowired
    Constantes constantes;

    @Override
    public Mono<TransactionDTO> register(TransactionRequestDTO transactionRequestDTO) {
        return null;
    }

    @Override
    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO, String IdProductClient) {

        return transactionRepository.save(transactionConverter.DTOtoEntity(transactionDTO))
                .flatMap(trx -> {

                    //KAFKA Transaction
                    kafkaProducer.sendMessageTransaction(kafkaConvert.TransactionEntityToDTOKafka(trx));
                    log.info("KAFKA sendMessageTransaction");

                    return productClientRepository.findById(IdProductClient)
                            .flatMap(productClient -> {
                                productClient.setBalance(CalculateBalance(productClient.getBalance(),
                                        transactionDTO.getMont(),
                                        Constantes.TipoTrxTransferenciaEntrada,0.0));

                                return productClientRepository.save(productClient)
                                        .flatMap(prdcli -> {

                                            //KAFKA ActualizarSaldo ProductClient
                                            kafkaProducer.sendMessageProductClient(kafkaConvert.ProductClientEntityToDTOKafka(prdcli));
                                            log.info("KAFKA sendMessageProductClient");

                                            return Mono.just(transactionConverter.EntityToDTO(trx));
                                        });
                            })
                            .switchIfEmpty(Mono.error(() -> new FunctionalException("Error, No se encontro producto")));
                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("Error al registrar la trx de entrada")));
    }

    public Mono<TransactionDTO> registerTrxEntrada(ProductClient productClient, Transaction transactionOrigen){
        transactionOrigen.setId(null);
        transactionOrigen.setIdProductClient(productClient.getId());
        transactionOrigen.setIdTransactionType(Constantes.TipoTrxTransferenciaEntrada);
        transactionOrigen.setTransactionFee(0.00);

        return transactionRepository.save(transactionOrigen)
                .flatMap(x -> {
                    productClient.setBalance(CalculateBalance(productClient.getBalance(),
                            transactionOrigen.getMont(),
                            transactionOrigen.getIdTransactionType(),
                            transactionOrigen.getTransactionFee()));

                    //KAFKA Transaction
                    kafkaProducer.sendMessageTransaction(kafkaConvert.TransactionEntityToDTOKafka(x));
                    log.info("KAFKA sendMessageTransaction");

                    return productClientRepository.save(productClient)
                            .flatMap(pc -> {

                                //KAFKA ActualizarSaldo ProductClient
                                kafkaProducer.sendMessageProductClient(kafkaConvert.ProductClientEntityToDTOKafka(pc));
                                log.info("KAFKA sendMessageProductClient");

                                return Mono.just(transactionConverter.EntityToDTO(x));
                            });
                });
    }

    @Override
    public Flux<ProductClientReportDTO> findByDocumentNumber(String documentNumber) {
        return productClientRepository.findByDocumentNumber(documentNumber)
                .flatMap(prodCli -> {
                    var data = transactionRepository.findByIdProductClient(prodCli.getId())
                            .collectList()
                            .map(transactions -> ProductClientReportDTO.from(prodCli, transactions));
                    return data;
                }).switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros de productos afiliados")));
    }

    @Override
    public Flux<TransactionDTO> findAll() {
        log.debug("findAll executing");
        Flux<TransactionDTO> dataTransactionDTO = transactionRepository.findAll()
                .map(TransactionConvert::EntityToDTO);
        return dataTransactionDTO;
    }

    private Double CalculateBalance(Double ActualBalance, Double amountTrx, Integer transactionType, Double trxFee) {
        Double newBalance = 0.00;
        if(transactionType.equals(Constantes.TipoTrxRetiro)) //retiro
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxDeposito)) //deposito
            newBalance = ActualBalance + amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxConsumo)) //deposito
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxTransferenciaSalida)) //Transferencia a cuenta externa
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxTransferenciaEntrada)) //Transferencia a cuenta externa
            newBalance = ActualBalance + amountTrx - trxFee;

        BigDecimal bd = new BigDecimal(newBalance).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
