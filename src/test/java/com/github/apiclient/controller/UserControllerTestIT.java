package com.github.apiclient.controller;

import com.github.apiclient.model.dto.UserDTO;
import com.github.apiclient.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testGetUserInfo() throws Exception {
        String login = "testuser";
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(login);

        when(userService.getUserInfo(anyString())).thenReturn(userDTO);

        mockMvc.perform(get("/users/{login}", login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login", is(login)));
    }

    @Test
    void testGetUserInfo_throwsException_whenLoginBlank() throws Exception {
        String login = " ";

        mockMvc.perform(get("/users/{login}", login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}