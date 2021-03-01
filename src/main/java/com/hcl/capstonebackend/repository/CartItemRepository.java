package com.hcl.capstonebackend.repository;

import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    Iterable<CartItem> findAllByUser(User user);
}
