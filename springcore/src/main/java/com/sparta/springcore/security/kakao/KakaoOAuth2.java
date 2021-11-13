package com.sparta.springcore.security.kakao;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOAuth2 {
    public KakaoUserInfo getUserInfo(String authorizedCode) {
        String accessToken = getAccessToken(authorizedCode);
        KakaoUserInfo kakaoUserInfo = getUserInfoByToken(accessToken);

        return kakaoUserInfo;
    }

    private String getAccessToken(String authorizedCode) {
        // http header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // http body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", System.getenv("KAKAO_REST_API_KEY"));
        params.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        params.add("code", authorizedCode);

        // header + body
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // http request
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, kakaoTokenRequest, String.class);

        // json 'string' -> json 'object'
        String tokenJson = response.getBody();
        JSONObject rjson = new JSONObject(tokenJson);
        String accessToken = rjson.getString("access_token");

        return accessToken;
    }

    private KakaoUserInfo getUserInfoByToken(String accessToken) {
        // http header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // header (+ body)
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class);

        JSONObject body = new JSONObject(response.getBody());
        Long id = body.getLong("id");
        String email = body.getJSONObject("kakao_account").getString("email");
        String nickname = body.getJSONObject("properties").getString("nickname");

        return new KakaoUserInfo(id, email, nickname);
    }
}
