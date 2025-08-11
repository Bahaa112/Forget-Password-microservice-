package com.example.email.Service;

import com.example.email.DTO.MailBody;
import com.example.email.Entity.ForgotPassword;
import com.example.email.Entity.OTPData;
import com.example.email.Entity.Users;
import com.example.email.Repository.ForgotPasswordRepository;
import com.example.email.Repository.UsersRepository;
import com.example.email.Security.ChangePassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UsersRepository usersRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, OTPData> otpStore = new HashMap<>();

    public ResponseEntity<String> verifyEmail(String email) {
        Users user = usersRepository.findUsersByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email not found");
        }

        int otp = otpGenerator();
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 1000);

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is your OTP for password reset: " + otp)
                .subject("OTP for password reset")
                .build();

        emailService.sendSimpleMessage(mailBody);

        otpStore.put(email, new OTPData(otp, expiration));

        return ResponseEntity.ok("OTP sent to email.");
    }

    public ResponseEntity<String> verifyOtp(Integer otp, String email) {
        OTPData otpData = otpStore.get(email);

        if (otpData == null || otpData.getOtp() != otp) {
            return new ResponseEntity<>("Invalid OTP", HttpStatus.NOT_FOUND);
        }

        if (otpData.getExpirationTime().before(new Date())) {
            otpStore.remove(email);
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP Verified!");
    }

    public ResponseEntity<String> changePassword(ChangePassword changePassword, Integer otp, String email) {
        if (!Objects.equals(changePassword.getPassword(), changePassword.getRepeatPassword())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.EXPECTATION_FAILED);
        }

        ResponseEntity<String> otpRes = verifyOtp(otp, email);
        if (!"OTP Verified!".equals(otpRes.getBody())) {
            return new ResponseEntity<>("Wrong or expired OTP", HttpStatus.EXPECTATION_FAILED);
        }

        Users user = usersRepository.findUsersByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.getPassword());
        usersRepository.updatePassword(email, encodedPassword);

        otpStore.remove(email);

        return ResponseEntity.ok("Password changed successfully!");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100000, 999999);
    }
}
