package com.mbtc.passwordmanager.controller;

<<<<<<< HEAD
import com.mbtc.passwordmanager.model.UpdateEntry;
=======
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e
import com.mbtc.passwordmanager.model.UrlCredential;
import com.mbtc.passwordmanager.model.UrlEntry;
import com.mbtc.passwordmanager.model.User;
import com.mbtc.passwordmanager.repository.CredRepository;
import com.mbtc.passwordmanager.repository.UserRepository;
<<<<<<< HEAD
import com.mbtc.passwordmanager.util.Generator;
=======
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
<<<<<<< HEAD
@CrossOrigin(origins="localhost:3000")
=======
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e
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
<<<<<<< HEAD
        urlCredential.setUrlPassword(passwordEncoder.encode(Generator.generatePW(15)));
=======
        urlCredential.setUrlPassword(passwordEncoder.encode(urlEntry.getUrlPassword()));
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e

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

<<<<<<< HEAD
    @DeleteMapping("/deleteCredentials/{username}/{credId}")
    public ResponseEntity<String> delCred(
            @PathVariable("username") String username,
            @PathVariable("credId") Integer credId){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.getUrlCredentials().removeIf(urlCredential -> urlCredential.getCredId() == credId);
            userRepository.save(user);
            credRepository.deleteById(credId);
            return new ResponseEntity<>("Credentials Deleted", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Credentials does not Exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateCredentials/{credId}")
    public ResponseEntity<String> upCred(
            @PathVariable("credId") Integer credId,
            @RequestBody UpdateEntry updateEntry){

        Optional<UrlCredential> urlCredential = credRepository.findById(credId);
        if(urlCredential.isPresent()){
            UrlCredential _urlCredential = urlCredential.get();
            _urlCredential.setUrl(updateEntry.getUrl());
            _urlCredential.setUrlPassword(passwordEncoder.encode(updateEntry.getUrlPassword()));
            credRepository.save(_urlCredential);
            return new ResponseEntity<>("Credentials Updated", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Credentials NOT FOUND", HttpStatus.NO_CONTENT);
=======
    @DeleteMapping("/deleteCredentials/{credId}")
    public ResponseEntity<String> delCred(@PathVariable("credId") Integer credId) {

        if (!credRepository.existsById(credId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            credRepository.deleteById(credId);
            return new ResponseEntity<>("Deleted credential with ID: "+credId, HttpStatus.OK);
>>>>>>> 41229d689faede3eb57328b476ff58a4b5416b6e
        }
    }
}
