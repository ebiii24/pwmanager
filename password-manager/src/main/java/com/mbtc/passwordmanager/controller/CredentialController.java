package com.mbtc.passwordmanager.controller;

import com.mbtc.passwordmanager.model.UrlCredential;
import com.mbtc.passwordmanager.model.UrlEntry;
import com.mbtc.passwordmanager.model.User;
import com.mbtc.passwordmanager.repository.CredRepository;
import com.mbtc.passwordmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credential")
public class CredentialController {

    @Autowired CredRepository credRepository;

    @Autowired UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/addCredential")
    public ResponseEntity<UrlCredential> addCredential(@RequestBody UrlEntry urlEntry) {
        if(!userRepository.existsById(userRepository.findByUsername(urlEntry.getUsername()).getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UrlCredential urlCredential = new UrlCredential();

        urlCredential.setId(userRepository
                            .findByUsername(urlEntry
                            .getUsername()).getId());
        urlCredential.setUrl(urlEntry.getUrl());
        urlCredential.setUrlPassword(passwordEncoder.encode(urlEntry.getUrlPassword()));

        userRepository.findByUsername(urlEntry.getUsername()).getUrlCredentials().add(urlCredential);
        credRepository.save(urlCredential);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/showCredentials/{username}")
    public ResponseEntity<List<UrlCredential>> showCred(@PathVariable("username") String username){

        try{
            List<UrlCredential> filteredList = new ArrayList<>();
            credRepository.findAll().forEach(cred -> {
                if (cred.getId().equals(userRepository.findByUsername(username).getId())) {
                    filteredList.add(cred);
                }
            });
            if(filteredList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(filteredList, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCredentials/{credId}")
    public ResponseEntity<String> delCred(@PathVariable("credId") Integer credId) {

        if (!credRepository.existsById(credId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            credRepository.deleteById(credId);
            return new ResponseEntity<>("Deleted credential with ID: "+credId, HttpStatus.OK);
        }
    }
}
