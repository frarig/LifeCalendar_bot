package ru.bot.service;

import org.springframework.stereotype.Service;
import ru.bot.data.PersonData;
import ru.bot.data.UserDataRepository;

import java.util.List;

/**
 * Получает список всех пользователей,
 * сохраняет, удаляет и получет данные профиля
 */

@Service
public class UserDataService {
    private final UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<PersonData> getAllPersonsProfiles() {
        return userDataRepository.findAll();
    }

    public void savePersonProfileData(PersonData personData) {
        userDataRepository.save(personData);
    }

    public void deletePersonProfileData(String profileDataId) {
        userDataRepository.deleteById(profileDataId);
    }

    public PersonData getPersonProfileData(long chatId) {
        return userDataRepository.findByChatId(chatId);
    }
}
