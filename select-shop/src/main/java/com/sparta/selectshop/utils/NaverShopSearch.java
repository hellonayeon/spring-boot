package com.sparta.selectshop.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class NaverShopSearch {
    public String search() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Naver-Client-Id", System.getenv("X_NAVER_CLIENT_ID"));
        headers.add("X-Naver-Client-Secret", System.getenv("X_NAVER_CLIENT_SECRET"));
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        // rest.exchange() automate UTF Encoding
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=아디다스&sort=asc", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public static void main(String[] args) {
        NaverShopSearch naverShopSearch = new NaverShopSearch();
        naverShopSearch.search();
    }
}
