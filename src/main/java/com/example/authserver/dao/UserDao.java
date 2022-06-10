package com.example.authserver.dao;

import com.example.authserver.ds.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,String > {

    Optional<User> findUserByUsername(String username);

}
