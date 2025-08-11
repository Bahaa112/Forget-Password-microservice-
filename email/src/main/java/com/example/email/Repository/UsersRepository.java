package com.example.email.Repository;

import com.example.email.Entity.Users;
import com.example.email.Enums.UserStatus;
import com.example.email.Projection.UsersProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    //@Query(value = "select * from users", nativeQuery = true)
    public Page<UsersProjection> findAllBy(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update users set isDeleted=true where name=:name", nativeQuery = true)
    public void deleteUserByName(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into users(email,name,password,companyid) values (:email , :name , :password , :companyid);", nativeQuery = true)
    public void addUser(String email, String name, String password, int companyid);

    @Modifying
    @Transactional
    @Query(value = "update users set email=:email , name=:name , password=:password , companyid=:companyid where email=:email;", nativeQuery = true)
    public void updateUser(String email, String name, String password, int companyid);

    public Page<UsersProjection> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByEmail(String email);

    Users findUsersByName(String name);


    Page<UsersProjection> findUsersByStatus(UserStatus status, Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "update users set status=:status where id=:id;" , nativeQuery = true)
    void updateById(int id, String status);

    Page<UsersProjection> findAllByNameContainingIgnoreCaseAndStatus(String name , UserStatus status,Pageable pageable);

    Users findUsersByEmail(String email);

    boolean existsById(int id);

    @Modifying
    @Transactional
    @Query(value = "update users set isDeleted=true where id=:id;",nativeQuery = true)
    void deleteById(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET isDeleted = true WHERE companyid = :companyId", nativeQuery = true)
    void markUsersDeletedByCompanyId(@Param("companyId") int companyId);

    @Modifying
    @Transactional
    @Query(value = "update users set password=:password where email=:email;",nativeQuery = true)
    void updatePassword(String email,String password);



}
