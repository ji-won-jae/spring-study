package com.springstudy.springsociallogin.apple;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleAccountService {

    private final Gson gson;

    private Claims getTokenClaims(ApplePublicKeyResDto applePublicKeyResDto, String identityToken) {
        try {

            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            Map<String, String> header = gson.fromJson(new String(Base64.getDecoder().decode(headerOfIdentityToken), "UTF-8"), Map.class);
            ApplePublicKeyResDto.Key key = applePublicKeyResDto.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                    .orElseThrow(RuntimeException::new);

            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("apple identity token이 만료되었습니다.");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("apple identity token 알고리즘 오류입니다.");
        } catch (Exception e) {
            throw new RuntimeException("apple 간편로그인 인증시 오류입니다.");
        }
    }

    //    애플에서 내려주는 퍼블릭 키 얻는 메소드
    private ApplePublicKeyResDto getAppleKeys() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        URI uri = URI.create("https://appleid.apple.com/auth/keys");
        headers.setContentType(new MediaType("application", "json"));    //Response Header to UTF-8
        ResponseEntity<String> restResponse = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(headers), String.class); //예외처리 추가 필요;

        return gson.fromJson(restResponse.getBody(), ApplePublicKeyResDto.class);
    }

    public AppleAccountDto linkAppleServiceByToken(String identityToken) {
//      apple public key 얻어오기
        ApplePublicKeyResDto applePublicKeyResDto = getAppleKeys();

//        토큰 유효성 검증
        Claims claims = getTokenClaims(applePublicKeyResDto, identityToken);
        String aud = claims.getAudience();
        String iss = claims.getIssuer();

        log.info("aud : {}", aud);
        log.info("iss : {}", iss);
        if (!iss.equals("https://appleid.apple.com") || !aud.equals("com.springstudy.springsociallogin")) {
            throw new RuntimeException();
        }

        return AppleAccountDto.of(claims.getSubject(), claims.get("email", String.class));
    }
}