package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.Service.OrderService;
import com.hcl.capstonebackend.domain.Orders;
import com.hcl.capstonebackend.dto.OrderItemDto;
import com.hcl.capstonebackend.dto.OrdersDto;
import com.hcl.capstonebackend.security.AuthenticationRequest;
import com.hcl.capstonebackend.security.AuthenticationResponse;
import com.hcl.capstonebackend.security.JwtUtil;
import com.hcl.capstonebackend.security.UserDetailsServiceImpl;
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
    private OrderService orderService;



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

        boolean isUserAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> ((GrantedAuthority) role)
                .getAuthority().equals("ROLE_ADMIN"));

        String valueStr = String.valueOf(isUserAdmin);

        if(isUserAdmin)
            return new ResponseEntity<>(valueStr, HttpStatus.OK);


        return new ResponseEntity<>(valueStr, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody OrdersDto ordersDto, Principal principal){

        Orders orders = orderService.createNewOrder(ordersDto, principal);

        return new ResponseEntity<>(orders, HttpStatus.CREATED);

    }

    @PostMapping("/order/item")
    public ResponseEntity<?> createOrderItem(@RequestBody OrderItemDto orderItem, Principal principal){

        orderService.addOrderItem(orderItem, principal);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(){

        return new ResponseEntity<>(orderService.retrieveAllOrderItems(), HttpStatus.OK);
    }


}
