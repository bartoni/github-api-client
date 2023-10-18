package com.github.apiclient.service.impl;

import com.github.apiclient.model.Login;
import com.github.apiclient.model.dto.UserDTO;
import com.github.apiclient.repository.LoginRepository;
import com.github.apiclient.service.GitHubApiClient;
import com.github.apiclient.service.UserService;
import com.github.apiclient.model.GitHubUser;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final GitHubApiClient apiClient;
    private final LoginRepository loginRepository;

    public UserServiceImpl(GitHubApiClient apiClient, LoginRepository loginRepository) {
        this.apiClient = apiClient;
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDTO getUserInfo(String login) {
        GitHubUser gitHubUser = apiClient.getGitHubUser(login);

        loginRepository.findById(login).ifPresentOrElse(
                existingLogin -> loginRepository.save(existingLogin.incrementRequestCount()),
                () -> loginRepository.save(new Login(login)));

        int followers = gitHubUser.getFollowers();
        int publicRepos = gitHubUser.getPublicRepos();
        double calculations = 6.0 / followers * (2 + publicRepos);

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
