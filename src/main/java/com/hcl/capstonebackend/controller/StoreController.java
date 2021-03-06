package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.Service.StoreService;
import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.domain.Cart;
import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.dto.AlbumDto;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
public class StoreController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;


    @Autowired
    StoreService storeService;


    @GetMapping("/albums")
    public Iterable<Album> getAllAlbums() {
        return albumRepository.findAll();
    }


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

        boolean result = storeService.userExists(userDto);

        if (result)
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getAllInCart(Principal principal) {

        return ResponseEntity.ok(storeService.retrieveAllInCart(principal));
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartItem(@PathVariable Long id){

        Optional<CartItem> cartItem = cartItemRepository.findById(id);

        CartItem cartItem1 = cartItem.get();

        return new ResponseEntity<>(cartItem1, HttpStatus.OK);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> createAlbumInCart(@RequestBody AlbumDto album, Principal principal) {

        storeService.saveAlbumInCart(album, principal);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> deleteAlbumFromCart(@PathVariable Long id){

        storeService.removeAlbumFromCart(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> deleteCart(Principal principal){
        return null;
    }

    @GetMapping("/albums/test/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id){

        return new ResponseEntity<>(storeService.retrieveAlbumById(id), HttpStatus.OK);
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long id, String quantity,  Principal principal){

        if(!cartItemRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<CartItem> cartItem = cartItemRepository.findById(id);

        CartItem cartItem1 = cartItem.get();

        cartItem1.setQuantity(Long.valueOf(quantity));

        cartItemRepository.save(cartItem1);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
