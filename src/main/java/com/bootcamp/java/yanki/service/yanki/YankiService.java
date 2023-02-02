package com.bootcamp.java.yanki.service.yanki;

import com.bootcamp.java.yanki.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiService {

    public Flux<YankiResponseDTO> findAll();

    public Flux<YankiResponseDTO> findByDocumentNumber(String DocumentNumber);

    public Mono<YankiResponseDTO> findByCellPhoneNumber(String CellPhoneNumber);

    public Mono<YankiResponseDTO> create(YankiRequestDTO yankiRequestDTO);

}
