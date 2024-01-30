package org.hsharan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Api {
    @GetMapping
    public String base(){
        return "Welcome to Expense Project";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Welcome Admin";
    }

    @GetMapping("/user")
    public String user(){
        return "Welcome user";
    }
}
