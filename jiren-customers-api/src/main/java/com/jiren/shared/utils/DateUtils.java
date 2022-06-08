package com.jiren.shared.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static final String AMERICA_LIMA = "America/Lima";
    public static final ZoneId LIMA_TIME_ZONE = ZoneId.of(AMERICA_LIMA);

    public static Timestamp convert(LocalDate date, LocalTime time) {
        return date != null ? Timestamp.valueOf(date.atTime(time)
                .atZone(LIMA_TIME_ZONE)
                .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()) : null;
    }

    public static LocalDateTime convert(Date date) {
        return date != null ? date.toInstant().atZone(getToday().getZone()).toLocalDateTime() : null;
    }

    public static Timestamp convert(LocalDateTime date) {
        return date != null ? Timestamp.valueOf(date
                .atZone(LIMA_TIME_ZONE)
                .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()) : null;
    }

    public static LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant()
                .atZone(getToday().getZone()).toLocalDateTime() : null;
    }

    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(LIMA_TIME_ZONE).toInstant());
    }

    public static LocalDateTime convertWithUTF(LocalDateTime localDateTime) {
        return localDateTime.atZone(LIMA_TIME_ZONE).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime nextUtilDate(LocalDateTime date) {
        LocalDateTime newDate = date.plusDays(1);
        if(newDate.getDayOfWeek() == DayOfWeek.SATURDAY)
            newDate = newDate.plusDays(2);
        if(newDate.getDayOfWeek() == DayOfWeek.SUNDAY)
            newDate = newDate.plusDays(1);

        return newDate;
    }

    public static Date nextUtilDate(Date date) {
        return convertToDate(nextUtilDate(convert(date)));
    }

    public static String toString(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone(LIMA_TIME_ZONE));
        return formatter.format(date);
    }

    public static String toString(ZonedDateTime date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }

    public static ZonedDateTime getToday() {
        return ZonedDateTime.now(LIMA_TIME_ZONE);
    }
}
