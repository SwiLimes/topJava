package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isTimeAfter(LocalTime lt, LocalTime startTime) {
        return !lt.isBefore(startTime);
    }
    public static boolean isTimeBefore(LocalTime lt, LocalTime endTime) {
        return lt.isBefore(endTime);
    }
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return isTimeAfter(lt, startTime) && isTimeBefore(lt, endTime);
    }
}
