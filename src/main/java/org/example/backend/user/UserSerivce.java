package org.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.example.backend.user.model.UserDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSerivce {
    private final UserRepository userRepository;

    public void signup(UserDto.SignupReq dto) {
        userRepository.save(dto.toEntity());
    }
}
