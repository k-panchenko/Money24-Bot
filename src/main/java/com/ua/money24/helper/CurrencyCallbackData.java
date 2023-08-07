package com.ua.money24.helper;

public class CurrencyCallbackData extends CallbackData {
    public static final String ID = "id";

    static {
        prefix = "currency";
        names = new String[]{ID};
    }

    public CurrencyCallbackData(String... parts) {
        super(parts);
    }
}
