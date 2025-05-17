package com.Podzilla.analytics.util;

import java.util.UUID;

public class StringToUUIDParser {
    public static UUID parseStringToUUID(final String str) {
        try {
            return UUID.fromString(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: " + str, e);
        }
    }

}
