package com.github.apiclient.service;

import com.github.apiclient.model.dto.UserDTO;

public interface UserService {
    UserDTO getUserInfo(String login);

}
