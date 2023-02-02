package com.bootcamp.java.yanki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"idYankiRequest"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_YankiRequest")
public class Yanki {

    @Id
    private String id;
    @NotNull
    private String idDocumentType;
    @NotNull
    private String documentNumber;
    @NotNull
    private String cellPhoneNumber;
    @NotNull
    private String cellPhoneIMEI;
    @NotNull
    private String emailAddress;

    @JsonIgnore
    private String resultDescription;

    @JsonIgnore
    private Integer result;

}
