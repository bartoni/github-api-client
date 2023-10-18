package com.github.apiclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized request - check GitHub API token!")
public class GitHubApiUnauthorizedException extends RuntimeException {
}
