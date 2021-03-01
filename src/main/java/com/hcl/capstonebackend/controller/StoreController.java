package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.dto.TitleDto;
import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class StoreController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;

//    @Autowired
//    UserRepository userRepository;

    @GetMapping("/albums")
    public Iterable<Album> getAllAlbums(){
        return albumRepository.findAll();
    }

    @GetMapping("/albums/{name}")
    public Iterable<Album> getAlbumByTitle(@PathVariable String name){
        Iterable<Album> albums = albumRepository.findAll();

        List<Album> albums1 = new ArrayList<>();

        for(Album album : albums){
            if(album.getTitle().toLowerCase().contains(name.toLowerCase())){
                albums1.add(album);
            }
        }
        return (Iterable<Album>) albums1;
    }

    @PostMapping("/loginme")
    ResponseEntity<?> userLogin(@RequestBody UserDto userDto){

        System.out.println(userDto.getUsername());
        boolean result = userRepository.existsUsersByUsername(userDto.getUsername());

        if(result)
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/cart")
    public ResponseEntity<?> getAllInCart(){

        Optional<User> user = userRepository.findById(1l);

        User user1 = user.get();

        return ResponseEntity.ok(cartItemRepository.findAllByUser(user1));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> createAlbum(@RequestBody TitleDto album){


        Optional<User> user = userRepository.findById(1l);

        User user1 = user.get();

        Album myAlbum = albumRepository.findByTitle(album.getTitle());

        System.out.println(myAlbum);


        CartItem cartItem = CartItem.builder()
                .user(user1)
                .album(myAlbum)
                .build();
        cartItemRepository.save(cartItem);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
