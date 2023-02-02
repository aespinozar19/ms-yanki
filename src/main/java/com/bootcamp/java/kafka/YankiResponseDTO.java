package com.bootcamp.java.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YankiResponseDTO {
    private String id;
    private Integer idDocumentType;
    private String documentNumber;
    private String cellPhoneNumber;
    private String cellPhoneIMEI;
    private String emailAddress;
    private String resultDescription;
    private Integer result;
}