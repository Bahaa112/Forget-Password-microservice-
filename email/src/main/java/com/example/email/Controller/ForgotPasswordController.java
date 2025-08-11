package com.example.email.Controller;

import com.example.email.Security.ChangePassword;
import com.example.email.Service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/verify/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        return forgotPasswordService.verifyEmail(email);
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        return forgotPasswordService.verifyOtp(otp, email);
    }

    @PostMapping("/changePassword/{otp}/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable Integer otp, @PathVariable String email) {
        return forgotPasswordService.changePassword(changePassword, otp,email);
    }
}
