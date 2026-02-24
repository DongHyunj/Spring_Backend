package org.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.example.backend.user.model.AuthUserDetails;
import org.example.backend.user.model.UserDto;
import org.example.backend.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto.SignupReq dto) {
        userService.signup(dto);
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto.LoginReq dto) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);
            AuthUserDetails user = (AuthUserDetails)authentication.getPrincipal();

            String jwt = JwtUtil.createToken(user.getIdx(), user.getUsername(), user.getRole());
            return ResponseEntity.ok().header("Set-Cookie", "ATOKEN=" + jwt + "; Path=/").build();

        } catch (DisabledException e) {
            return ResponseEntity.badRequest().body("이메일 인증을 먼저 완료해 주세요.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity emailVerify(String uuid) {
        userService.emailVerify(uuid);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:5173/user/login"))
                .build();
    }
}
