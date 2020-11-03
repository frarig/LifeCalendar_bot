package ru.bot.data;

import java.io.Serializable;

/**
 * Данные вводимые пользователем
 */

public class PersonData implements Serializable {
    private String id;
    private long chatId;
    private String dateOfBirth;
    private int age;
    private String calendar;

    public PersonData() {
    }

    public PersonData(String id, long chatId, String dateOfBirth, int age, String calendar) {
        this.id = id;
        this.chatId = chatId;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.calendar = calendar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    @Override
    public String toString() {
        return "PersonData{" + "id=" + id + ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age + ", calendar='" + calendar + '\'' + '}';
    }
}
