package com.grokonez.jwtauthentication.security.services;


import com.grokonez.jwtauthentication.message.request.AuthRequestData;
import com.grokonez.jwtauthentication.model.DeviceCollectedAuthData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Service
public class AuthService {

  RestTemplate restTemplate = new RestTemplate();
  final String uri = "https://ind-verify.hyperverge.co/api";

  public AuthService() throws MalformedURLException, URISyntaxException {

    String result = restTemplate.getForObject(uri, String.class);
    System.out.println(result);
  }

  public boolean authenticate(AuthRequestData authRequestData) {
    DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
    authData.setPan(authRequestData.getPan());
    authData.setName(authRequestData.getName());
    authData.setDob(authRequestData.getDOB());

    return  restTemplate.patchForObject(uri, authData,Boolean.class);
  }

}
