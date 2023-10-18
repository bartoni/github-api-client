package com.recrutation.github.controller;

import com.recrutation.github.model.dto.UserDTO;
import com.recrutation.github.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user information for a GitHub user.
     *
     * @param login GitHub user account login.
     * @return UserDTO containing publicly available information about the user.
     */
    @GetMapping("/users/{login}")
    public UserDTO getUserInfo(@PathVariable("login") String login) {
        logger.info("Received request to get user info for login: {}", login);

        return userService.getUserInfo(login);
    }
}
