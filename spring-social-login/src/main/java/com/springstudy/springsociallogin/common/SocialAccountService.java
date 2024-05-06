package com.springstudy.springsociallogin.common;

import com.springstudy.springsociallogin.apple.AppleAccountDto;
import com.springstudy.springsociallogin.apple.AppleAccountService;
import com.springstudy.springsociallogin.google.GoogleAccountDto;
import com.springstudy.springsociallogin.google.GoogleAccountService;
import com.springstudy.springsociallogin.kakao.KakaoAccountDto;
import com.springstudy.springsociallogin.kakao.KakaoAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialAccountService {

    private final AppleAccountService appleAccountService;

    private final KakaoAccountService kakaoAccountService;

    private final GoogleAccountService googleAccountService;

    @Transactional
    public boolean socialLogin(LoginReqDto loginReqDto) {
        boolean checked = false;

        if (loginReqDto.getSocialType().equals(SocialType.GOOGLE)) {
            GoogleAccountDto google = googleAccountService.getProfile(loginReqDto.getIdentityToken());
            if (google != null) checked = true;
        }
        if (loginReqDto.getSocialType().equals(SocialType.KAKAO)) {
            KakaoAccountDto kakao = kakaoAccountService.getMe(loginReqDto.getIdentityToken());
            if (kakao != null) checked = true;
        }
        if (loginReqDto.getSocialType().equals(SocialType.APPLE)) {
            AppleAccountDto apple = appleAccountService.linkAppleServiceByToken(loginReqDto.getIdentityToken());
            if (apple != null) checked = true;
        }

        return checked;
    }
}
