package com.bootcamp.java.yanki.repository;

import com.bootcamp.java.yanki.entity.Yanki;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface YankiRepository extends ReactiveMongoRepository<Yanki,String> {
    Mono<Yanki> findById(String Id);

    Flux<Yanki> findByDocumentNumber(String DocumentNumber);

    Mono<Yanki> findByCellPhoneNumber(String CellPhoneNumber);

}
