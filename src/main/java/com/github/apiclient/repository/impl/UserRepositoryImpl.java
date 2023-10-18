package com.github.apiclient.repository.impl;

import com.github.apiclient.repository.UserRepository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, Integer> requestCounts = new HashMap<>();

    @Override
    public void incrementRequestCount(String login) {
        requestCounts.put(login, requestCounts.getOrDefault(login, 0) + 1);
    }

    @Override
    public int getRequestCountsForLogin(String login) {
        return requestCounts.get(login);
    }
}
