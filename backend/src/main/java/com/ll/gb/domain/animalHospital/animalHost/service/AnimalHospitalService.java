package com.ll.gb.domain.animalHospital.animalHost.service;

import com.ll.gb.domain.address.address.service.AddressService;
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
    public static class AnimalHospitalDto {
        private String name;
        private String lotBasedAddress;
        private String roadBasedAddress;
        private Double longitude;
        private Double latitude;

        public AnimalHospitalDto(String name, String lotBasedAddress, String roadBasedAddress, Double longitude, Double latitude) {
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
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

    public List<AnimalHospitalDto> findItemsFromDataGoKr() {
        List<AnimalHospitalDto> animalHospitalDtos = new ArrayList<>();
        List<com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService> services = getAnimalHospitalServices();

        services.forEach(service ->
                processAnimalHospitalService(service, animalHospitalDtos)
        );

        return animalHospitalDtos;
    }

    private List<com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService> getAnimalHospitalServices() {
        return List.of(
                daejeon_Yuseong_animalHospitalService,
                daejeon_Daedeok_animalHospitalService,
                daejeon_Seo_animalHospitalService,
                daejeon_Jung_animalHospitalService,
                daejeon_Dong_animalHospitalService
        );
    }

    private void processAnimalHospitalService(
            com.ll.gb.domain.animalHospital.proxy.dataGoKr.service.AnimalHospitalService service,
            List<AnimalHospitalDto> animalHospitalDtos
    ) {
        service.getAnimalHospitals(1, 50).getContent().forEach(dto -> {
            AddressService.Coordinate coordinate = AddressService.Coordinate.empty();

            if (coordinate.isNull() && !dto.getRoadBasedAddress().isBlank()) {
                coordinate = addressService.roadBasedAddressToCoordinate(dto.getRoadBasedAddress());
            }

            if (coordinate.isNull() && !dto.getLotBasedAddress().isBlank()) {
                coordinate = addressService.roadBasedAddressToCoordinate(dto.getLotBasedAddress());
            }

            animalHospitalDtos.add(new AnimalHospitalDto(
                    dto.getName(),
                    dto.getLotBasedAddress(),
                    dto.getRoadBasedAddress(),
                    coordinate.getLongitude(),
                    coordinate.getLatitude()
            ));
        });
    }
}
