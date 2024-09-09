package unius.application_member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.application_member.dto.InitializeUserInfoDto;
import unius.application_member.dto.SetBookshelfRevelationDto;
import unius.application_member.dto.SetUserNicknameDto;
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
            @RequestBody @Valid InitializeUserInfoDto.Request request) {
        return ResponseEntity.ok(memberService.initializeUserInfo(Long.parseLong(id), request));
    }

    @PatchMapping("/info/set/nickname")
    public ResponseEntity<SetUserNicknameDto.Response> setUserNickname(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetUserNicknameDto.Request request) {
        return ResponseEntity.ok(memberService.setUserNickname(Long.parseLong(id), request));
    }

    @PatchMapping("/bookshelf/set/revelation")
    public ResponseEntity<Void> setBookshelfRevelation(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetBookshelfRevelationDto.Request request) {
        memberService.setBookshelfRevelation(Long.parseLong(id), request);
        return ResponseEntity.noContent().build();
    }
}
