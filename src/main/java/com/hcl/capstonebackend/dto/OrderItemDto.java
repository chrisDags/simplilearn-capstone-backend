package com.hcl.capstonebackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    //private Long id;
    private Long id;
    private String title;
    private Long quantity;
    private BigDecimal price;



}
