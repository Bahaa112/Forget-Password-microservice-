package com.example.email.Repository;

import com.example.email.Entity.ForgotPassword;
import com.example.email.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer> {

    List<ForgotPassword> findByOtpAndUsers(Integer otp, Users users);
    List<ForgotPassword> findByUsers(Users users);


}
