package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T t, T start, T end) {
        return (start == null || t.compareTo(start) >= 0) && (end == null || t.compareTo(end) < 0);
    }

    public static LocalDateTime getStartLocalDateTime(LocalDate date) {
        return date == null ? LocalDateTime.MIN : date.atStartOfDay();
    }

    public static LocalDateTime getEndLocalDateTime(LocalDate date) {
        return date == null ? LocalDateTime.MAX : date.plusDays(1).atStartOfDay();
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

