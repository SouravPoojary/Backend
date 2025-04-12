package com.example.Backend.response;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




public class AuthenticationResponse {
    private String token;
    private String role;

    private AuthenticationResponse() {
//        this.token = token;
//        this.role = role;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Static builder method
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    // Builder class
    public static class AuthenticationResponseBuilder {
        private String token;
        private String role;

        public AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public AuthenticationResponse build() {
            AuthenticationResponse response = new AuthenticationResponse();
            response.token = this.token;
            response.role = this.role;
            return response;
        }
    }

}
