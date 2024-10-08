package com.learn.demo.controllers;

import com.learn.demo.models.*;
import com.learn.demo.payload.request.TokenRefreshRequest;
import com.learn.demo.payload.response.TokenRefreshResponse;
import com.learn.demo.security.jwt.AuthEntryPointJwt;
import com.learn.demo.security.jwt.exeption.TokenRefreshException;
import com.learn.demo.security.services.RefreshTokenService;
import com.learn.demo.security.services.UserDataService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.learn.demo.payload.request.SignupRequest;
import com.learn.demo.payload.request.LoginRequest;
import com.learn.demo.payload.response.JwtResponse;
import com.learn.demo.payload.response.MessageResponse;
import com.learn.demo.repository.RoleRepository;
import com.learn.demo.repository.UserRepository;
import com.learn.demo.security.jwt.JwtUtils;
import com.learn.demo.security.services.UserDetailsImpl;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    public AuthController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    // connection to an account, with creation of a token
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        int deleteDBToken = refreshTokenService.deleteByUserId(userDetails.getId());

        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        System.out.println("token: " + jwt);
        return ResponseEntity
                .ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    // creation of account
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        //
        // new MessageResponse("User registered successfully!")
        return ResponseEntity.ok(user.getId());
    }

    // delete token by id
    @PostMapping("/deletetoken")
    public ResponseEntity<?> deleteTokenById(@Valid @RequestBody Long id) {
        int response = refreshTokenService.deleteByUserId(id);
        return ResponseEntity.ok(new MessageResponse("good bro, nice"+ response));
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    private final UserDataService userDataService;

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


    @PostMapping("/create")
    public ResponseEntity<?> createUserData(@RequestBody UserData userData){
        System.out.println("id: "+ userData.getId() + ", name : " + userData.getTaskName());
        System.out.println("discipline: "+ userData.getTaskDiscipline() + ", date : " + userData.getTaskDate());
        System.out.println("acheivneement: "+ userData.getTaskAchievement());
        return ResponseEntity.ok().body(userDataService.saveUserData(userData));
    }

    @GetMapping(value="/tasks")
    public ResponseEntity<List<UserData>> getAllUserData(){
        return ResponseEntity.ok().body(userDataService.getAllUserData());
    }

    @GetMapping(value="/tasks/{id}")
    public ResponseEntity<UserData> getUserDataById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userDataService.getUserDataById(id));
    }

    @PostMapping("/delete/{idTask}")
    public ResponseEntity<?> deleteUserData(@RequestBody UserData userData, @PathVariable("idTask") Long idTask){
        return ResponseEntity.ok().body(userDataService.deleteUserData(idTask, userData));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUserData(@RequestBody UserData userData){
        userDataService.deleteUser(userData.getId());
        return ResponseEntity.ok().body(userDataService.saveUserData(userData));
    }

    @GetMapping(value="/id/{username}")
    public Long getIdByUsername(@PathVariable("username") String username){
        return userDataService.userIdByUsername(username);
    }
}
