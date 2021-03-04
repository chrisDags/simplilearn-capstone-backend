package com.hcl.capstonebackend.controller;

import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class AdminController {
    //todo: admin specific URIs for editing/deleting store items

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable Long id) {

        albumRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/albums/{id}")
    public ResponseEntity<?> patchAlbumById(@PathVariable Long id){

        System.out.println("hit endpoint: " + id);

        Optional<Album> album = albumRepository.findById(id);

        Album album1 = album.get();


        return null;
    }

}
