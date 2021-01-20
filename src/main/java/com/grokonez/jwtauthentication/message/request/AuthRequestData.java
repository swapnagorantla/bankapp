package com.grokonez.jwtauthentication.message.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Getter
@Setter

public class AuthRequestData {

  private String pan;
  private String name;
  private String DOB;


}
