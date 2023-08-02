package com.ua.money24.model;

public record Rate(int id, int groupID, int regionID, String currCode, int currId, String type, double rate,
                   double prevRate, String status, String date) {
}