package org.example;

import org.example.telegrambot.MyBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static final String BOTTOKEN = "6811380563:AAEOflGyKdQkgwfoRuUaosUz7KYVzH7bksA";
    public static final String BOTUSERNAME = "Eko_zakovatbot";

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot(BOTTOKEN, BOTUSERNAME));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}