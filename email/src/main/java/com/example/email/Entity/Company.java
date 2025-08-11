package com.example.email.Entity;


import com.example.email.Enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.List;

@Data
@Entity
@SQLDelete(sql = "UPDATE company SET isDeleted = true WHERE id = ?")
@Where(clause = "isDeleted = false")
public class Company {

    @Id
    private int id;
    private String email;
    private Date creationDate;
    private Date updatedDate;
    private String companyName;
    private String location;
    private boolean isDeleted;
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL , fetch= FetchType.LAZY)
    private List<Users> users;
    @Enumerated(EnumType.STRING)
    private CompanyStatus status = CompanyStatus.WORKING;


}
