package com.examen.geolocation.utilities;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.examen.geolocation.dtos.LocationDTO;
import com.examen.geolocation.entities.LocationEntity;

@Component
public class LocationUtility {
    
    public LocationDTO entityToDTO(LocationEntity locationEntity){
        if (locationEntity == null) {
            return null;
        }
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationEntity.getId());
        locationDTO.setIp(locationEntity.getIp());
        locationDTO.setLatitude(locationEntity.getLatitude());
        locationDTO.setLongitude(locationEntity.getLongitude());
        locationDTO.setCountryCode(locationEntity.getCountryCode());
        locationDTO.setCountryName(locationEntity.getCountryName());
        locationDTO.setPhoneCode(locationEntity.getPhoneCode());
        locationDTO.setContinentCode(locationEntity.getContinentCode());

        return locationDTO;
    }

    public LocationEntity DtoToEntity(LocationDTO locationDTO){
        if (locationDTO == null) {
            return null;
        }
        LocationEntity entity = new LocationEntity();
        entity.setId(locationDTO.getId());
        entity.setIp(locationDTO.getIp());
        entity.setLatitude(locationDTO.getLatitude());
        entity.setLongitude(locationDTO.getLongitude());
        entity.setCountryCode(locationDTO.getCountryCode());
        entity.setCountryName(locationDTO.getCountryName());
        entity.setPhoneCode(locationDTO.getPhoneCode());
        entity.setContinentCode(locationDTO.getContinentCode());

        return entity;
    }

   public List<LocationDTO> entityToPrintList(List<LocationEntity> locationsEntities){
        List<LocationDTO> dtos = locationsEntities.stream()
                              .map(this::entityToDTO)
                              .collect(Collectors.toList());
        return dtos;
    }
}
