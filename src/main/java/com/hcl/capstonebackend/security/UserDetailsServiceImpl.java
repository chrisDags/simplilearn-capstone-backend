package com.hcl.capstonebackend.security;

import com.hcl.capstonebackend.dto.UserDto;
import com.hcl.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
        This method runs on authentication
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       /*
            Optional<User> user = userRepository.findByUsername(userName);
            user.orElseThrow(()-> new UsernameNotFoundException("Username not found!: "+ userName));
            return user.map(UserDetailsImpl::new).get();
        */


        com.hcl.capstonebackend.domain.User userDao = userRepository.findByUsername(username);

        if(userDao == null)
            throw new UsernameNotFoundException("User \""+ username + "\" not found!");

        return new User(userDao.getUsername(), userDao.getPassword(), new ArrayList<>());
    }

    public com.hcl.capstonebackend.domain.User save(UserDto userDto){
        com.hcl.capstonebackend.domain.User newUserDao = new com.hcl.capstonebackend.domain.User();

        newUserDao.setUsername(userDto.getUsername());
        newUserDao.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(newUserDao);
    }
}
