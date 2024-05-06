package com.springstudy.springsociallogin.common;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SocialAccountController {

    private final SocialAccountService socialAccountService;

    @PostMapping("/social-login")
    public ResponseEntity<String> login(@RequestBody LoginReqDto loginReqDto) {
        boolean isLogin = socialAccountService.socialLogin(loginReqDto);
        return ResponseEntity.ok("test: " + isLogin);
    }
}
