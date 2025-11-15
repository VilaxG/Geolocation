package com.examen.geolocation.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.examen.geolocation.dtos.LocationDTO;
import com.examen.geolocation.response.ResponseBean;
import com.examen.geolocation.services.LocationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins = "*")
public class GeolocationController {

    @Autowired
    LocationService locationService;

    @PostMapping("/saveIp")
    public ResponseBean saveIp(@RequestBody LocationDTO locationDTO) {
        ResponseBean bean = new ResponseBean<>();
        Long id = locationService.saveAndFlush(locationDTO);

        if (id == null) {
            bean.setStatus(300);
            bean.setMessage("Registro Fallido");
            bean.setData(null);
        } else {
            bean.setStatus(200);
            bean.setMessage("Registro Exitoso");
            bean.setData(id);
        }

        return bean;
    }

    @GetMapping("/getAllIp")
    public List<LocationDTO> getAllIp() {
        return locationService.getAllIp();
    }

    @GetMapping("/getIp")
    public LocationDTO getIp(@RequestParam String ip) {
        LocationDTO dto = locationService.getIp(ip);
        return dto;
    }

    @DeleteMapping("/removeIp")
    public ResponseBean removeIp(@RequestParam Long id) {
        ResponseBean bean = new ResponseBean<>();
        Boolean removed = locationService.deleteIp(id);

        if (removed == null || !removed) {
            bean.setStatus(300);
            bean.setMessage("Eliminacion Fallida");
            bean.setData(null);
        } else {
            bean.setStatus(200);
            bean.setMessage("Eliminacion Exitosa");
            bean.setData(id);
        }
        return bean;
    }
}
