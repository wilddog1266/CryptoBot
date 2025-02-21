package com.skillbox.cryptobot.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "subscribes")
public class Subscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private Long telegramId;

    @Column(name = "subscribed_price")
    private Double subscribedPrice;

    public Subscribers() {
    }

    public Subscribers(UUID id, Long telegramId, Double subscribedPrice) {
        this.id = id;
        this.telegramId = telegramId;
        this.subscribedPrice = subscribedPrice;
    }

    public Subscribers(Long telegramId) {
        this.telegramId = telegramId;
        this.subscribedPrice = null;
    }

    public Subscribers(Long telegramId, Double subscribedPrice) {
        this.telegramId = telegramId;
        this.subscribedPrice = subscribedPrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Double getSubscribedPrice() {
        return subscribedPrice;
    }

    public void setSubscribedPrice(Double subscribedPrice) {
        this.subscribedPrice = subscribedPrice;
    }

    @Override
    public String toString() {
        return "Subscribers{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", subscribedPrice=" + subscribedPrice +
                '}';
    }
}
