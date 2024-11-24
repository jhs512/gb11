package com.ll.gb.domain.animalHospital.proxy.dataGoKr.controller;

import com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/animalHospitals/proxy/dataGoKr")
@RequiredArgsConstructor
public class Ap1V1AnimalHospitalController {
    @Autowired
    @Qualifier("daejeon_Yuseong_AnimalHospitalService")
    private AnimalHospitalService daejeon_Yuseong_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Daedeok_AnimalHospitalService")
    private AnimalHospitalService daejeon_Daedeok_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Seo_AnimalHospitalService")
    private AnimalHospitalService daejeon_Seo_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Jung_AnimalHospitalService")
    private AnimalHospitalService daejeon_Jung_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Dong_AnimalHospitalService")
    private AnimalHospitalService daejeon_Dong_animalHospitalService;

    @GetMapping("/{sido}/{gugun}")
    public AnimalHospitalService.Pageable getItems(
            @PathVariable String sido,
            @PathVariable String gugun,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int itemsPerPage
    ) {
        return findAnimalHospitalServiceBySidoAndGugun(sido, gugun)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sido or gugun"))
                .getAnimalHospitals(page, itemsPerPage);
    }

    private Optional<AnimalHospitalService> findAnimalHospitalServiceBySidoAndGugun(String sido, String gugun) {
        if ("daejeon".equals(sido)) {
            switch (gugun) {
                case "yuseong":
                    return Optional.of(daejeon_Yuseong_animalHospitalService);
                case "daedeok":
                    return Optional.of(daejeon_Daedeok_animalHospitalService);
                case "seo":
                    return Optional.of(daejeon_Seo_animalHospitalService);
                case "jung":
                    return Optional.of(daejeon_Jung_animalHospitalService);
                case "dong":
                    return Optional.of(daejeon_Dong_animalHospitalService);
            }
        }

        return Optional.empty();
    }
}
