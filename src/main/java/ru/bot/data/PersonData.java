package ru.bot.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.PropertyKey;
import java.io.Serializable;

/**
 * Данные пользователя
 */

@Document(collection = "users")
public class PersonData implements Serializable {
    @Id
    @PropertyKey
    private long chatId;
    private String dateOfBirth;
    private String age;

    public PersonData() {
    }

    public PersonData(long chatId, String dateOfBirth, String age) {
        this.chatId = chatId;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonData{" + "dateOfBirth='" + dateOfBirth + '\'' + ", age=" + age + '\'' + '}';
    }
}
