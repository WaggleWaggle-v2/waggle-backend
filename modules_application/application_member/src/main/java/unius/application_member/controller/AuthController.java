package unius.application_member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unius.application_member.service.AuthService;

@Controller
@RequestMapping("/unius/member")
@RequiredArgsConstructor
public class AuthController {

    private static final String GRANT_TYPE = "Bearer ";

    private final AuthService authService;

    @GetMapping("/login/google")
    public ResponseEntity<Void> googleLogin(
            @RequestParam("code") String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, GRANT_TYPE + authService.googleLogin(code));

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }
}
