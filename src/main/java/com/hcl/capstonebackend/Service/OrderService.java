package com.hcl.capstonebackend.Service;

import com.hcl.capstonebackend.domain.OrderItem;
import com.hcl.capstonebackend.domain.Orders;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.OrderItemDto;
import com.hcl.capstonebackend.dto.OrdersDto;
import com.hcl.capstonebackend.repository.OrderItemRepository;
import com.hcl.capstonebackend.repository.OrdersRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    public void addOrderItem(OrderItemDto orderItem, Principal principal){

        Optional<User> user = userRepository.findByUsername(principal.getName());
        User user1 = user.get();


        Optional<Orders> orders = ordersRepository.findById(orderItem.getId());

        Orders orders1 = orders.get();


        OrderItem orderItem1 = OrderItem.builder()
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .title(orderItem.getTitle())
                .orders(orders1)
                .build();


        orderItemRepository.save(orderItem1);

    }

    public Iterable<OrderItem> retrieveAllOrderItems(){
        return  orderItemRepository.findAll();
    }

    public Orders createNewOrder(OrdersDto ordersDto, Principal principal){
        Optional<User> user = userRepository.findByUsername(principal.getName());

        User user1 = user.get();

        Orders orders = Orders.builder()
                .billingAddress(ordersDto.getBillingAddress())
                .creditCardName(ordersDto.getCreditCardName())
                .creditCard(ordersDto.getCreditCard())
                .cvv(ordersDto.getCvv())
                .expirationDate(ordersDto.getExpirationDate())
                .shippingAddress(ordersDto.getShippingAddress())
                .billingAddress(ordersDto.getBillingAddress())
                .total(ordersDto.getTotal())
                .user(user1)
//                .albumNames(ordersDto.getAlbumNames())
                .build();


        ordersRepository.save(orders);

        return orders;
    }
}
