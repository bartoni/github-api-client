package com.github.apiclient.service.impl;

import com.github.apiclient.model.dto.UserDTO;
import com.github.apiclient.repository.UserRepository;
import com.github.apiclient.service.GitHubApiClient;
import com.github.apiclient.model.GitHubUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private GitHubApiClient apiClient;

    @Mock
    private UserRepository userRepository;

    @Test
    void getUserInfo() {
        //given
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setId("123");
        gitHubUser.setLogin("testuser");
        gitHubUser.setName("Test User");
        gitHubUser.setType("User");
        gitHubUser.setAvatarUrl("http://example.com/avatar");
        gitHubUser.setCreatedAt("2023-10-18");
        gitHubUser.setFollowers(100);
        gitHubUser.setPublicRepos(50);

        when(apiClient.getGitHubUser("testuser")).thenReturn(gitHubUser);

        //when
        UserDTO userDTO = userService.getUserInfo("testuser");

        //then
        assertEquals("123", userDTO.getId());
        assertEquals("testuser", userDTO.getLogin());
        assertEquals("Test User", userDTO.getName());
        assertEquals("User", userDTO.getType());
        assertEquals("http://example.com/avatar", userDTO.getAvatarUrl());
        assertEquals("2023-10-18", userDTO.getCreatedAt());
        assertEquals("3.12", userDTO.getCalculations());

        verify(userRepository, times(1)).incrementRequestCount("testuser");
    }
}