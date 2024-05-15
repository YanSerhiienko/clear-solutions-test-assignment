package com.example.clearsolutionstestassignment.user;

import com.example.clearsolutionstestassignment.responseWrapper.CustomResponseWrapper;
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
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> createUser(@RequestBody @Valid UserCreationDTO dto) {
        User user = userService.createUser(dto);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("User has been successfully created")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> updateUser(@RequestBody @Valid UserUpdateDTO dto, @PathVariable("id") long id) {
        User user = userService.updateUser(dto, id);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("/{id}/update-phone-number")
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> updatePhoneNumber(@RequestParam String phoneNumber, @PathVariable("id") Long userId) {
        User updated = userService.updatePhoneNumber(phoneNumber, userId);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(updated);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User's phone number has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User has been deleted")
                .data(null)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/search-by-birth-date")
    public ResponseEntity<CustomResponseWrapper<List<UserResponseDTO>>> searchByBirthDate(@RequestParam LocalDate from, @RequestParam  LocalDate to) {
        List<User> users = userService.searchUsersByBirthDate(from, to);
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(users);
        CustomResponseWrapper<List<UserResponseDTO>> wrapper = CustomResponseWrapper.<List<UserResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of users by birth date from " + from + " to " + to)
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> userDetails(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponseWrapper<List<UserResponseDTO>>> getAll() {
        List<User> allUsers = userService.getAllUsers();
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(allUsers);
        CustomResponseWrapper<List<UserResponseDTO>> wrapper = CustomResponseWrapper.<List<UserResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all users")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
