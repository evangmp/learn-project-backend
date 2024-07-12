package com.learn.demo.controllers;

import com.learn.demo.models.UserData;
import com.learn.demo.security.jwt.AuthEntryPointJwt;
import com.learn.demo.security.services.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserDataController {

    private final UserDataService userDataService;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }


    @GetMapping(value="/tasks/{id}")
    public ResponseEntity<UserData> getUserDataById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userDataService.getUserDataById(id));
    }

}
