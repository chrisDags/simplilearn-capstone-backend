package com.hcl.capstonebackend;

import com.hcl.capstonebackend.domain.Album;
import com.hcl.capstonebackend.domain.CartItem;
import com.hcl.capstonebackend.domain.Song;
import com.hcl.capstonebackend.domain.User;
import com.hcl.capstonebackend.repository.AlbumRepository;
import com.hcl.capstonebackend.repository.CartItemRepository;
import com.hcl.capstonebackend.repository.SongRepository;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class CapstoneBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneBackendApplication.class, args);
    }


    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {




        Album album = Album.builder()
                .artist("artist")
                .price(Double.valueOf(100))
                .releaseDate(new Date())
                .title("Title")
                .format("CD")
                .genre("Rock")
                .build();

        Album album1 = Album.builder()
                .artist("artist 2")
                .price(50.00)
                .releaseDate(new Date())
                .genre("Rap")
                .title("Title 2")
                .format("Vinyl")
                .build();

        Album album2 = Album.builder()
                .artist("artist 2")
                .price(50.00)
                .releaseDate(new Date())
                .genre("Rap 2")
                .title("Title 3")
                .format("Vinyl")
                .build();


        Album album3 = Album.builder()
                .artist("artist 3")
                .price(50.00)
                .releaseDate(new Date())
                .genre("Rap 2")
                .title("Title 4")
                .format("Vinyl")
                .build();



        Song song = Song.builder()
                .songName("Community Property")
                .album(album)
                .build();

        Song song1 = Song.builder()
                .songName("song by artist2")
                .album(album1)
                .build();

        Song song2 = Song.builder()
                .songName("another song")
                .album(album1)
                .build();

        Song song3 = Song.builder()
                .songName("artist 2 song")
                .album(album2)
                .build();


        User user = User.builder()
                .username("chris")
                .password("123")
                .build();

        User user1 = User.builder()
                .username("foo")
                .password("123")
                .build();

        CartItem cartItem = CartItem.builder()
                .album(album)
                .user(user)
                .build();

        CartItem cartItem1 = CartItem.builder()
                .album(album)
                .user(user)
                .build();

        CartItem cartItem2 = CartItem.builder()
                .album(album)
                .user(user1)
                .build();

        albumRepository.save(album);
        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        songRepository.save(song);
        songRepository.save(song1);
        songRepository.save(song2);
        userRepository.save(user);
        userRepository.save(user1);
        cartItemRepository.save(cartItem);
        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);
    }
}
