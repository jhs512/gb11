package com.ll.gb.domain.animalHospital.proxy.dataGoKr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class Daejeon_Dong_AnimalHospitalService implements AnimalHospitalService {
    @Value("${custom.dataGoKr.animalHospitals.daejeon-si.dong-gu.baseUrl}")
    private String baseUrl;

    @Value("${custom.dataGoKr.animalHospitals.daejeon-si.dong-gu.queryString}")
    private String queryString;

    @Value("${custom.dataGoKr.animalHospitals.daejeon-si.dong-gu.apiKey}")
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
                        (String) row.getOrDefault("업소명", ""),
                        "",
                        (String) row.getOrDefault("사업장소재지(도로명)", "")
                )).toList();

        return new Pageable(
                page,
                itemsPerPage,
                totalItems,
                content
        );
    }
}
