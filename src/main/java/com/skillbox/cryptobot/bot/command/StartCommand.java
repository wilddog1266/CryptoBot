package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.SubscribersService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class StartCommand implements IBotCommand {

    private final SubscribersService subscribersService;

    public StartCommand(SubscribersService subscribersService) {
        this.subscribersService = subscribersService;
    }

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long telegramId = message.getFrom().getId();
        subscribersService.registerUser(telegramId);

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        answer.setText("""
                Привет! Данный бот помогает отслеживать стоимость биткоина.
                Поддерживаемые команды:
                 /get_price - получить стоимость биткоина
                 /get_subscription - узнать о своей подписке на стоимость биткоина
                 /subscribe (price) - подписаться на стоимость биткоина.
                 /unsubscribe - перестать отслеживать стоимость биткоина.
                """);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            System.out.println("Error occurred in /start command " + e);
        }
    }
}