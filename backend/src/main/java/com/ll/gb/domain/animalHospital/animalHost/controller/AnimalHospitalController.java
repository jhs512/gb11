package com.ll.gb.domain.animalHospital.animalHost.controller;

import com.ll.gb.domain.animalHospital.animalHost.service.AnimalHospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/animalHospitals")
@RequiredArgsConstructor
public class AnimalHospitalController {
    private final AnimalHospitalService animalHospitalService;

    @GetMapping("/fromDataGoKr")
    public List<AnimalHospitalService.AnimalHospitalDto> getItemsFromDataGoKr() {
        return animalHospitalService.findItemsFromDataGoKr();
    }
}
