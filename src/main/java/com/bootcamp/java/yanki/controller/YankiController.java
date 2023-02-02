package com.bootcamp.java.yanki.controller;

import com.bootcamp.java.yanki.dto.*;
import com.bootcamp.java.yanki.service.productClient.ProductClientService;
import com.bootcamp.java.yanki.service.yanki.YankiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/yanki")
public class YankiController {

    @Autowired
    private ProductClientService productClientService;

    @Autowired
    private YankiService yankiService;

    @PostMapping
    public Mono<ResponseEntity<YankiResponseDTO>> registerAffiliation(@Valid @RequestBody YankiRequestDTO yankiRequestDTO) {
        log.info("create executed {}", yankiRequestDTO);
        return yankiService.create(yankiRequestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping()
    public Mono<ResponseEntity<Flux<YankiResponseDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(yankiService.findAll()));
    }

    @GetMapping("getByDocumentNumber/{documentNumber}")
    public Mono<ResponseEntity<Flux<YankiResponseDTO>>> getByDocumentNumber(@PathVariable String documentNumber){
        log.info("getByDocumentNumber executed {}", documentNumber);
        return Mono.just(ResponseEntity.ok()
                .body(yankiService.findByDocumentNumber(documentNumber)));
    }

    @GetMapping("getByCellPhoneNumber/{cellPhoneNumber}")
    public Mono<ResponseEntity<YankiResponseDTO>> getByCellPhoneNumber(@PathVariable String cellPhoneNumber){
        log.info("getByCellPhoneNumber executed {}", cellPhoneNumber);
        return yankiService.findByCellPhoneNumber(cellPhoneNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
