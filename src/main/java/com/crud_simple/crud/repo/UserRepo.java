package com.crud_simple.crud.repo;

import java.util.List;

import com.crud_simple.crud.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepo
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    List<User> findUserByUsername(String username);

    User findById(int id);
}