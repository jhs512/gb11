package com.ll.gb.domain.animalHospital.proxy.dataGoKr.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface AnimalHospitalService {
    @Getter
    @AllArgsConstructor
    class AnimalHospitalDto {
        private String name;
        private String lotBasedAddress;
        private String roadBasedAddress;
    }

    @AllArgsConstructor
    @Getter
    class Pageable {
        private int page;
        private int itemsPerPage;
        private int totalItems;
        private List<AnimalHospitalDto> content;
    }

    Pageable getAnimalHospitals(int page, int itemsPerPage);
}
