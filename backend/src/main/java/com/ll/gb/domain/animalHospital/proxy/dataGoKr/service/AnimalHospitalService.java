package com.ll.gb.domain.animalHospital.proxy.dataGoKr.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public interface AnimalHospitalService {
    @Getter
    @ToString
    class AnimalHospitalDto {
        private String name;
        private String lotBasedAddress;
        private String roadBasedAddress;

        public AnimalHospitalDto(String name, String lotBasedAddress, String roadBasedAddress) {
            if (name == null) {
                name = "";
            }

            if (lotBasedAddress == null) {
                lotBasedAddress = "";
            }

            if (roadBasedAddress == null) {
                roadBasedAddress = "";
            }

            this.name = name;
            this.lotBasedAddress = lotBasedAddress;
            this.roadBasedAddress = roadBasedAddress;
        }
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
