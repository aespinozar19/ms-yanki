package com.bootcamp.java.yanki.dto;

import com.bootcamp.java.yanki.entity.ProductClient;
import com.bootcamp.java.yanki.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductClientReportDTO {
    private ProductClient productClient;
    private List<Transaction> transactionList = new ArrayList<>();

    public static ProductClientReportDTO from(ProductClient prodCli, List<Transaction> transactionList) {
        return ProductClientReportDTO.builder()
                .productClient(prodCli)
                .transactionList(transactionList)
                .build();

    }
}
