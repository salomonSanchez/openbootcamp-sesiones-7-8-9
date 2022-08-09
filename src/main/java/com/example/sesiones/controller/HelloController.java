package com.example.sesiones.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${message}")
    private String mensage;
    
    @GetMapping("/")
    public String main(){
        return this.mensage;
    }

    @GetMapping("/saludar")
    public String saludo(){
        return "un saludo desde spring rest!...";
    }
    
}
