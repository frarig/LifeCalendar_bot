package ru.bot.calendar;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Calendar {
    private static final int WEEKS = 52;
    private int year;
    private String[][] life;
    private final StringBuilder calendar = new StringBuilder();
    private final List<String> listOfCalendars = new ArrayList<>();

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

                for (int j = 0; j < WEEKS; j++) {

                    if (j < dateOfBirth.getWeek()) {
                        life[i][j] = "\u274C";
                    } else if (j >= dateOfBirth.getWeek()) {
                        life[i][j] = "\u2705";
                    }
                }
            } else if (i > dateOfBirth.getYear()) {
                Arrays.fill(life[i], "\u2705");
            }
        }
    }

    public void getCalendar() {
        life = new String[year][WEEKS];

        fillingTheCalendar();
        calendarAppend();

        if (year > 25 && year < 51) {
            listOfCalendars.add(calendar.substring(0,1300));    // 25
            listOfCalendars.add(calendar.substring(1300)); // 50

        } else if (year > 50 && year < 76) {
            listOfCalendars.add(calendar.substring(0,1300));    // 25
            listOfCalendars.add(calendar.substring(1300,2600)); // 50
            listOfCalendars.add(calendar.substring(2600)); // 75

        } else if (year > 75 && year < 101) {
            listOfCalendars.add(calendar.substring(0,1300));    // 25
            listOfCalendars.add(calendar.substring(1300,2600)); // 50
            listOfCalendars.add(calendar.substring(2600,3900)); // 75
            listOfCalendars.add(calendar.substring(3900)); // 100
        } else {
            listOfCalendars.add(calendar.toString());    // 25
        }
    }

    public void cleanCalendar() {
        calendar.setLength(0);
        listOfCalendars.clear();
    }

    public List<String> getListOfCalendar() {
        return listOfCalendars;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}