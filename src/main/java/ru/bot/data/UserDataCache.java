package ru.bot.data;

import org.springframework.stereotype.Component;
import ru.bot.handler.BotState;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private final Map<Integer, BotState> usersBotStates = new HashMap<>();
    private final Map<Integer, PersonData> usersProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.GREETING;
        }

        return botState;
    }

    @Override
    public PersonData getUserProfileData(int userId) {
        PersonData userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData = new PersonData();
        }
        return userProfileData;
    }

    @Override
    public void saveUserProfileData(int userId, PersonData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }
}
