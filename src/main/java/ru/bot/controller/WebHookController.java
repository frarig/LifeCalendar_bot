package ru.bot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.MyTestBot;

@Controller
@RestController
public class WebHookController {
    private final MyTestBot testBot;

    public WebHookController(MyTestBot testBot) {
        this.testBot = testBot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return testBot.onWebhookUpdateReceived(update);
    }
}
