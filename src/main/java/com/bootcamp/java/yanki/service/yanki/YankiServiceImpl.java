package com.bootcamp.java.yanki.service.yanki;


import com.bootcamp.java.yanki.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.yanki.converter.KafkaConvert;
import com.bootcamp.java.yanki.converter.YankiConvert;
import com.bootcamp.java.yanki.dto.*;
import com.bootcamp.java.yanki.entity.Yanki;
import com.bootcamp.java.yanki.kafka.KafkaProducer;
import com.bootcamp.java.yanki.repository.ProductClientRepository;
import com.bootcamp.java.yanki.repository.YankiRepository;
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
public class YankiServiceImpl implements YankiService {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConvert kafkaConvert;

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    YankiConvert yankiConvert;

    @Autowired
    private YankiRepository yankiRepository;

    @Override
    public Flux<YankiResponseDTO> findAll() {
        return yankiRepository.findAll()
                .map(x -> yankiConvert.EntityToDTO(x));

    }

    @Override
    public Flux<YankiResponseDTO> findByDocumentNumber(String DocumentNumber) {
        return yankiRepository.findByDocumentNumber(DocumentNumber)
                .map(x -> yankiConvert.EntityToDTO(x))
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encuentro registros Yanki")));
    }

    @Override
    public Mono<YankiResponseDTO> findByCellPhoneNumber(String CellPhoneNumber) {
        return yankiRepository.findByCellPhoneNumber(CellPhoneNumber)
                .map(x -> yankiConvert.EntityToDTO(x))
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encuentro registros Yanki")));
    }

    @Override
    public Mono<YankiResponseDTO> create(YankiRequestDTO yankiRequestDTO) {
        log.info("Procedimiento para crear una nueva afiliacion a Yanki monedero");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(yankiRequestDTO.toString());

        log.info("Valida si existe cuenta activa en yanki con el numero de celular");
        productClientRepository.findByCellPhoneNumber(yankiRequestDTO.getCellPhoneNumber())
                .thenReturn(Mono.error(new FunctionalException("Existe una cuenta activa con el numero de celular")));
        log.info("No existe cuenta activa, registra solicitud de afiliacion a Yanki");

        Yanki yankiEntity = yankiConvert.DTOtoEntity(yankiRequestDTO);



        return yankiRepository.save(yankiEntity)
                .flatMap(x -> {
                    YankiResponseDTO yankiResponseDTO = yankiConvert.EntityToDTO(x);
                    com.bootcamp.java.kafka.YankiResponseDTO YankiResponseDTOKafka = kafkaConvert.YankiResponseDTOToDTOKafka(yankiResponseDTO);
                    log.info("YankiResponseDTOKafka {}", YankiResponseDTOKafka);

                    kafkaProducer.sendMessageYankiResponseDTO(YankiResponseDTOKafka);

                    return Mono.just(yankiResponseDTO);
                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al guardar el registro Yanki")));
    }
}
