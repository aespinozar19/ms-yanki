package com.bootcamp.java.yanki.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YankiRequestDTO {
    //private String id;
    private Integer idDocumentType;
    private String documentNumber;
    private String cellPhoneNumber;
    private String cellPhoneIMEI;
    private String emailAddress;
}
