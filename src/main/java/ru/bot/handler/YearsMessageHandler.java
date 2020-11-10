package ru.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.calendar.Calendar;
import ru.bot.calendar.DateOfBirth;
import ru.bot.data.PersonData;
import ru.bot.data.UserDataCache;
import ru.bot.service.ReplyMessageService;
import ru.bot.service.UserDataService;

/**
 * Спрашивает пользователя на сколько лет рассчитать календарь
 */

@Component
public class YearsMessageHandler implements InputMessageHandler {
    private final ReplyMessageService messageService;
    private final UserDataCache userDataCache;
    private final Calendar calendar;
    private final UserDataService userDataService;

    public YearsMessageHandler(ReplyMessageService messageService, UserDataCache userDataCache,
                               Calendar calendar, UserDataService userDataService) {

        this.userDataService = userDataService;
        this.messageService = messageService;
        this.userDataCache = userDataCache;
        this.calendar = calendar;
    }

    @Override
    public SendMessage handler(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String usersAnswer = message.getText();

        if (isNum(usersAnswer)) {
            int years = Integer.parseInt(usersAnswer);

            if (years < 1) {
                userDataCache.setUsersCurrentBotState(userId,BotState.ASK_HOW_YEARS);
                return messageService.getReplyMessage(chatId, "errorOfMinimumLimit");
            } else if (years > 100) {
                userDataCache.setUsersCurrentBotState(userId,BotState.ASK_HOW_YEARS);
                return messageService.getReplyMessage(chatId, "errorOfMaximumLimit");
            } else {
                calendar.setYear(years);

                saveUser(userId, chatId, usersAnswer);

                userDataCache.setUsersCurrentBotState(userId,BotState.PRINT_CALENDAR);
                return messageService.getReplyMessage(chatId, "nice");
            }
        } else {
            userDataCache.setUsersCurrentBotState(userId,BotState.ASK_HOW_YEARS);
            return messageService.getReplyMessage(chatId, "reply.howManyYearsToCount");
        }
    }

    @Override
    public BotState getState() {
        return BotState.ASK_HOW_YEARS;
    }

    private boolean isNum(String message) {
        try {
            Integer.parseInt(message);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void saveUser(int userId, long chatId, String usersAnswer) {
        DateOfBirth dateOfBirth = DateOfBirth.getInstance();
        PersonData personData = userDataCache.getUserProfileData(userId);

        personData.setChatId(chatId);
        personData.setDateOfBirth(dateOfBirth.getBirthDay());
        personData.setAge(usersAnswer);

        userDataService.savePersonProfileData(personData);
    }
}
