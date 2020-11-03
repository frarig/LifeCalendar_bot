package ru.bot.data;

import ru.bot.handler.BotState;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    PersonData getUserProfileData(int userId);

    void saveUserProfileData(int userId, PersonData userProfileData);
}
