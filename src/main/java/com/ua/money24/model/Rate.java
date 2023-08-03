package com.ua.money24.model;

public record Rate (int id, int regionId, String currencyCode, int currencyId, double buyRate, double sellRate) {
}
