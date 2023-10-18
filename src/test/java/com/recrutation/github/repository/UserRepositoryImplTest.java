package com.recrutation.github.repository;
import com.recrutation.github.repository.impl.UserRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void incrementRequestCount_shouldIncrementCount() {
        String login = "testUser";

        userRepository.incrementRequestCount(login);
        userRepository.incrementRequestCount(login);
        userRepository.incrementRequestCount(login);

        int requestCounts = userRepository.getRequestCountsForLogin(login);

        assertEquals(3, requestCounts);
    }

    @Test
    void incrementRequestCount_withDifferentLogins_shouldIncrementCountsCorrectly() {
        String login1 = "user1";
        String login2 = "user2";

        userRepository.incrementRequestCount(login1);
        userRepository.incrementRequestCount(login1);
        userRepository.incrementRequestCount(login2);

        int requestCountsLogin1 = userRepository.getRequestCountsForLogin(login1);
        int requestCountsLogin2 = userRepository.getRequestCountsForLogin(login2);

        assertEquals(2, requestCountsLogin1);
        assertEquals(1, requestCountsLogin2);
    }
}
