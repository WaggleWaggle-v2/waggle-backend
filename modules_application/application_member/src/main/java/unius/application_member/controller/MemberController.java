package unius.application_member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.application_member.service.MemberService;

@Controller
@RequestMapping("/unius/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info/me")
    public ResponseEntity<GetMyUserInfoDto.Response> getMyUserInfo(
            @RequestHeader("X-User-Id-Header") String id) {
        return ResponseEntity.ok(memberService.getMyUserInfo(Long.parseLong(id)));
    }
}
