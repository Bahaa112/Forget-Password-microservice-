package com.example.email.Security;


import lombok.Data;

@Data
public class ChangePassword {

    private String password;
    private String repeatPassword;
    private Integer otp;
}
