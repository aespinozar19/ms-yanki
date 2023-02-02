package com.bootcamp.java.yanki.repository;

import com.bootcamp.java.yanki.entity.MoneyType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MoneyTypeRepository extends ReactiveMongoRepository<MoneyType, Integer> {
    Mono<MoneyType> findByIdMoneyType(Integer IdMoneyType);
}
