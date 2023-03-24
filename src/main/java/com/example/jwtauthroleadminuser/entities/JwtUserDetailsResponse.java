package com.example.jwtauthroleadminuser.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserDetailsResponse {

    private User user;
    private String jwtToken;

}
