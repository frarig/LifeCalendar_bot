package ru.bot.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<PersonData, String> {
    PersonData findByChatId(long chatId);
}
