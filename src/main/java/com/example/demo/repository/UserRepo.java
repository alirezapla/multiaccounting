package com.example.demo.repository;


import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import com.example.demo.model.User;
@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
        List<User> findByUsername(String username);
}