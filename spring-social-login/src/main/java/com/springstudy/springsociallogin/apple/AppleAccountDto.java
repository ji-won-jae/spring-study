package com.springstudy.springsociallogin.apple;

import io.micrometer.common.util.StringUtils;
import lombok.Data;

@Data
public class AppleAccountDto {
    private String id; // sub
    private String email; // email

    public static AppleAccountDto of(String id, String email) {

        AppleAccountDto apple = new AppleAccountDto();
        apple.setId(id);

        if (StringUtils.isNotEmpty(email)) {
            apple.setEmail(email);
        }

        return apple;
    }
}
