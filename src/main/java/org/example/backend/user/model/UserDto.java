package org.example.backend.user.model;

import lombok.Getter;

public class UserDto {

    @Getter
    public static class SignupReq {
        private String email;
        private String name;
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .build();
        }
    }
}
