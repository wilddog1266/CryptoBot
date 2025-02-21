package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.SubscribersService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
public class SubscribeCommand implements IBotCommand {

    private final SubscribersService subscribersService;

    public SubscribeCommand(SubscribersService subscribersService) {
        this.subscribersService = subscribersService;
    }

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long telegramId = message.getFrom().getId();
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        if (arguments.length == 0) {
            answer.setText("Введите цену (USD) после команды. Например: /subscribe 50000");
        } else {
            try {
                Double price = Double.parseDouble(arguments[0]);
                subscribersService.subscribe(telegramId, price);
                answer.setText("Вы подписаны на стоимость биткоина " + price + " USD");
            } catch (NumberFormatException e) {
                answer.setText("Некорректное число. Введите цену после команды. Например: /subscribe 50000");
            }
        }

        try {
            absSender.execute(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}