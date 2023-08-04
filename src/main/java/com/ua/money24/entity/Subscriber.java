package com.ua.money24.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Subscriber {
    @Id
    private Integer id;
    private Integer regionId;

    public Subscriber() {
    }

    public Subscriber(Integer id, Integer regionId) {
        this.id = id;
        this.regionId = regionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }
}
