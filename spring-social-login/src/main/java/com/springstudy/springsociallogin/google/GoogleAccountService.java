package com.springstudy.springsociallogin.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleAccountService {
    @Value("${google.api.clientIosId}")
    private String googleClientIosId;

    @Value("${google.api.clientAndroidId}")
    private String googleClientAndroidId;

    public GoogleAccountDto getProfile(String identityToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(Arrays.asList(googleClientAndroidId, googleClientIosId))
                .build();

        // Verify id token
        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(identityToken);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Google authentication failed");
        }

        // If id token is null(invalid), throw exception
        if (idToken == null) {
            throw new RuntimeException("Google authentication failed");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        return GoogleAccountDto.of(payload.getSubject(), payload.getEmail());
    }
}
