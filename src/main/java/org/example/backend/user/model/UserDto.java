package org.example.backend.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class SignupReq {
        private String email;
        private String name;
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .role("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class LoginReq {
        private String email;
        private String password;
    }
}
