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
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    //todo: admin specific URIs for editing/deleting store items

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable Long id) {

        albumRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @PutMapping("/albums/{id}")
    public ResponseEntity<?> patchAlbumById(@PathVariable Long id, @RequestBody Album album){

        Optional<Album> album1 = albumRepository.findById(id);

        Album album2 = album1.get();

        album2.setArtist(album.getArtist());
        album2.setDescription(album.getDescription());
        album2.setFormat(album.getFormat());
        album2.setTitle(album.getTitle());
        album2.setGenre(album.getGenre());
        album2.setPrice(album.getPrice());

        albumRepository.save(album2);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
