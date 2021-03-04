package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.AlbumDto;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
    todo:
     All of this is going to be refactored with all the business logic in their own services, URIs will be changed to
     start with api/v1/

 */

@CrossOrigin
@RestController
public class StoreController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;


    @GetMapping("/albums")
    public Iterable<Album> getAllAlbums() {
        return albumRepository.findAll();
    }


    //todo: either handle this on the frontend or find a cleaner way here
    @GetMapping("/albums/{name}")
    public Iterable<Album> getAlbumByTitle(@PathVariable String name) {
        Iterable<Album> albums = albumRepository.findAll();

        List<Album> albums1 = new ArrayList<>();

        for (Album album : albums) {
            if (album.getTitle().toLowerCase().contains(name.toLowerCase())) {
                albums1.add(album);
            }
        }
        return (Iterable<Album>) albums1;
    }

    @PostMapping("/loginme")
    ResponseEntity<?> userLogin(@RequestBody UserDto userDto) {

        System.out.println(userDto.getUsername());
        boolean result = userRepository.existsUsersByUsername(userDto.getUsername());

        if (result)
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getAllInCart(Principal principal) {

        System.out.println(principal.getName());

        Optional<User> user = userRepository.findByUsername(principal.getName());

        User user1 = user.get();

        return ResponseEntity.ok(cartItemRepository.findAllByUser(user1));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> createAlbumInCart(@RequestBody AlbumDto album, Principal principal) {


        System.out.println(principal.getName());
        System.out.println(album.getQuantity());


        Optional<User> user = userRepository.findByUsername(principal.getName());
        user.orElseThrow(() -> new UsernameNotFoundException("User not authenticated"));

        User user1 = user.get();

        Album myAlbum = albumRepository.findByTitle(album.getTitle());

        System.out.println(myAlbum);


        CartItem cartItem = CartItem.builder()
                .user(user1)
                .album(myAlbum)
                .quantity(album.getQuantity())
                .build();
        cartItemRepository.save(cartItem);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> deleteAlbumFromCart(@PathVariable Long id){

        cartItemRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/albums/test/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id){

        Optional<Album> album = albumRepository.findById(id);
        Album album1 = album.get();
        System.out.println(album1.getTitle());
        return new ResponseEntity<>(album1, HttpStatus.OK);
    }


//    @PostMapping("/cart")
//    public ResponseEntity<?> createAlbumInCart(@RequestBody AlbumDto albumDto, Principal principal) {
//
//
//        System.out.println(principal.getName());
//
//        Optional<User> user = userRepository.findByUsername(principal.getName());
//        user.orElseThrow(() -> new UsernameNotFoundException("User not authenticated"));
//
//        User user1 = user.get();
//
//        Album myAlbum = albumRepository.findByTitle(albumDto.getTitle());
//
//        System.out.println(myAlbum);
//
//
//        CartItem cartItem = CartItem.builder()
//                .user(user1)
//                .album(myAlbum)
//                .quantity(Long.valueOf(albumDto.getQuantity()))
//                .build();
//        cartItemRepository.save(cartItem);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//
//    }
}
