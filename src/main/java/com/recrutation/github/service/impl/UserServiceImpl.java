package com.recrutation.github.service.impl;

import com.recrutation.github.model.GitHubUser;
import com.recrutation.github.model.dto.UserDTO;
import com.recrutation.github.repository.UserRepository;
import com.recrutation.github.service.GitHubApiClient;
import com.recrutation.github.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GitHubApiClient apiClient;

    @Autowired
    private UserRepository userRepository;

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
