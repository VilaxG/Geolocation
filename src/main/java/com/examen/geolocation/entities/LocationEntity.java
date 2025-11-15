package com.examen.geolocation.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="ipTable", schema="public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ip")
    private String ip;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "countryCode")
    private String countryCode;
    @Column(name = "countryName")
    private String countryName;
    @Column(name = "phoneCode")
    private String phoneCode;
    @Column(name = "continentCode")
    private String continentCode;
}
