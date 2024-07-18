package com.mbtc.passwordmanager.service;

import com.mbtc.passwordmanager.model.User;
import com.mbtc.passwordmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD

=======
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
<<<<<<< HEAD

=======
import java.util.HashSet;
import java.util.Set;
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}