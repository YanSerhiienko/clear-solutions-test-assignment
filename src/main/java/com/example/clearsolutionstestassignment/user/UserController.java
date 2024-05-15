package com.example.clearsolutionstestassignment.user;

import com.example.clearsolutionstestassignment.user.dto.UserCreationDTO;
import com.example.clearsolutionstestassignment.user.dto.UserMapper;
import com.example.clearsolutionstestassignment.user.dto.UserResponseDTO;
import com.example.clearsolutionstestassignment.user.dto.UserUpdateDTO;
import com.example.clearsolutionstestassignment.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreationDTO dto) {
        User user = userService.createUser(dto);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid UserUpdateDTO dto, @PathVariable("id") long id) {
        User user = userService.updateUser(dto, id);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("/{id}/update-phone-number")
    public ResponseEntity<UserResponseDTO> updatePhoneNumber(@RequestParam String phoneNumber, @PathVariable("id") Long userId) {
        User updated = userService.updatePhoneNumber(phoneNumber, userId);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }


    @GetMapping("/search-by-birth-date")
    public ResponseEntity<List<UserResponseDTO>> searchByBirthDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        List<User> users = userService.searchUsersByBirthDate(from, to);
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> userDetails(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> allUsers = userService.getAllUsers();
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(allUsers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
