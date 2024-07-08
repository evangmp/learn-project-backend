package com.learn.demo.controllers;

import com.learn.demo.models.UserParameters;
import com.learn.demo.security.services.UserParametersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/parameters")
public class UserParametersController {
    private final UserParametersService userParametersService;

    public UserParametersController(UserParametersService userParametersService) {
        this.userParametersService = userParametersService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<UserParameters> getUserParameters(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userParametersService.getUserParametersById(id));
    }
}
