package com.github.apiclient.repository;

public interface UserRepository {
    void incrementRequestCount(String login);
    int getRequestCountsForLogin(String login);
}