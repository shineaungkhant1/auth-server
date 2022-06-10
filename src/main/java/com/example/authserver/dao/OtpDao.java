package com.example.authserver.dao;

import com.example.authserver.ds.Otp;
import com.example.authserver.ds.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpDao extends JpaRepository<Otp,String > {
    Optional<Otp> findOtpByUsername(String username);
}
