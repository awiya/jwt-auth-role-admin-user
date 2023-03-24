package com.example.jwtauthroleadminuser.repositories;

import com.example.jwtauthroleadminuser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
