package com.example.jwtauthroleadminuser.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUserDetailsRequest {

    private String username;
    private String password;

}
