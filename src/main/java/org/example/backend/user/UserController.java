package org.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.example.backend.user.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserSerivce userSerivce;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto.SignupReq dto) {
        userSerivce.signup(dto);
        return ResponseEntity.ok("성공");
    }
}
