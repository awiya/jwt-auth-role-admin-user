package com.example.jwtauthroleadminuser.controllers;


import com.example.jwtauthroleadminuser.entities.JwtUserDetailsRequest;
import com.example.jwtauthroleadminuser.entities.JwtUserDetailsResponse;
import com.example.jwtauthroleadminuser.services.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtController {

    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping({"/authenticate"})
    public JwtUserDetailsResponse createJwtToken(@RequestBody JwtUserDetailsRequest jwtUserDetailsRequest) throws Exception {
        return jwtUserDetailsService.createJwtToken(jwtUserDetailsRequest);
    }
}
