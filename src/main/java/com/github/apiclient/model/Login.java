package com.github.apiclient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Login {

    @Id
    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "REQUEST_COUNT")
    private int requestCount = 1;

    public Login() {
    }

    public Login(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Login incrementRequestCount() {
        ++this.requestCount;
        return this;
    }
}
