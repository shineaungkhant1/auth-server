package com.example.authserver.service;

import com.example.authserver.dao.OtpDao;
import com.example.authserver.dao.UserDao;
import com.example.authserver.ds.Otp;
import com.example.authserver.ds.User;
import com.example.authserver.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OtpDao otpDao;

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public void auth(User user){
        Optional<User> o=userDao.findUserByUsername(user.getUsername());
        if (o.isPresent()){
            User u=o.get();
            if (passwordEncoder.matches(user.getPassword(), user.getPassword())){
                renewOtp(u);
            }
            else {
                throw new BadCredentialsException("Bad credentials.");
            }
        }
        else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    public boolean check(Otp otpToValidate){
        
    }

    private void renewOtp(User u){
        String code= GenerateCodeUtil.generateCode();

        Optional<Otp> userOtp=otpDao.findOtpByUsername(u.getUsername());
        if(userOtp.isPresent()){//existing otp
            Otp otp=userOtp.get();
            otp.setCode(code);
        }else {
            Otp otp=new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpDao.save(otp);
        }

    }


}
