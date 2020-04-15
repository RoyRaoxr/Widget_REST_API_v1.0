package com.challenge.demo.repo;

import com.challenge.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserUUID(UUID userUUID);
}
