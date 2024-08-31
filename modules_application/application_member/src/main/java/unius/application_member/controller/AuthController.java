package unius.application_member.controller;

import lombok.RequiredArgsConstructor;
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

    private final AuthService authService;

    @GetMapping("/login/google")
    public ResponseEntity<String> googleLogin(
            @RequestParam("code") String code) {
        return ResponseEntity.ok(authService.googleLogin(code));
    }
}
