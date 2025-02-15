package unius.application_member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @GetMapping("/info/get/sendBookCount")
    public ResponseEntity<GetSendBookCountDto.Response> getSendBookCount(
            @RequestHeader("X-User-Id-Header") String id) {
        return ResponseEntity.ok(memberService.getSendBookCount(id));
    }

    @GetMapping("/info/get/receiveBookCount")
    public ResponseEntity<GetReceiveBookCountDto.Response> getReceiveBookCount(
            @RequestHeader("X-User-Id-Header") String id) {
        return ResponseEntity.ok(memberService.getReceiveBookCount(id));
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
            @RequestParam String user) {
        return ResponseEntity.ok(memberService.getBookshelfInfo(user));
    }

    @GetMapping("/bookshelf/get/random")
    public ResponseEntity<List<GetRandomBookshelfInfoDto.Response>> getRandomBookshelfInfo() {
        return ResponseEntity.ok(memberService.getRandomBookshelfInfo());
    }

    @PostMapping("/book/create")
    public ResponseEntity<?> createBook(
            @RequestHeader(value = "X-User-Id-Header", required = false) String id,
            @RequestPart MultipartFile bookImage,
            @RequestPart @Valid CreateBookDto.Request request) {
        return ResponseEntity.ok(memberService.createBook(id, bookImage, request));
    }

    @GetMapping("/book/get")
    public ResponseEntity<List<GetBookshelfBookListDto.Response>> getBookshelfBookList(
            @RequestParam String uuid,
            @RequestParam(required = false) Long cursorId) {
        return ResponseEntity.ok(memberService.getBookshelfBookList(uuid, cursorId));
    }

    @GetMapping("/book/get/{bookId}")
    public ResponseEntity<GetBookInfoDto.Response> getBookInfo(
            @RequestHeader(value = "X-User-Id-Header", required = false) String id,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(memberService.getBookDetail(id, bookId));
    }

    @DeleteMapping("/book/delete/{bookId}")
    public ResponseEntity<Void> deleteBook(
            @RequestHeader("X-User-Id-Header") String id,
            @PathVariable Long bookId) {
        memberService.deleteBook(id, bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mybook/send")
    public ResponseEntity<List<GetMySendBookDto.Response>> getMySendBookList(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestParam(required = false) Long cursorId,
            @RequestParam String order) {
        return ResponseEntity.ok(memberService.getMySendBookList(id, cursorId, order));
    }

    @GetMapping("/mybook/receive")
    public ResponseEntity<List<GetMyReceiveBookDto.Response>> getMyReceiveBookList(
            @RequestHeader("X-User-Id-Header") String id,
            @RequestParam(required = false) Long cursorId,
            @RequestParam String order) {
        return ResponseEntity.ok(memberService.getMyReceiveBookList(id, cursorId, order));
    }
}
