package com.hcl.capstonebackend.Service;

import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.dto.AlbumDto;
import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    CartItemRepository cartItemRepository;


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
