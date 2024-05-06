package com.springstudy.springsociallogin.kakao;

import lombok.Data;

@Data
public class KakaoAccountDto {
    private String id;
    private String email;

    public static KakaoAccountDto of(String id, String email) {
        KakaoAccountDto kakao = new KakaoAccountDto();
        kakao.setId(id);
        kakao.setEmail(email);

        return kakao;
    }

}
