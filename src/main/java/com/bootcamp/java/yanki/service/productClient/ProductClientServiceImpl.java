package com.bootcamp.java.yanki.service.productClient;


import com.bootcamp.java.yanki.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.yanki.converter.KafkaConvert;
import com.bootcamp.java.yanki.converter.ProductClientConvert;
import com.bootcamp.java.yanki.converter.TransactionConvert;
import com.bootcamp.java.yanki.dto.ProductClientDTO;
import com.bootcamp.java.yanki.dto.ProductClientRequest;
import com.bootcamp.java.yanki.dto.ProductClientTransactionDTO;
import com.bootcamp.java.yanki.kafka.KafkaProducer;
import com.bootcamp.java.yanki.repository.ProductClientRepository;
import com.bootcamp.java.yanki.repository.TransactionRepository;
import com.bootcamp.java.yanki.service.transaction.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductClientServiceImpl implements ProductClientService{


    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConvert kafkaConvert;

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionConvert transactionConvert;

    @Autowired
    ProductClientConvert productClientConvert;

    @Override
    public Flux<ProductClientDTO> findAll() {
        log.debug("findAll executing");
        Flux<ProductClientDTO> dataProductClientDTO = productClientRepository.findAll()
                .map(ProductClientConvert::EntityToDTO);
        return dataProductClientDTO;
    }

    @Override
    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber) {
        log.debug("findByDocumentNumber executing");
        Flux<ProductClientDTO> dataProductClientDTO = productClientRepository.findByDocumentNumber(DocumentNumber)
                .map(ProductClientConvert::EntityToDTO)
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros")));;
        return dataProductClientDTO;
    }

    @Override
    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber) {
        return productClientRepository.findByAccountNumber(AccountNumber)
                .map(ProductClientConvert::EntityToDTO)
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros")));
    }

    @Override
    public Mono<ProductClientTransactionDTO> create(ProductClientRequest productClientRequest) {
        log.info("Procedimiento para crear una nueva afiliacion");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(productClientRequest.toString());

        return null;
    }
}
