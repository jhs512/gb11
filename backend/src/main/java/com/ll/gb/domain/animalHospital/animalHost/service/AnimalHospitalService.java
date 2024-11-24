package com.ll.gb.domain.animalHospital.animalHost.service;

import com.ll.gb.domain.address.address.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AnimalHospitalService {
    private final AddressService addressService;

    @Autowired
    @Qualifier("daejeon_Yuseong_AnimalHospitalService")
    private com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService daejeon_Yuseong_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Daedeok_AnimalHospitalService")
    private com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService daejeon_Daedeok_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Seo_AnimalHospitalService")
    private com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService daejeon_Seo_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Jung_AnimalHospitalService")
    private com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService daejeon_Jung_animalHospitalService;

    @Autowired
    @Qualifier("daejeon_Dong_AnimalHospitalService")
    private com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService daejeon_Dong_animalHospitalService;

    @Getter
    @AllArgsConstructor
    public static class AnimalHospitalDto {
        private String name;
        private String lotBasedAddress;
        private String roadBasedAddress;
        private Double longitude;
        private Double latitude;
    }

    public List<AnimalHospitalDto> findItemsFromDataGoKr() {
        List<AnimalHospitalDto> animalHospitalDtos = new ArrayList<>();

        daejeon_Yuseong_animalHospitalService.getAnimalHospitals(1, 50).getContent().forEach(animalHospitalDto -> {
            AddressService.Coordinate coordinate = addressService.roadBasedAddressToCoordinate(animalHospitalDto.getRoadBasedAddress());

            animalHospitalDtos.add(new AnimalHospitalDto(
                    animalHospitalDto.getName(),
                    animalHospitalDto.getLotBasedAddress(),
                    animalHospitalDto.getRoadBasedAddress(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude()
            ));
        });

        daejeon_Daedeok_animalHospitalService.getAnimalHospitals(1, 50).getContent().forEach(animalHospitalDto -> {
            AddressService.Coordinate coordinate = addressService.roadBasedAddressToCoordinate(animalHospitalDto.getRoadBasedAddress());

            animalHospitalDtos.add(new AnimalHospitalDto(
                    animalHospitalDto.getName(),
                    animalHospitalDto.getLotBasedAddress(),
                    animalHospitalDto.getRoadBasedAddress(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude()
            ));
        });

        daejeon_Seo_animalHospitalService.getAnimalHospitals(1, 50).getContent().forEach(animalHospitalDto -> {
            AddressService.Coordinate coordinate = addressService.roadBasedAddressToCoordinate(animalHospitalDto.getRoadBasedAddress());

            animalHospitalDtos.add(new AnimalHospitalDto(
                    animalHospitalDto.getName(),
                    animalHospitalDto.getLotBasedAddress(),
                    animalHospitalDto.getRoadBasedAddress(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude()
            ));
        });

        daejeon_Jung_animalHospitalService.getAnimalHospitals(1, 50).getContent().forEach(animalHospitalDto -> {
            AddressService.Coordinate coordinate = addressService.roadBasedAddressToCoordinate(animalHospitalDto.getRoadBasedAddress());

            animalHospitalDtos.add(new AnimalHospitalDto(
                    animalHospitalDto.getName(),
                    animalHospitalDto.getLotBasedAddress(),
                    animalHospitalDto.getRoadBasedAddress(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude()
            ));
        });

        daejeon_Dong_animalHospitalService.getAnimalHospitals(1, 50).getContent().forEach(animalHospitalDto -> {
            AddressService.Coordinate coordinate = addressService.roadBasedAddressToCoordinate(animalHospitalDto.getRoadBasedAddress());

            animalHospitalDtos.add(new AnimalHospitalDto(
                    animalHospitalDto.getName(),
                    animalHospitalDto.getLotBasedAddress(),
                    animalHospitalDto.getRoadBasedAddress(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude()
            ));
        });

        return animalHospitalDtos;
    }
}
