package com.bootcamp.java.yanki.kafka;

import com.bootcamp.java.kafka.YankiResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {

    @Autowired
    KafkaProducer kafkaProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.yankiresult.name:my_yanki_register_result}",
            groupId = "${spring.kafka.consumer.group-id:myGroup}")
    public void consumeYankiResponseDTOResult(YankiResponseDTO yankiResponseDTO){
        LOGGER.info(String.format("consumeYankiResponseDTOResult RECIEVED"));
        LOGGER.info(String.format("YankiResponseDTO message recieved -> %s", yankiResponseDTO.toString()));

        //productClientService.create(productClientDTO1).block();
        LOGGER.info(String.format("REGISTRO YANKI ACTUALIZADO"));
    }
}
