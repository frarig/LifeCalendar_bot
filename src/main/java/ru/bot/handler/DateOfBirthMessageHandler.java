package ru.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.calendar.DateOfBirth;
import ru.bot.data.UserDataCache;
import ru.bot.service.ReplyMessageService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Спрашивает пользователя о дате рождения
 */

@Component
public class DateOfBirthMessageHandler implements InputMessageHandler {

    private final ReplyMessageService messageService;
    private final UserDataCache userDataCache;
    private final DateOfBirth dateOfBirth = DateOfBirth.getInstance();

    public DateOfBirthMessageHandler(ReplyMessageService messageService, UserDataCache userDataCache) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handler(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String usersAnswer = message.getText();

        if (!isDateValid(usersAnswer)) {
            return messageService.getReplyMessage(chatId, "reply.enterDateOfBirth");

        } else {
            dateOfBirth.setBirthDay(usersAnswer);

            userDataCache.setUsersCurrentBotState(userId,BotState.ASK_HOW_YEARS);
            return messageService.getReplyMessage(chatId, "nice.enterDateOfBirth");
        }
    }

    @Override
    public BotState getState() {
        return BotState.ASK_DATE_OF_BIRTH;
    }

    private boolean isDateValid(String message) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(message, formatter);
            formatter.parse(message);
            return date.isBefore(LocalDate.now());

        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
