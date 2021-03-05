package com.hcl.capstonebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDto {
    private String name;
    private String creditCardName;
    private String creditCard;
    private int cvv;
    //turn this into a Date
    private String expirationDate;
    private String billingAddress;
    private String shippingAddress;
    private BigDecimal total;
    private List<String> albumNames;
}
