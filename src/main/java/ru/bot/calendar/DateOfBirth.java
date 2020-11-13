package ru.bot.calendar;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateOfBirth {

    private int day;
    private int month;
    private int year;
    public String birthDay;
    private static DateOfBirth instance;

    private DateOfBirth() {
    }

    public static DateOfBirth getInstance() {
        if (instance == null) {
            instance = new DateOfBirth();
        }
        return instance;
    }

    public void findingThePeriodOfLife() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(birthDay, formatter);
        LocalDate endDate = LocalDate.now();
        Period period = Period.between(startDate, endDate);
        day = period.getDays();
        month = period.getMonths();
        year = period.getYears();
    }

    public int getWeek() {
        return (int) Math.ceil((day / 7d) + (month * 4.285));
    }

    public int getYear() {
        return year;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
