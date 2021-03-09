package com.hcl.capstonebackend.Service;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.domain.Song;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.AlbumDto;
import com.hcl.capstonebackend.dto.CartDto;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.SongRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import com.sun.jdi.InvalidTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    SongRepository songRepository;



    public void updateCartItemQuantity(String id, CartDto cartDto){
        Optional<CartItem> cartItem = cartItemRepository.findById(Long.valueOf(id));
        cartItem.orElseThrow(() -> new RuntimeException("Invalid id"));

        CartItem cartItem1 = cartItem.get();

        cartItem1.setQuantity(Long.valueOf(cartDto.getQuantity()));

        cartItemRepository.save(cartItem1);
    }

    public List<Song> retrieveSongsByAlbumId(Long id){
        return songRepository.getAllByAlbum_Id(id);
    }

    public boolean userExists(UserDto userDto){
        return  userRepository.existsUsersByUsername(userDto.getUsername());
    }

    public Iterable<CartItem> retrieveAllInCart(Principal principal){

        Optional<User> user = userRepository.findByUsername(principal.getName());

        User user1 = user.get();

        return  cartItemRepository.findAllByUser(user1);
    }

    public void saveAlbumInCart(AlbumDto album, Principal principal){


        Optional<User> user = userRepository.findByUsername(principal.getName());
        user.orElseThrow(() -> new UsernameNotFoundException("User not authenticated"));

        User user1 = user.get();

        Album myAlbum = albumRepository.findByTitle(album.getTitle());



        CartItem cartItem = CartItem.builder()
                .user(user1)
                .album(myAlbum)
                .quantity(album.getQuantity())
                .build();
        cartItemRepository.save(cartItem);

    }

    public void removeAlbumFromCart(Long id){
        cartItemRepository.deleteById(id);
    }

    public Album retrieveAlbumById(Long id){
        Optional<Album> album = albumRepository.findById(id);
        Album album1 = album.get();

        return album1;
    }

}
