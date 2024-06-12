package com.kokai.Services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateTimeFunctions {

    public static String getFormattedLocalDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }

    public static String formatDate(String date) {
        String[] possibleDates = {"dd/MM/yyyy", "ddMMyyyy", "dd-MM-yyyy"};
        for (String dates : possibleDates) {
            try {
                LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dates));
                String parsedDate = convertedDate.format(DateTimeFormatter.ofPattern(possibleDates[0]));
                System.out.println("Parsed date: " + parsedDate);
                return parsedDate;
            } catch (Exception e) {
                System.out.println("Failed to format date in format: " + dates);
            }
        }
        return null;
    }

    public static LocalDate stringToDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return parsedDate;
        } catch (Exception e) {
            System.out.println("Failed to parse string into date:");
        }
        return null;
    }

    public static int getAgeFromDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        Period age = Period.between(date, today);
        return age.getYears();
    }

    public static int getLocalHour() {
        return LocalTime.now().getHour();
    }

    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    public static String greetByTimeOfDay(int time) {
        if (time >= 0 && time < 12) {
            return "morning";
        } else if (time >= 12 && time < 18) {
            return "afternoon";
        } else if (time >= 18 && time <= 23) {
            return "evening";
        }
        return "";
    }

}
