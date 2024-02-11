package org.hsharan.controller;

import org.hsharan.userAuth.UserDetails;
import org.hsharan.userAuth.UserSerivce;
import org.hsharan.userAuth.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserSerivce userSerivce;
    @GetMapping
    public String base(@RequestParam String pass){
        String password = passwordEncoder.encode(pass);
        return "Welcome to Expense Project "+password;
    }

    @GetMapping("/admin")
    public String admin(){
        return "Welcome Admin";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetails> user(@PathVariable String userId){
        UserDetails userDetails = userSerivce.findUserById(userId);
        userDetails.setPassword("**************");
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping("/")
    public ResponseEntity<String> addUser(@RequestBody UserDetails userDetails){
        try {
            userSerivce.addUser(userDetails);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Successfully added the User Details");
    }



}
