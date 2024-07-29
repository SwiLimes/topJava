package ru.javawebinar.topjava.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isTimeAfterOrEquals(LocalTime lt, LocalTime startTime) {
        return !lt.isBefore(startTime);
    }

    public static boolean isTimeBefore(LocalTime lt, LocalTime endTime) {
        return lt.isBefore(endTime);
    }

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return startTime != null
                ? (endTime != null ? isTimeAfterOrEquals(lt, startTime) && isTimeBefore(lt, endTime) : isTimeAfterOrEquals(lt, startTime))
                : (endTime == null || isTimeBefore(lt, endTime));
    }
}
