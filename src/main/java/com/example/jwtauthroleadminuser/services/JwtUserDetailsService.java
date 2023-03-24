package com.example.jwtauthroleadminuser.services;


import com.example.jwtauthroleadminuser.entities.JwtUserDetailsRequest;
import com.example.jwtauthroleadminuser.entities.JwtUserDetailsResponse;
import com.example.jwtauthroleadminuser.entities.User;
import com.example.jwtauthroleadminuser.exceptions.UserNotFoundException;
import com.example.jwtauthroleadminuser.repositories.UserRepository;
import com.example.jwtauthroleadminuser.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepo;
    private AuthenticationManager authenticationManager;

    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public JwtUserDetailsResponse createJwtToken(JwtUserDetailsRequest jwtUserDetailsRequest) throws Exception {
        String username = jwtUserDetailsRequest.getUsername();
        String userPassword = jwtUserDetailsRequest.getPassword();
        authenticate(username, userPassword);

        UserDetails userDetails = loadUserByUsername(username);
        String generatedToken = jwtTokenUtil.generateToken(userDetails);

        User user = userRepo.findById(username)
                .orElseThrow(() -> new UserNotFoundException(format("this user does not exist in the DB", username)));

        return new JwtUserDetailsResponse(user, generatedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username)
                .orElseThrow(() -> new UserNotFoundException(format("this user does not exist in the DB", username)));

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("No user was found with the username:" + username);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
