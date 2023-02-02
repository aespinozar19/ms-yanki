package com.bootcamp.java.yanki.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YankiResponseDTO {
    private String id;
    private String idDocumentType;
    private String documentNumber;
    private String cellPhoneNumber;
    private String cellPhoneIMEI;
    private String emailAddress;
    private String resultDescription;
    private Integer result;
}
