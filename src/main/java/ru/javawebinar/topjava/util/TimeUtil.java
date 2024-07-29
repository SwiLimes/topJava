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
        if (startTime == null && endTime == null) {
            return true;
        } else if (startTime != null && endTime != null) {
            return isTimeAfterOrEquals(lt, startTime) && isTimeBefore(lt, endTime);
        } else if (startTime != null) {
            return isTimeAfterOrEquals(lt, startTime);
        } else {
            return isTimeBefore(lt, endTime);
        }
    }
}
