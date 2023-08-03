package com.ua.money24.model.response;


import java.util.List;

public record ExecAsPublicResponse(int statusCode, String statusMessage, Result result) {
    public record Result(List<Rate> rates, String message) {
        public record Rate(int id, int groupID, int regionID, String currCode, int currId, String type, double rate,
                           double prevRate, String status, String date) {
        }
    }
}
