package com.example.clearsolutionstestassignment.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByBirthDateBetween(LocalDate rangeFrom, LocalDate rangeTo);

    int countByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.phoneNumber = :phone where u.id = :id")
    void updatePhoneNumber(@Param("phone") String phoneNumber, @Param("id") Long userId);
}
