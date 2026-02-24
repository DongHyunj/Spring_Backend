package org.example.backend.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.backend.user.model.AuthUserDetails;
import org.example.backend.user.model.EmailVerify;
import org.example.backend.user.model.User;
import org.example.backend.user.model.UserDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EmailVerifyRepository emailVerifyRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void signup(UserDto.SignupReq dto) {
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

       emailService.sendSignupVerification(dto.getEmail());


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow();

        return AuthUserDetails.from(user);
    }

    public void emailVerify(String uuid) {
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid).orElseThrow();

        User user = userRepository.findByEmail(emailVerify.getEmail()).orElseThrow();

        user.setEnable(true);

        userRepository.save(user);
    }
}
