package com.example.email.Projection;

import com.example.email.Enums.UserStatus;

public interface UsersProjection {

    String getEmail();
    String getName();
    CompanyIdOnly getCompany();
    //String getStatus();
    UserStatus getStatus();

    interface CompanyIdOnly
    { Integer getId();

    }

}
