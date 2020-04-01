package com.coronatracker.controller;

import com.coronatracker.db.model.User;
import com.coronatracker.dto.AuthResponse;
import com.coronatracker.dto.LoginDTO;
import com.coronatracker.security.SessionTokenProvider;
import com.coronatracker.service.FacebookService;
import com.coronatracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SessionTokenProvider sessionTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private FacebookService facebookService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody final LoginDTO loginDTO) {
        final User user = userService.createUser(loginDTO);
        return ResponseEntity.ok(AuthResponse.builder().authToken(sessionTokenProvider.createToken(user.getId())).build());
    }
}
