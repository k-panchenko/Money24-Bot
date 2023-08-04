package com.ua.money24.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rate {
    @Id
    private Integer id;
    private Integer regionId;
    private String currencyCode;
    private Integer currencyId;
    private Double buyRate;
    private Double sellRate;

    public Rate() {
    }

    public Rate(Integer id, Integer regionId, String currencyCode, Integer currencyId, Double buyRate, Double sellRate) {
        this.id = id;
        this.regionId = regionId;
        this.currencyCode = currencyCode;
        this.currencyId = currencyId;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(Double buyRate) {
        this.buyRate = buyRate;
    }

    public Double getSellRate() {
        return sellRate;
    }

    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }
}
