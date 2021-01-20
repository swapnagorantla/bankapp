package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.message.request.LoginForm;
import com.grokonez.jwtauthentication.message.request.SignUpForm;
import com.grokonez.jwtauthentication.message.request.deleteForm;
import com.grokonez.jwtauthentication.message.response.JwtResponse;
import com.grokonez.jwtauthentication.model.Role;
import com.grokonez.jwtauthentication.model.RoleName;
import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.RoleRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;
import com.grokonez.jwtauthentication.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class EmployeeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/employeeSignin")
    public ResponseEntity<?> employeeSignin() throws LoginException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth +":: auth in emp");
        if (auth != null) {
            new SecurityContextLoginModule().login();
        }
      //  auth.setAuthenticated(true);
        return ResponseEntity.ok().body("Employee loggedin successfully!");
    }



    @PostMapping("/signupEmployee")
    @PreAuthorize("hasRole('ADMIN')")
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

        System.out.println(deleteRequest.getUsername());
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

    @PostMapping("/employeeSignOut")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        auth.setAuthenticated(false);

        return ResponseEntity.ok().body("Employee logged out successfully!");

    }

}
