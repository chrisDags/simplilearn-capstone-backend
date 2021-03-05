package com.hcl.capstonebackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcl.capstonebackend.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//todo
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creditCardName;
    @Column(name = "credit_card")
    private String creditCard;
    private int cvv;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "billing_address")
    private String billingAddress;
    @Column(name = "shipping_address")
    private String shippingAddress;

    @ElementCollection
    private List<String> albumNames;

    private BigDecimal total;


}
