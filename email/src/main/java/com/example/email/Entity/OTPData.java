package com.example.email.Entity;

import java.util.Date;

public class OTPData {

    private final int otp;
    private final Date expirationTime;

    public OTPData(int otp, Date expirationTime) {
        this.otp = otp;
        this.expirationTime = expirationTime;
    }

    public int getOtp() {
        return otp;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }
}
