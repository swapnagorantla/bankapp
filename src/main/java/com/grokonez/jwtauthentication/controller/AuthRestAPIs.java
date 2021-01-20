package com.grokonez.jwtauthentication.controller;


import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.grokonez.jwtauthentication.message.request.deleteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.grokonez.jwtauthentication.message.request.LoginForm;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.message.response.JwtResponse;
import com.grokonez.jwtauthentication.model.Role;
import com.grokonez.jwtauthentication.model.RoleName;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.RoleRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import com.grokonez.jwtauthentication.security.jwt.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        validateRequest(signUpRequest);

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
        	switch(role) {
	    		case "admin":
	    			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	    			roles.add(adminRole);
	    	}
        });
        
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }


    @PostMapping("/createEmployee")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody SignUpForm signUpRequest) {
        validateRequest(signUpRequest);
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            System.out.println(role);
            switch(role) {

                case "employee":
                    Role employeeRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(employeeRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }


    @DeleteMapping("/deleteEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEmployee(@Valid @RequestBody deleteForm deleteRequest) {
            userRepository.deleteAllByUsername(deleteRequest.getUsername());
        return ResponseEntity.ok().body("User deleted successfully!");
    }

    public ResponseEntity<String> validateRequest(@Valid @RequestBody SignUpForm signUpRequest){
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Fail -> Error!",
        HttpStatus.BAD_REQUEST);
    }


}