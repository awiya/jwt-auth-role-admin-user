package com.example.jwtauthroleadminuser.repositories;

import com.example.jwtauthroleadminuser.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
