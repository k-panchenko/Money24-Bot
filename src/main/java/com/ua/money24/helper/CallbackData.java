package com.ua.money24.helper;

import jakarta.validation.ValidationException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CallbackData {
    protected static String separator = ":";
    protected static String prefix;
    protected static String[] names;

    private final String[] parts;

    public CallbackData(String... parts) {
        this.parts = parts;
    }

    public static Map<String, String> parse(String callbackData) {
        var parts = new LinkedList<>(Arrays.asList(callbackData.split(separator)));
        var dataPrefix = parts.poll();
        if (!prefix.equals(dataPrefix)) {
            throw new ValidationException(String.format("Bad prefix %s != %s", dataPrefix, prefix));
        }
        if (names.length != parts.size()) {
            throw new ValidationException(String.format(
                    "Callback data takes %d arguments but %d were given",
                    names.length,
                    parts.size()
            ));
        }
        return IntStream.range(0, names.length)
                .boxed()
                .collect(Collectors.toMap(i -> names[i], parts::get));
    }

    public static boolean filter(String callbackData) {
        try {
            parse(callbackData);
            return true;
        } catch (ValidationException exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.join(separator, parts);
    }
}
