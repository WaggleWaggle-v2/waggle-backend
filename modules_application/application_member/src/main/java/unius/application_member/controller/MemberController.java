package unius.application_member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.application_member.dto.InitializeUserInfoDto;
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

    @PostMapping("/init")
    public ResponseEntity<InitializeUserInfoDto.Response> initializeUserInfo(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody InitializeUserInfoDto.Request request) {
        return ResponseEntity.ok(memberService.initializeUserInfo(Long.parseLong(id), request));
    }
}
