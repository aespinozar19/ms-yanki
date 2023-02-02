package com.bootcamp.java.yanki.converter;

import com.bootcamp.java.yanki.dto.YankiRequestDTO;
import com.bootcamp.java.yanki.dto.YankiResponseDTO;
import com.bootcamp.java.yanki.entity.Yanki;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class YankiConvert {
    public static YankiResponseDTO EntityToDTO(Yanki yanki) {
        return YankiResponseDTO.builder()
                .id(yanki.getId())
                .idDocumentType(yanki.getIdDocumentType())
                .documentNumber(yanki.getDocumentNumber())
                .cellPhoneNumber(yanki.getCellPhoneNumber())
                .cellPhoneIMEI(yanki.getCellPhoneIMEI())
                .emailAddress(yanki.getEmailAddress())
                .result(yanki.getResult())
                .resultDescription(yanki.getResultDescription())
                .build();
    }

    public static Yanki DTOtoEntity(YankiRequestDTO yankiRequestDTO) {
        return Yanki.builder()
                .idDocumentType(yankiRequestDTO.getIdDocumentType())
                .documentNumber(yankiRequestDTO.getDocumentNumber())
                .cellPhoneNumber(yankiRequestDTO.getCellPhoneNumber())
                .cellPhoneIMEI(yankiRequestDTO.getCellPhoneIMEI())
                .emailAddress(yankiRequestDTO.getEmailAddress())
                .result(-1) //Espera de respuesta
                .resultDescription("Pendiente de resultado")
                .build();
    }
}
