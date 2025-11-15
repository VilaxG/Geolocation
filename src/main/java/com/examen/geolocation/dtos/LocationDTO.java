package com.examen.geolocation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
public class LocationDTO {
    private long id;
    private String ip;
    private String latitude;
    private String longitude;
    private String countryCode;
    private String countryName;
    private String phoneCode;
    private String continentCode;
    
}
