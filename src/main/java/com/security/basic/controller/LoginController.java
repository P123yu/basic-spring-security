package com.security.basic.controller;

import com.security.basic.model.Login;
import com.security.basic.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
@CrossOrigin

public class LoginController {

    @Autowired
    private LoginService loginService;

    // this request in form of request body is coming from security filter chain after authentication
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login){
         Authentication authentication=loginService.login(login);
         if(authentication!=null){
             return ResponseEntity.ok(authentication);
         }
         else{
             return ResponseEntity.status(400).body("Invalid Credentials");
         }
    }
}
