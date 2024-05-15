package com.example.clearsolutionstestassignment.user.service.impl;

import com.example.clearsolutionstestassignment.exception.EmailAlreadyExistsException;
import com.example.clearsolutionstestassignment.exception.IncorrectDateRangeException;
import com.example.clearsolutionstestassignment.exception.UserNotFoundException;
import com.example.clearsolutionstestassignment.user.User;
import com.example.clearsolutionstestassignment.user.UserRepository;
import com.example.clearsolutionstestassignment.user.dto.UserCreationDTO;
import com.example.clearsolutionstestassignment.user.dto.UserMapper;
import com.example.clearsolutionstestassignment.user.dto.UserUpdateDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private User user;
    private UserCreationDTO creationDTO;
    private UserUpdateDTO updateDTO;

    @BeforeEach
    public void init() {
        user = User.builder()
                .id(1L)
                .email("saul@mail.com")
                .firstName("Saul")
                .lastName("Goodman")
                .birthDate(LocalDate.of(1960, 11, 12))
                .build();

        creationDTO = UserCreationDTO.builder()
                .email("saul@mail.com")
                .firstName("Saul")
                .lastName("Goodman")
                .birthDate(LocalDate.of(2020, 11, 12)).build();

        updateDTO = UserUpdateDTO.builder()
                .email("saul@mail.com")
                .firstName("Jimmy")
                .lastName("Mcgill")
                .birthDate(LocalDate.of(1960, 11, 12)).build();
    }

    @Test
    public void CreateUser_ReturnsUser() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userServiceImpl.createUser(creationDTO);

        Assertions.assertThat(savedUser).isNotNull();
        assertEquals(1L, savedUser.getId());
    }

    @Test
    public void CreateUser_ThrowEmailAlreadyExistsException() {
        when(userRepository.countByEmail(creationDTO.getEmail())).thenReturn(1);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userServiceImpl.createUser(creationDTO);
        });
    }


    @Test
    public void GetUserById_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        User userReturn = userServiceImpl.getUserById(1L);

        Assertions.assertThat(userReturn).isNotNull();
    }

    @Test
    public void GetUserById_ThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.getUserById(1L);
        });
    }

    @Test
    public void SearchUsersByBirthDate_ReturnUsersList() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByBirthDateBetween(any(), any())).thenReturn(users);

        List<User> usersResponse = userServiceImpl.searchUsersByBirthDate(LocalDate.of(1950, 1, 1), LocalDate.of(1960, 1, 1));

        Assertions.assertThat(usersResponse).isNotNull();
    }

    @Test
    public void SearchUsersByBirthDate_ThrowIncorrectDateRangeException() {
        assertThrows(IncorrectDateRangeException.class, () -> {
            userServiceImpl.searchUsersByBirthDate(LocalDate.of(2000, 1, 1), LocalDate.of(1999, 1, 1));
        });
    }

    @Test
    public void GetAlUsers_ReturnUsersList() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userServiceImpl.getAllUsers();

        Assertions.assertThat(allUsers).isNotEmpty();
    }

    @Test
    public void UpdateUser_ReturnUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userServiceImpl.updateUser(updateDTO, user.getId());

        assertEquals("Jimmy", savedUser.getFirstName());
        assertEquals("Mcgill", savedUser.getLastName());
    }

    @Test
    public void UpdateUser_ThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.updateUser(updateDTO, 1L);
        });
    }

    @Test
    public void DeleteUser_ReturnVoid() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        doNothing().when(userRepository).deleteById(user.getId());

        assertAll(() -> userServiceImpl.deleteUser(user.getId()));
    }

    @Test
    public void DeleteUser_ThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.deleteUser(1L);
        });
    }

    @Test
    public void UpdatePhoneNumber_ReturnUser() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        User saved = userRepository.save(user);

        doNothing().when(userRepository).updatePhoneNumber("1111", 1L);
        userRepository.updatePhoneNumber("1111", saved.getId());

        assertNotEquals("1111", saved.getPhoneNumber());
    }
}