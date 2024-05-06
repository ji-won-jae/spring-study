package com.springstudy.springsociallogin.common;

import lombok.Getter;

@Getter
public enum SocialType {
    KAKAO("카카오"),
    GOOGLE("구글"),
    APPLE("애플");

    final String value;

    SocialType(final String value) {
        this.value = value;
    }
}
