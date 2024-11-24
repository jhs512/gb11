package com.ll.gb.domain.animalHospital.proxy.dataGoKr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class Daejeon_Jung_AnimalHospitalService implements AnimalHospitalService {
    @Value("${custom.dataGoKr.animalHospitals.daejeon-si.jung-gu.baseUrl}")
    private String baseUrl;

    @Value("${custom.dataGoKr.animalHospitals.daejeon-si.jung-gu.queryString}")
    private String queryString;

    @Value("${custom.dataGoKr.animalHospitals.daejeon-si.jung-gu.apiKey}")
    private String apiKey;

    public Pageable getAnimalHospitals(int page, int itemsPerPage) {
        RestClient restClient = RestClient.create();

        Map body = restClient
                .get()
                .uri(
                        baseUrl + "?" + queryString,
                        page,
                        itemsPerPage,
                        apiKey
                )
                .retrieve()
                .body(Map.class);

        int totalItems = (int) body.getOrDefault("matchCount", 0);

        List<Map> contentMap = (List<Map>) body.getOrDefault("data", List.of());
        List<AnimalHospitalDto> content = contentMap.stream()
                .map(row -> new AnimalHospitalDto(
                        (String) row.getOrDefault("사업장명", ""),
                        (String) row.getOrDefault("소재지전체주소", ""),
                        (String) row.getOrDefault("도로명전체주소", "")
                )).toList();

        return new Pageable(
                page,
                itemsPerPage,
                totalItems,
                content
        );
    }
}
