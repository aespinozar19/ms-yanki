package com.bootcamp.java.yanki.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"idMoneyType"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_MoneyType")
public class MoneyType {
    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private Integer idMoneyType;//1

    @NotNull
    private String description;//SOL || BOTCOINT
}
