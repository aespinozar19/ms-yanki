package com.bootcamp.java.yanki.converter;

import com.bootcamp.java.yanki.dto.MoneyTypeDTO;
import com.bootcamp.java.yanki.entity.MoneyType;
import org.springframework.stereotype.Component;

@Component
public class MoneyTypeConvert {
    public static MoneyTypeDTO EntityToDTO(MoneyType transactionType) {
        return MoneyTypeDTO.builder()
                .id(transactionType.getId())
                .idMoneyType(transactionType.getIdMoneyType())
                .description(transactionType.getDescription())
                .build();
    }
}
