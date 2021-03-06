package com.hcl.capstonebackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
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

    private BigDecimal total;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "orders")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


}
