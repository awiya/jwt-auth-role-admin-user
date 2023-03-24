package com.example.jwtauthroleadminuser.services;

import com.example.jwtauthroleadminuser.entities.Role;
import com.example.jwtauthroleadminuser.entities.User;
import com.example.jwtauthroleadminuser.exceptions.RoleNotFoundException;
import com.example.jwtauthroleadminuser.repositories.RoleRepository;
import com.example.jwtauthroleadminuser.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role admin_role = Role.builder()
                .roleName("ROLE_ADMIN")
                .roleDescription("The admin role of the application.")
                .build();
        roleRepository.save(admin_role);

        Role user_role = Role.builder()
                .roleName("ROLE_USER")
                .roleDescription("The default user role for new users.")
                .build();
        roleRepository.save(user_role);

        Set<Role> admin_roles=new HashSet<>();
        admin_roles.add(admin_role);
        admin_roles.add(user_role);
        User admin = User.builder()
                .username("admin")
                .firstName("awiya")
                .lastName("ayiwa")
                .password(getEncodedPassword("adminPass"))
                .role(admin_roles)
                .build();
        userRepository.save(admin);

        Set<Role> user_roles=new HashSet<>();
        user_roles.add(user_role);
        User sample_user = User.builder()
                .username("user")
                .firstName("random")
                .lastName("usi")
                .password(getEncodedPassword("userPass"))
                .role(user_roles)
                .build();
        userRepository.save(sample_user);
    }

    public User registerNewUser(User user) {
        Role role = roleRepository.findById("ROLE_USER")
                .orElseThrow(()->new RoleNotFoundException(format("this role: %s is not allowed",user.getUsername())));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(getEncodedPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
