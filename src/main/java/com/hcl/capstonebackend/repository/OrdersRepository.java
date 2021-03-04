package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Long> {
}
