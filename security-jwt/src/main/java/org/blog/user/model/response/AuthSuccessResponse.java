package org.blog.user.model.response;

public class AuthSuccessResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

    public AuthSuccessResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public AuthSuccessResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
