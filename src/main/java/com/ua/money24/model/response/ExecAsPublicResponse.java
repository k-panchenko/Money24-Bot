package com.ua.money24.model.response;


import com.ua.money24.model.Rate;

import java.util.List;

public record ExecAsPublicResponse(int statusCode, String statusMessage, Result result) {
    public record Result(List<Rate> rates, String message) {
    }
}
