package com.examen.geolocation.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examen.geolocation.entities.LocationEntity;

public interface ILocationRepository extends JpaRepository<LocationEntity,Long>{
    List<LocationEntity> findAll();
    Optional<LocationEntity> findById(long id);
    LocationEntity saveAndFlush(LocationEntity locationEntity);
    Optional<LocationEntity> findByIp(String ip);
    void deleteById(Long id);
}
