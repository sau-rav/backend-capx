package com.xyz.protos;

import javax.ws.rs.core.Response;

public class AuthResponse {
    private Response.Status responseStatus;
    private String uid;
    private String userName;
    private String email;

    private AuthResponse(Builder builder) {
        this.responseStatus = builder.responseStatus;
        this.uid = builder.uid;
        this.userName = builder.userName;
        this.email = builder.email;
    }

    public Response.Status getResponseStatus() {
        return responseStatus;
    }

    public String getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private Response.Status responseStatus;
        private String uid;
        private String userName;
        private String email;

        public Builder responseStatus(Response.Status responseStatus) {
            this.responseStatus = responseStatus;
            return this;
        }

        public Builder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }
}
