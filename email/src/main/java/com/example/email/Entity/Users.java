package com.example.email.Entity;

import com.example.email.Enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Data
@Entity
@SQLDelete(sql = "UPDATE users SET isDeleted = true WHERE id = ?")
@Where(clause = "isDeleted = false")
public class Users {

    @Id
    private int id;
    private String email;
    private Date creationDate;
    private Date updatedDate;
    private String password;
    private String name;
    private boolean isDeleted;
    //private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId")
    private Company company;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;
    public Integer getCompanyId() {
        return company != null ? company.getId() : null;
    }

    //@OneToOne(mappedBy = "Users")
    //private ForgotPassword forgotPassword;

}
