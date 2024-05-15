package com.example.clearsolutionstestassignment.user.service.impl;

import com.example.clearsolutionstestassignment.exception.EmailAlreadyExistsException;
import com.example.clearsolutionstestassignment.exception.IncorrectDateRangeException;
import com.example.clearsolutionstestassignment.exception.UserNotFoundException;
import com.example.clearsolutionstestassignment.user.User;
import com.example.clearsolutionstestassignment.user.UserRepository;
import com.example.clearsolutionstestassignment.user.dto.UserCreationDTO;
import com.example.clearsolutionstestassignment.user.dto.UserMapper;
import com.example.clearsolutionstestassignment.user.dto.UserUpdateDTO;
import com.example.clearsolutionstestassignment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(UserCreationDTO dto) {
        if (userRepository.countByEmail(dto.getEmail()) > 0) {
            throw new EmailAlreadyExistsException("User with such email already exists");
        }
        User user = userMapper.mapUserCreationDTOToUser(dto);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be found"));
    }

    @Override
    public List<User> searchUsersByBirthDate(LocalDate rangeFrom, LocalDate rangeTo) {
        if (rangeFrom.isAfter(rangeTo)) {
            throw new IncorrectDateRangeException("Date range is incorrect");
        }
        return userRepository.findByBirthDateBetween(rangeFrom, rangeTo);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserUpdateDTO dto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be updated"));
        User mapped = userMapper.mapUserUpdateDTOToUser(dto, user);
        return userRepository.save(mapped);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be deleted"));
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User updatePhoneNumber(String phoneNumber, Long userId) {
        userRepository.updatePhoneNumber(phoneNumber, userId);
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User could not be updated"));
    }
}
