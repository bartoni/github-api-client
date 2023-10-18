package com.github.apiclient.service.impl;

import com.github.apiclient.model.dto.UserDTO;
import com.github.apiclient.repository.UserRepository;
import com.github.apiclient.service.GitHubApiClient;
import com.github.apiclient.service.UserService;
import com.github.apiclient.model.GitHubUser;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final GitHubApiClient apiClient;

    private final UserRepository userRepository;

    public UserServiceImpl(GitHubApiClient apiClient, UserRepository userRepository) {
        this.apiClient = apiClient;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUserInfo(String login) {
        GitHubUser gitHubUser = apiClient.getGitHubUser(login);

        int followers = gitHubUser.getFollowers();
        int publicRepos = gitHubUser.getPublicRepos();
        double calculations = 6.0 / followers * (2 + publicRepos);

        userRepository.incrementRequestCount(login);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(gitHubUser.getId());
        userDTO.setLogin(gitHubUser.getLogin());
        userDTO.setName(gitHubUser.getName());
        userDTO.setType(gitHubUser.getType());
        userDTO.setAvatarUrl(gitHubUser.getAvatarUrl());
        userDTO.setCreatedAt(gitHubUser.getCreatedAt());
        userDTO.setCalculations(String.valueOf(calculations));

        return userDTO;
    }
}
