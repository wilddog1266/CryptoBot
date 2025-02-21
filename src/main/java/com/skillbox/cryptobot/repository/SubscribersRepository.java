package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.model.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscribersRepository extends JpaRepository<Subscribers, UUID> {
    Optional<Subscribers> findByTelegramId(Long telegramId);
    List<Subscribers> findBySubscribedPriceGreaterThan(double price);
}
