package com.github.apiclient.service;

import com.github.apiclient.exception.GitHubApiException;
import com.github.apiclient.exception.GitHubApiUserNotFoundException;
import com.github.apiclient.exception.GitHubApiUnauthorizedException;
import com.github.apiclient.model.GitHubUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class GitHubApiClient {

    private static final Logger logger = LoggerFactory.getLogger(GitHubApiClient.class);
    public final String usersApiUrl;
    public final String apiToken;
    private final RestTemplate restTemplate;

    public GitHubApiClient(RestTemplate restTemplate,
            @Value("${github.users.api.url}") String usersApiUrl,
            @Value("${github.api.token}") String apiToken) {
        this.restTemplate = restTemplate;
        this.usersApiUrl = usersApiUrl;
        this.apiToken = apiToken;
    }

    public GitHubUser getGitHubUser(String login) throws GitHubApiUserNotFoundException, GitHubApiUnauthorizedException, GitHubApiException {
        String apiUrl = usersApiUrl + login;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        headers.set("Accept", "application/vnd.github+json");
        headers.setBearerAuth(apiToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GitHubUser> responseEntity = restTemplate.exchange(URI.create(apiUrl), HttpMethod.GET, entity, GitHubUser.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            logger.error(e.toString());
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                throw new GitHubApiUserNotFoundException();
            } else if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                throw new GitHubApiUnauthorizedException();
            } else {
                throw new GitHubApiException("Error: HTTP response code - " + e.getStatusCode());
            }
        }
    }
}
