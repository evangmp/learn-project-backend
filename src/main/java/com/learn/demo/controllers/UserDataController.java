package com.learn.demo.controllers;

import com.learn.demo.models.UserData;
import com.learn.demo.security.services.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserDataController {

    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping(value="/tasks")
    public ResponseEntity<List<UserData>> getAllUserData(){
        return ResponseEntity.ok().body(userDataService.getAllUserData());
    }

    @GetMapping(value="/tasks/{id}")
    public ResponseEntity<UserData> getUserDataById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userDataService.getUserDataById(id));
    }

    @PostMapping(value="/create")
    public ResponseEntity<UserData> createUserData(@RequestBody UserData userData){
        return ResponseEntity.ok().body(userDataService.saveUserData(userData));
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<UserData> updateUserData(@PathVariable("id") Long id, @RequestBody UserData userData){
        return ResponseEntity.ok().body(userDataService.saveUserData(userData));
    }
}
