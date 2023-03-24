package com.example.jwtauthroleadminuser.controllers;


import com.example.jwtauthroleadminuser.entities.User;
import com.example.jwtauthroleadminuser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/onlyAdmin"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String forAdmin() {
        return "Only the admin can access this URL.";
    }

    @GetMapping({"/onlyUser"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public String forUser() {
        return "Only the user can access this URL.";
    }
}
