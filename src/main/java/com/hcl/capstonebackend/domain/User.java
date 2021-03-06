package com.hcl.capstonebackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String roles;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
    private List<CartItem> cartItemList = new LinkedList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
    @JsonIgnore
    private List<Orders> ordersList = new LinkedList<>();
}


