package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
public class GetSubscriptionCommand implements IBotCommand {

    private final SubscribersRepository subscribersRepository;

    public GetSubscriptionCommand(SubscribersRepository subscribersRepository) {
        this.subscribersRepository = subscribersRepository;
    }

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long telegramId = message.getFrom().getId();
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        Double price = null;

        if(subscribersRepository.findByTelegramId(telegramId).isPresent()) {
            Subscribers currentSubscriber = subscribersRepository.findByTelegramId(telegramId).get();
            price = currentSubscriber.getSubscribedPrice();
        }
        if(price != null) {
            answer.setText("Вы подписаны на стоимость биткоина " + price + " USD");
        } else {
            answer.setText("Активные подписки отсутствуют");
        }
    }
}