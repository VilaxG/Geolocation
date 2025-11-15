package com.examen.geolocation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.geolocation.dtos.LocationDTO;
import com.examen.geolocation.entities.LocationEntity;
import com.examen.geolocation.repositories.ILocationRepository;
import com.examen.geolocation.utilities.LocationUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocationService {
    @Autowired
    ILocationRepository iLocationRepository;

    @Autowired
    LocationUtility locationUtility;

    public Long saveAndFlush(LocationDTO locationDTO) {
        if (locationDTO == null) {
            return null;
        }
        LocationEntity locationEntity = locationUtility.DtoToEntity(locationDTO);
        if (locationUtility == null) {
            return null;
        }
        return locationUtility.entityToDTO(iLocationRepository.saveAndFlush(locationEntity)).getId();
    };

    public List<LocationDTO> getAllIp() {
        List<LocationEntity> entities = iLocationRepository.findAll();

        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return locationUtility.entityToPrintList(entities);
    }
    
    public LocationDTO getIp(String ip) {
        log.info("Obteniendo ip " + ip);
        Optional<LocationEntity> optional = iLocationRepository.findByIp(ip);

        if (optional == null || optional.isEmpty()) {
            return null;
        }
        return locationUtility.entityToDTO(optional.get());
    }

    public Boolean deleteIp(Long id) {
        if (!iLocationRepository.findById(id).isPresent()) {
            return false;
        }
        iLocationRepository.deleteById(id);
        return true;
    }

}
