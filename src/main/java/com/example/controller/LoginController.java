package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Esto debe corresponder con el archivo login.html en la carpeta de templates
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Esto debe corresponder con el archivo index.html en la carpeta de templates
    }
}




