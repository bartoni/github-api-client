package com.recrutation.github.service;

import com.recrutation.github.exception.GitHubApiException;
import com.recrutation.github.exception.GitHubApiUnauthorizedException;
import com.recrutation.github.exception.GitHubApiUserNotFoundException;
import com.recrutation.github.model.GitHubUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GitHubApiClientTest {

    private GitHubApiClient gitHubApiClient;

    private RestTemplate restTemplateMock;

    public static final String URL = "localhost:8080/";
    public static final String LOGIN = "testuser";

    @BeforeEach
    void setup() {
        this.restTemplateMock = Mockito.mock(RestTemplate.class);
        this.gitHubApiClient = new GitHubApiClient(restTemplateMock, URL, "test");
    }

    @Test
    void testGetGitHubUser_Success() throws GitHubApiException {
        String apiUrl = URL + LOGIN;
        GitHubUser expectedGitHubUser = new GitHubUser();
        expectedGitHubUser.setLogin(LOGIN);

        ResponseEntity<GitHubUser> responseEntity = new ResponseEntity<>(expectedGitHubUser, HttpStatus.OK);
        when(restTemplateMock.exchange(any(), eq(HttpMethod.GET), any(), eq(GitHubUser.class))).thenReturn(responseEntity);

        GitHubUser actualGitHubUser = gitHubApiClient.getGitHubUser(LOGIN);

        assertEquals(expectedGitHubUser.getLogin(), actualGitHubUser.getLogin());
        verify(restTemplateMock, times(1)).exchange(eq(URI.create(apiUrl)), eq(HttpMethod.GET), any(), eq(GitHubUser.class));
    }

    @Test
    void testGetGitHubUser_UserNotFound() {
        String login = "nonexistentuser";
        String apiUrl = URL + login;
        when(restTemplateMock.exchange(eq(URI.create(apiUrl)), eq(HttpMethod.GET), any(), eq(GitHubUser.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(GitHubApiUserNotFoundException.class, () -> gitHubApiClient.getGitHubUser(login));
    }

    @Test
    void testGetGitHubUser_Unauthorized() {
        String apiUrl = URL + LOGIN;
        when(restTemplateMock.exchange(eq(URI.create(apiUrl)), eq(HttpMethod.GET), any(), eq(GitHubUser.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        assertThrows(GitHubApiUnauthorizedException.class, () -> gitHubApiClient.getGitHubUser(LOGIN));
    }

    @Test
    void testGetGitHubUser_OtherError() {
        String apiUrl = URL + LOGIN;
        when(restTemplateMock.exchange(eq(URI.create(apiUrl)), eq(HttpMethod.GET), any(), eq(GitHubUser.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(GitHubApiException.class, () -> gitHubApiClient.getGitHubUser(LOGIN));
    }
}