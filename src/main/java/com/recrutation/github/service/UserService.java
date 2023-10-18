package com.recrutation.github.service;

import com.recrutation.github.model.dto.UserDTO;

public interface UserService {
    UserDTO getUserInfo(String login);

}
