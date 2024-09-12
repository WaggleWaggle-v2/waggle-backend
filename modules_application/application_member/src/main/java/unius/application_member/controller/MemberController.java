package unius.application_member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unius.application_member.dto.*;
import unius.application_member.service.MemberService;

import java.util.List;

@Controller
@RequestMapping("/unius/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info/me")
    public ResponseEntity<GetMyUserInfoDto.Response> getMyUserInfo(
            @RequestHeader("X-User-Id-Header") String id) {
        return ResponseEntity.ok(memberService.getMyUserInfo(id));
    }

    @PostMapping("/init")
    public ResponseEntity<Void> initializeUserInfo(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid InitializeUserInfoDto.Request request) {
        memberService.initializeUserInfo(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/info/set/nickname")
    public ResponseEntity<SetUserNicknameDto.Response> setUserNickname(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetUserNicknameDto.Request request) {
        return ResponseEntity.ok(memberService.setUserNickname(id, request));
    }

    @PatchMapping("/bookshelf/set/revelation")
    public ResponseEntity<Void> setBookshelfRevelation(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetBookshelfRevelationDto.Request request) {
        memberService.setBookshelfRevelation(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/bookshelf/set/background")
    public ResponseEntity<Void> setBookshelfBackground(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetBookshelfBackgroundDto.Request request) {
        memberService.setBookshelfBackground(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/bookshelf/set/theme")
    public ResponseEntity<Void> setBookshelfTheme(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetBookshelfThemeDto.Request request) {
        memberService.setBookshelfTheme(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/bookshelf/set/introduction")
    public ResponseEntity<Void> setBookshelfIntroduction(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestBody @Valid SetBookshelfIntroductionDto.Request request) {
        memberService.setBookshelfIntroduction(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bookshelf/get")
    public ResponseEntity<GetBookshelfInfoDto.Response> getBookshelfInfo(
            @RequestHeader(value = "X-User-Id-Header", required = false) String id,
            @RequestParam String user) {
        return ResponseEntity.ok(memberService.getBookshelfInfo(id, user));
    }

    @GetMapping("/bookshelf/get/random")
    public ResponseEntity<List<GetRandomBookshelfInfoDto.Response>> getRandomBookshelfInfo() {
        return ResponseEntity.ok(memberService.getRandomBookshelfInfo());
    }
}
