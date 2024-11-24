package com.ll.gb.domain.address.address.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class AddressService {
    @Value("${custom.kakaoApp.restApiKey}")
    private String kakaoAppRestApiKey;

    @AllArgsConstructor
    @Getter
    public static class Coordinate {
        private final Double latitude;
        private final Double longitude;
    }

    public Coordinate roadBasedAddressToCoordinate(String roadBasedAddress) {
        RestClient restClient = RestClient.create();

        Map body = restClient
                .get()
                .uri("https://dapi.kakao.com/v2/local/search/address?query=" + roadBasedAddress)
                .header("Authorization", "KakaoAK " + kakaoAppRestApiKey)
                .retrieve()
                .body(Map.class);

        List rows = (List) body.getOrDefault("documents", List.of());

        if (rows.isEmpty()) {
            return new Coordinate(null, null);
        }

        Map firstRow = (Map) rows.get(0);

        double longitude = Double.parseDouble((String) firstRow.getOrDefault("x", "0"));
        double latitude = Double.parseDouble((String) firstRow.getOrDefault("y", "0"));

        return new Coordinate(latitude, longitude);
    }
}
