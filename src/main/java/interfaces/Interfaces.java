package interfaces;

import information.Information;
import interfaces.bot.telegram.TelegramBot;

public class Interfaces {

    public static TelegramBot telegramBot = null;

    public Interfaces(TelegramBot telegramBot){
        Interfaces.telegramBot = telegramBot;
    }

    public Interfaces(){
        if(telegramBot == null){
            throw new NullPointerException("The telegramBot must be initialized to use this constructor");
        }
    }

    public void send(String message, boolean telegram){
        if(telegram){
            sendTelegram(message, Information.TELEGRAM_ALLOWED_CHATS);
        }
    }

    private void sendTelegram(String message, long chatId){
        telegramBot.send(message, chatId);
    }

    private void sendTelegram(String message, long[] chatIds){
        for(long chatId: chatIds){
            this.sendTelegram(message, chatId);
        }
    }
}
