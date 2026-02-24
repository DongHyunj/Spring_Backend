package org.example.backend.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.backend.user.model.EmailVerify;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailVerifyRepository emailVerifyRepository;

    public void sendSignupVerification(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String uuid = UUID.randomUUID().toString();
            String link = "http://localhost:5173/verify?uuid=" + uuid;

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("🎉 [비욘드 서비스] 회원가입 이메일 인증을 완료해 주세요!");

            String htmlContents = buildHtmlContent(link);

            helper.setText(htmlContents, true);
            mailSender.send(message);

            EmailVerify emailVerify = EmailVerify.builder()
                    .email(email)
                    .uuid(uuid)
                    .build();

            emailVerifyRepository.save(emailVerify);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }

    private String buildHtmlContent(String link) {
        return """
                <div style="font-family: 'Apple SD Gothic Neo', 'sans-serif'; background-color: #f9f9f9; padding: 40px; text-align: center;">
                    <div style="max-width: 500px; margin: 0 auto; background-color: #ffffff; padding: 40px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
                        <h2 style="color: #333333; margin-bottom: 20px;">환영합니다! 🎉</h2>
                        <p style="color: #666666; font-size: 16px; line-height: 1.6; margin-bottom: 30px;">
                            저희 서비스에 가입해 주셔서 진심으로 감사드립니다.<br>
                            아래 <b>'이메일 인증하기'</b> 버튼을 클릭하여 가입을 완료해 주세요.
                        </p>
                        <a href="%s" style="display: inline-block; background-color: #2563EB; color: #ffffff; text-decoration: none; padding: 14px 28px; border-radius: 6px; font-weight: bold; font-size: 16px;">
                            이메일 인증하기
                        </a>
                        <p style="color: #999999; font-size: 12px; margin-top: 30px;">
                            본 메일은 발신 전용입니다. 만약 본인이 가입을 요청하지 않으셨다면 이 메일을 무시해 주세요.
                        </p>
                    </div>
                </div>
                """.formatted(link);
    }
}
