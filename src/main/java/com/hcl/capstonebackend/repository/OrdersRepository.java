package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.Orders;
import com.hcl.capstonebackend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

    Orders findOrdersByUser(User user);
}
