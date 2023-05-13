package com.beaconfire.Utils;

import java.util.HashMap;

import java.util.HashMap;

public class DayOfWeekUtils {
    private static final HashMap<String, String> dayOfWeekMap = new HashMap<>();

    static {
        dayOfWeekMap.put("1", "Monday");
        dayOfWeekMap.put("2", "Tuesday");
        dayOfWeekMap.put("3", "Wednesday");
        dayOfWeekMap.put("4", "Thursday");
        dayOfWeekMap.put("5", "Friday");
        dayOfWeekMap.put("6", "Saturday");
        dayOfWeekMap.put("7", "Sunday");
    }

    public static String getDayOfWeek(String day) {
        return dayOfWeekMap.get(day);
    }
}


