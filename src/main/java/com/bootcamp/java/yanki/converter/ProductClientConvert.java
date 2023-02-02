package com.bootcamp.java.yanki.converter;

import com.bootcamp.java.yanki.common.Constantes;
import com.bootcamp.java.yanki.common.Funciones;
import com.bootcamp.java.yanki.dto.ProductClientDTO;
import com.bootcamp.java.yanki.dto.ProductClientRequest;
import com.bootcamp.java.yanki.dto.webClientDTO.ClientResponseDTO;
import com.bootcamp.java.yanki.dto.webClientDTO.ProductResponseDTO;
import com.bootcamp.java.yanki.entity.ProductClient;
import org.springframework.stereotype.Component;

@Component
public class ProductClientConvert {
    public static ProductClientDTO EntityToDTO(ProductClient productClient) {
        return ProductClientDTO.builder()
                .id(productClient.getId())
                .idProduct(productClient.getIdProduct())
                .productDescription(productClient.getProductDescription())
                .idProductType(productClient.getIdProductType())
                .productTypeDescription(productClient.getProductTypeDescription())
                .idProductSubType(productClient.getIdProductSubType())
                .productSubTypeDescription(productClient.getProductSubTypeDescription())
                .idClient(productClient.getIdClient())
                .idClientType(productClient.getIdClientType())
                .clientTypeDescription(productClient.getClientTypeDescription())
                .idClientDocumentType(productClient.getIdClientDocumentType())
                .clientDocumentTypeDescription(productClient.getClientDocumentTypeDescription())
                .documentNumber(productClient.getDocumentNumber())
                .fullName(productClient.getFullName())
                .authorizedSigners(productClient.getAuthorizedSigners())
                .creditLimit(productClient.getCreditLimit())
                .balance(productClient.getBalance())
                .debt(productClient.getDebt())
                .maintenanceCost(productClient.getMaintenanceCost())
                .movementLimit(productClient.getMovementLimit())
                .credits(productClient.getCredits())
                .accountNumber(productClient.getAccountNumber())
                .transactionFee(productClient.getTransactionFee())
                .creditCardNumber(productClient.getCreditCardNumber())
                .billingDay(productClient.getBillingDay())
                .billingDate(productClient.getBillingDate())
                .invoiceDebt(productClient.getInvoiceDebt())
                .expiredDebt(productClient.getExpiredDebt())
                .build();
    }

    public static ProductClient DTOToEntity2(ProductClientRequest productClientRequest,
                                            ProductResponseDTO productResponseDTO,
                                            ClientResponseDTO clientResponseDTO) {
        return ProductClient.builder()
                .idProduct(productResponseDTO.getIdProduct())
                .productDescription(productResponseDTO.getDescription())
                .idProductType(productResponseDTO.getProductTypeDTO().getIdProductType())
                .productTypeDescription(productResponseDTO.getProductTypeDTO().getDescription())
                .idProductSubType(productResponseDTO.getProductSubTypeDTO().getIdProductSubType())
                .productSubTypeDescription(productResponseDTO.getProductSubTypeDTO().getDescription())
                .idClient(clientResponseDTO.getIdClient())
                .idClientType(clientResponseDTO.getClientTypeDTO().getIdClientType())
                .clientTypeDescription(clientResponseDTO.getClientTypeDTO().getDescription())
                .idClientDocumentType(clientResponseDTO.getClientDocumentTypeDTO().getIdClientDocumentType())
                .clientDocumentTypeDescription(clientResponseDTO.getClientDocumentTypeDTO().getDescription())
                .documentNumber(clientResponseDTO.getDocumentNumber())
                .fullName(clientResponseDTO.getFullName())
                //.authorizedSigners(productClientDTO.getAuthorizedSigners())
                //.creditLimit(productClientDTO.getCreditLimit())
                .balance(productClientRequest.getDepositAmount())
                //.debt(productClientDTO.getDebt())
                .maintenanceCost(Constantes.MaintenanceCost)
                .movementLimit(productResponseDTO.getProductSubTypeDTO().getMovementLimit())
                //.credits(productClientDTO.getCredits())
                .accountNumber(productClientRequest.getAccountNumber())
                .transactionFee(productResponseDTO.getTransactionFee())
                //.creditCardNumber(productClient.getCreditCardNumber())
                .registerDate(Funciones.GetCurrentDate())
                .build();
    }

    public static ProductClient DTOToEntity(ProductClientDTO productClientDTO) {
        return ProductClient.builder()
                .idProduct(productClientDTO.getIdProduct())
                .productDescription(productClientDTO.getProductDescription())
                .idProductType(productClientDTO.getIdProductType())
                .productTypeDescription(productClientDTO.getProductTypeDescription())
                .idProductSubType(productClientDTO.getIdProductSubType())
                .productSubTypeDescription(productClientDTO.getProductSubTypeDescription())
                .idClient(productClientDTO.getIdClient())
                .idClientType(productClientDTO.getIdClientType())
                .clientTypeDescription(productClientDTO.getClientTypeDescription())
                .idClientDocumentType(productClientDTO.getIdClientDocumentType())
                .clientDocumentTypeDescription(productClientDTO.getClientDocumentTypeDescription())
                .documentNumber(productClientDTO.getDocumentNumber())
                .fullName(productClientDTO.getFullName())
                .authorizedSigners(productClientDTO.getAuthorizedSigners())
                .creditLimit(productClientDTO.getCreditLimit())
                .balance(productClientDTO.getBalance())
                .debt(productClientDTO.getDebt())
                .maintenanceCost(productClientDTO.getMaintenanceCost())
                .movementLimit(productClientDTO.getMovementLimit())
                .credits(productClientDTO.getCredits())
                .accountNumber(productClientDTO.getAccountNumber())
                .transactionFee(productClientDTO.getTransactionFee())
                .creditCardNumber(productClientDTO.getCreditCardNumber())
                .registerDate(Funciones.GetCurrentDate())
                .build();
    }


}
