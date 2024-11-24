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

        public boolean isNull() {
            return latitude == null || longitude == null;
        }

        public static Coordinate empty() {
            return new Coordinate(null, null);
        }
    }

    public Coordinate roadBasedAddressToCoordinate(String roadBasedAddress) {
        roadBasedAddress = cleanAddressUntilStable(roadBasedAddress);

        if (isImpossibleToFind(roadBasedAddress)) {
            return Coordinate.empty();
        }

        RestClient restClient = RestClient.create();

        Map body = restClient
                .get()
                .uri("https://dapi.kakao.com/v2/local/search/address?query=" + roadBasedAddress)
                .header("Authorization", "KakaoAK " + kakaoAppRestApiKey)
                .retrieve()
                .body(Map.class);

        List rows = (List) body.getOrDefault("documents", List.of());

        if (rows.isEmpty()) {
            return Coordinate.empty();
        }

        Map firstRow = (Map) rows.get(0);

        double longitude = Double.parseDouble((String) firstRow.getOrDefault("x", "0"));
        double latitude = Double.parseDouble((String) firstRow.getOrDefault("y", "0"));

        return new Coordinate(latitude, longitude);
    }

    private boolean isImpossibleToFind(String address) {
        return address.contains("사서함") || address.contains("우편함");
    }

    public String cleanAddressUntilStable(String address) {
        while (true) {
            String cleanedAddress = cleanAddressOnce(address);

            // 조건에 해당하지 않아 더 이상 변하지 않으면 종료
            if (cleanedAddress.equals(address)) {
                return cleanedAddress;
            }

            // 반복 진행을 위해 현재 주소를 갱신
            address = cleanedAddress;
        }
    }

    private String cleanAddressOnce(String address) {
        // 공백 기준으로 나눔
        String[] words = address.split("\\s+");
        if (words.length == 0) {
            return address; // 입력이 비어있으면 그대로 반환
        }

        // 마지막 단어 가져오기
        String lastWord = words[words.length - 1];

        // 마지막 단어가 "(...)" 형식이거나 "호" 또는 "층"으로 끝나면 제거
        if (lastWord.matches("\\(.*\\)") || lastWord.endsWith("호") || lastWord.endsWith("층")) {
            // 마지막 단어를 제외한 부분만 다시 조합
            return String.join(" ", java.util.Arrays.copyOf(words, words.length - 1));
        }

        // 조건에 해당하지 않으면 원래 주소 반환
        return address;
    }
}
