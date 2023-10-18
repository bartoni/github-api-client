package com.github.apiclient.controller;

import com.github.apiclient.model.dto.UserDTO;
import com.github.apiclient.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@Validated
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
     * @return UserDTO containing publicly available information about the user with custom calculations field
     */
    @GetMapping("/{login}")
    public UserDTO getUserInfo(@PathVariable("login") @NotBlank String login) {
        logger.info("Received request to get user info for login: {}", login);

        return userService.getUserInfo(login);
    }
}
