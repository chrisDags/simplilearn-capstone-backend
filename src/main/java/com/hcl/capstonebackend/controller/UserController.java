package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

//    @Autowired
//    UserRepository userRepository;

//    @PostMapping("/mylogin")
//    ResponseEntity<?> userLogin(@RequestBody UserDto userDto){
//
//    }

//    @PostMapping("/register")
//    ResponseEntity<?> userLogin(@RequestBody UserDto user){
//
//        System.out.println(user.getUsername());
//        boolean doesExist = userRepository.existsUsersByUsername(user.getUsername());
//
//        if(doesExist){
//            return new ResponseEntity<>(HttpStatus.ACCEPTED);
//        }
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }


}
