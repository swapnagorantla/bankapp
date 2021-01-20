package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.message.request.AuthRequestData;
import com.grokonez.jwtauthentication.security.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AuthController {

  @Autowired
  private AuthService authService;

  @RequestMapping(method = POST, value = "/authenticate")
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
  public ResponseEntity<Boolean> authenticate(@RequestBody AuthRequestData authRequestData) {
    return new ResponseEntity<>(authService.authenticate(authRequestData), HttpStatus.OK);
  }

}
