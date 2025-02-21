package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final BinanceClient binanceClient;
    private final SubscribersRepository subscribersRepository;
    private final CryptoBot cryptoBot;

    @Value("${telegram.bot.notify.delay.value}")
    private int notifyDelay;

    private double lastPrice = 0.0;
    private final Map<Long, Long> lastNotifyTimestamps = new HashMap<>();

    public NotificationService(BinanceClient binanceClient, SubscribersRepository subscribersRepository, CryptoBot cryptoBot) {
        this.binanceClient = binanceClient;
        this.subscribersRepository = subscribersRepository;
        this.cryptoBot = cryptoBot;
    }

    @Scheduled(fixedRateString = "${binance.api.update.interval}")
    public void checkPriceAndNotify() {
        try {
            double currentPrice = binanceClient.getBitcoinPrice();
            if(currentPrice == lastPrice) {
                return;
            }
            lastPrice = currentPrice;

            List<Subscribers> subscribersList = subscribersRepository.findBySubscribedPriceGreaterThan(currentPrice);
            long now = System.currentTimeMillis();

            for(Subscribers sub : subscribersList) {
                Long lastNotify = lastNotifyTimestamps.getOrDefault(sub.getTelegramId(), 0L);

                if(now - lastNotify >= notifyDelay * 60 * 1000) {
                    SendMessage message = new SendMessage();
                    message.setChatId(sub.getTelegramId().toString());
                    message.setText("Пора покупать, стоимость биткоина " + currentPrice + " USD");
                    cryptoBot.execute(message);

                    lastNotifyTimestamps.put(sub.getTelegramId(), now);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при проверке цен и отправке уведомлений: " + e.getMessage());
        }
    }
}
