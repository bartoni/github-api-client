package com.recrutation.github.repository;

public interface UserRepository {
    void incrementRequestCount(String login);
    int getRequestCountsForLogin(String login);
}