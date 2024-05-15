package com.example.clearsolutionstestassignment.user;

import com.example.clearsolutionstestassignment.user.dto.UserMapper;
import com.example.clearsolutionstestassignment.user.dto.UserResponseDTO;
import com.example.clearsolutionstestassignment.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @SpyBean
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private UserResponseDTO responseDTO;

    @BeforeEach
    public void init() {
        user = User.builder()
                .id(1L)
                .email("saul@mail.com")
                .firstName("Saul")
                .lastName("Goodman")
                .birthDate(LocalDate.of(1960, 11, 12))
                .phoneNumber("505-842-5662").build();

        responseDTO = UserResponseDTO.builder()
                .id(1L)
                .email("saul@mail.com")
                .firstName("Saul")
                .lastName("Goodman")
                .birthDate(LocalDate.of(1960, 11, 12))
                .phoneNumber("505-842-5662").build();
    }

    @Test
    public void CreateUser_ReturnUserResponseDTO() throws Exception {
        when(userService.createUser(any())).thenReturn(user);

        ResultActions response = mockMvc.perform(post("/api/v1/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())));
    }

    @Test
    public void UpdateUser_ReturnUserResponseDTO() throws Exception {
        when(userService.updateUser(any(), any())).thenReturn(user);

        ResultActions response = mockMvc.perform(patch("/api/v1/users/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(responseDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(responseDTO.getLastName())));
    }

    @Test
    public void UserDetails_ReturnUserResponseDTO() throws Exception {
        long userId = 1;
        when(userService.getUserById(userId)).thenReturn(user);

        ResultActions response = mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(responseDTO.getEmail())));
    }

    @Test
    public void DeleteUser_ReturnString() throws Exception {
        long userId = 1;
        String message = "User has been deleted";
        doNothing().when(userService).deleteUser(userId);

        ResultActions response = mockMvc.perform(delete("/api/v1/users/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", CoreMatchers.is(message)));
    }

    @Test
    public void SearchByBirthDate_ReturnUserResponseDTO() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.searchUsersByBirthDate(any(), any())).thenReturn(userList);

        ResultActions response = mockMvc.perform(get("/api/v1/users/search-by-birth-date?from=2000-01-01&to=2001-05-06")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", CoreMatchers.is(userList.size())));
    }

    @Test
    public void GetAll_ReturnUserResponseDTO() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.getAllUsers()).thenReturn(userList);

        ResultActions response = mockMvc.perform(get("/api/v1/users/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", CoreMatchers.is(userList.size())));
    }

    @Test
    public void UpdatePhoneNumber_ReturnUserResponseDTO() throws Exception {
        when(userService.updatePhoneNumber(any(), any())).thenReturn(user);

        ResultActions response = mockMvc.perform(patch("/api/v1/users/1/update-phone-number?phoneNumber=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber", CoreMatchers.is(responseDTO.getPhoneNumber())));
    }
}