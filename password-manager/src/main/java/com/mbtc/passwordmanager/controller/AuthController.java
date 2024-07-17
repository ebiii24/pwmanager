package com.mbtc.passwordmanager.controller;

import com.mbtc.passwordmanager.model.UrlCredential;
import com.mbtc.passwordmanager.model.User;
import com.mbtc.passwordmanager.repository.CredRepository;
import com.mbtc.passwordmanager.repository.UserRepository;
import com.mbtc.passwordmanager.service.AuthenticationRequest;
import com.mbtc.passwordmanager.service.CustomUserDetailsService;
import com.mbtc.passwordmanager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredRepository credRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userRepository.findByUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return jwt;
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<User>> getAllAccounts(){
        try{
            List<User> list = new ArrayList<>();
            userRepository.findAll().forEach(list::add);

            if(list.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<String> delAcc(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
        return new ResponseEntity<>("Account Deleted", HttpStatus.ACCEPTED);
    }
}
