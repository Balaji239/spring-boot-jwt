package com.example.controller;

import com.example.entity.LoginRequest;
import com.example.entity.LoginResponse;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        Integer userId = userService.saveUser(user);
        return ResponseEntity.ok("User with id " + userId + " saved successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        String token = jwtUtil.generateToken(request.getName());
        return ResponseEntity.ok(new LoginResponse(token,"token generated successfully"));
    }

    @GetMapping("/welcome")
    public String demo1(Principal p){
        return "Welcome " + p.getName();
    }
}
