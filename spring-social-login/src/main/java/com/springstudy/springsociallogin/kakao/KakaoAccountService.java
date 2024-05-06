package com.springstudy.springsociallogin.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAccountService {

    @Value("${kakao.api.meUri}")
    private String meUri;

    @Transactional
    public KakaoAccountDto getMe(String accessToken) {

        log.info("> accessToken ::: {}", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);


        URI uri = UriComponentsBuilder.fromHttpUrl(meUri).build().toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);

        Map meResult = response.getBody();
        String id = meResult.get("id").toString();
        Map account = (Map) meResult.get("kakao_account");
        String email = account.get("email") != null ? account.get("email").toString() : null;

        return KakaoAccountDto.of(id, email);
    }
}
