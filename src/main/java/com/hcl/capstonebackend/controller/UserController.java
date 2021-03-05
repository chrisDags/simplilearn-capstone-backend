package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.domain.OrderItem;
import com.hcl.capstonebackend.domain.Orders;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.OrderItemDto;
import com.hcl.capstonebackend.dto.OrdersDto;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.OrderItemRepository;
import com.hcl.capstonebackend.repository.OrdersRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import com.hcl.capstonebackend.security.AuthenticationRequest;
import com.hcl.capstonebackend.security.AuthenticationResponse;
import com.hcl.capstonebackend.security.JwtUtil;
import com.hcl.capstonebackend.security.UserDetailsServiceImpl;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;



    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> retrieveRole(Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //System.out.println("NAME HERE: "+principal.getName());

        boolean isUserAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> ((GrantedAuthority) role)
                .getAuthority().equals("ROLE_ADMIN"));

        String valueStr = String.valueOf(isUserAdmin);

//        System.out.println(valueStr);

        if(isUserAdmin)
            return new ResponseEntity<>(valueStr, HttpStatus.OK);


        return new ResponseEntity<>(valueStr, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody OrdersDto ordersDto, Principal principal){


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

        System.out.println("order made");

        ordersRepository.save(orders);

        return new ResponseEntity<>(orders, HttpStatus.CREATED);

    }

    @PostMapping("/order/item")
    public ResponseEntity<?> createOrderItem(@RequestBody OrderItemDto orderItem, Principal principal){

//        System.out.println(orderItem.getOrders().getId());

        Optional<User> user = userRepository.findByUsername(principal.getName());
        User user1 = user.get();


        Optional<Orders> orders = ordersRepository.findById(orderItem.getId());

        Orders orders1 = orders.get();

        //Orders orders1 = orders.get();

        OrderItem orderItem1 = OrderItem.builder()
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .title(orderItem.getTitle())
                .orders(orders1)
                .build();

//        OrderItem orderItem1 = OrderItem.builder()
//                .quantity(orderItemDto.getOrderItem(.getQuantity())
//                .price(orderItemDto.getOrderItem().getPrice())
//                .title(orderItemDto.getOrderItem().getTitle())
//                .orders(orderItemDto.getOrders())
//                .build();
        System.out.println(orderItem1.toString());

        orderItemRepository.save(orderItem1);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(){

        return new ResponseEntity<>(ordersRepository.findAll(), HttpStatus.OK);
    }


}
