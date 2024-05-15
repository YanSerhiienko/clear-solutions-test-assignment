package com.example.clearsolutionstestassignment.user.service;

import com.example.clearsolutionstestassignment.user.User;
import com.example.clearsolutionstestassignment.user.dto.UserCreationDTO;
import com.example.clearsolutionstestassignment.user.dto.UserUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(UserCreationDTO dto);

    User getUserById(Long id);

    List<User> searchUsersByBirthDate(LocalDate rangeFrom, LocalDate rangeTo);

    List<User> getAllUsers();

    User updateUser(UserUpdateDTO dto, Long id);

    void deleteUser(Long id);

    User updatePhoneNumber(String phoneNumber, Long userId);
}
