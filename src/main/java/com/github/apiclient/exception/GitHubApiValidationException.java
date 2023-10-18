package com.github.apiclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Login cannot be blank!")
public class GitHubApiValidationException extends RuntimeException {
}
