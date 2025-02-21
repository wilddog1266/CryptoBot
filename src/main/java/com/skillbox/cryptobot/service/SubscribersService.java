package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscribersService {

    private final SubscribersRepository subscribersRepository;

    public SubscribersService(SubscribersRepository subscribersRepository) {
        this.subscribersRepository = subscribersRepository;
    }

    public void registerUser(Long telegramId) {
        Optional<Subscribers> existingUser = subscribersRepository.findByTelegramId(telegramId);

        if(existingUser.isEmpty()) {
            Subscribers newUser = new Subscribers(telegramId);
            subscribersRepository.save(newUser);
            System.out.println("Зарегистрирован новый пользователь: " + telegramId);
        } else {
            System.out.println("Пользователь уже зарегистрирован: " + telegramId);
        }
    }

    public void subscribe(Long telegramId, Double price) {
        Optional<Subscribers> existingUser = subscribersRepository.findByTelegramId(telegramId);

        if(existingUser.isPresent()) {
            Subscribers subscriber = existingUser.get();
            subscriber.setSubscribedPrice(price);
            subscribersRepository.save(subscriber);
        } else {
            Subscribers newSubscriber = new Subscribers(telegramId, price);
            subscribersRepository.save(newSubscriber);
        }
    }
}
