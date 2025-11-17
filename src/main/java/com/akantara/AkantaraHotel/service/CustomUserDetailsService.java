package com.akantara.AkantaraHotel.service;


import com.akantara.AkantaraHotel.exception.CustomException;
import com.akantara.AkantaraHotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Injects the UserRepository to access user data from the database
    @Autowired
    private UserRepository userRepository;

    // Loads a user by username (email) for Spring Security authentication, if the user is not found, it throws exception
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new CustomException("Username/Email is not found"));
    }
}
