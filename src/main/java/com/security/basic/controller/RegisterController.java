package com.security.basic.controller;

import com.security.basic.model.Login;
import com.security.basic.model.Register;
import com.security.basic.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/get")
    public String message(){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register){
        Register registerObj=registerService.register(register);
        if(registerObj==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        else{
            return ResponseEntity.ok(registerObj);
        }
    }



}
