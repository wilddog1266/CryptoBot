package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

@Service
public class UnsubscribeCommand implements IBotCommand {

    private final SubscribersRepository subscribersRepository;

    public UnsubscribeCommand(SubscribersRepository subscribersRepository) {
        this.subscribersRepository = subscribersRepository;
    }

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long telegramId = message.getFrom().getId();
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        Optional<Subscribers> subscriberOpt = subscribersRepository.findByTelegramId(telegramId);

        if (subscriberOpt.isEmpty() || subscriberOpt.get().getSubscribedPrice() == null) {
            answer.setText("Активные подписки отсутствуют");
        } else {
            Subscribers currentSubscriber = subscriberOpt.get();
            answer.setText("Подписка отменена");
            currentSubscriber.setSubscribedPrice(null);
            subscribersRepository.save(currentSubscriber);
        }
    }
}