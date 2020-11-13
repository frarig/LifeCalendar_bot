package ru.bot.calendar;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Calendar {
    private static final int WEEKS_IN_YEAR = 52;
    private int year;
    private String[][] life;
    private final StringBuilder calendar = new StringBuilder();
    private final List<String> listCalendars = new ArrayList<>();

    private void calendarAppend() {
        for (String[] a : life) {
            for (String i : a) {
                calendar.append(i);
            }
        }
    }

    private void fillingTheCalendar() {
        DateOfBirth dateOfBirth = DateOfBirth.getInstance();
        dateOfBirth.findingThePeriodOfLife();

        for (int i = 0; i < life.length; i++) {
            if (i < dateOfBirth.getYear()) {
                Arrays.fill(life[i], "\u274C");

            } else if (i == dateOfBirth.getYear()) {
                int weeks = dateOfBirth.getWeek();
                for (int j = 0; j < WEEKS_IN_YEAR; j++) {

                    if (j < weeks) {
                        life[i][j] = "\u274C";

                    } else {
                        life[i][j] = "\u2705";
                    }
                }
            } else {
                Arrays.fill(life[i], "\u2705");
            }
        }
    }

    public void getCompletedCalendar() {
        life = new String[year][WEEKS_IN_YEAR];

        fillingTheCalendar();
        calendarAppend();

        if (year > 25 && year < 51) {
            listCalendars.add(calendar.substring(   0,1300)); // 25
            listCalendars.add(calendar.substring(1300)); // 50

        } else if (year > 50 && year < 76) {
            listCalendars.add(calendar.substring(   0,1300)); // 25
            listCalendars.add(calendar.substring(1300,2600)); // 50
            listCalendars.add(calendar.substring(2600)); // 75

        } else if (year > 75 && year < 101) {
            listCalendars.add(calendar.substring(   0,1300)); // 25
            listCalendars.add(calendar.substring(1300,2600)); // 50
            listCalendars.add(calendar.substring(2600,3900)); // 75
            listCalendars.add(calendar.substring(3900)); // 100
        } else {
            listCalendars.add(calendar.toString());           // 25
        }
    }

    public void cleanCalendar() {
        calendar.setLength(0);
        listCalendars.clear();
    }

    public List<String> getListCalendar() {
        return listCalendars;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}