package com.recrutation.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "GitHub user not found!")
public class GitHubApiUserNotFoundException extends RuntimeException {
}
