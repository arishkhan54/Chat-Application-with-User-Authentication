package com.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
