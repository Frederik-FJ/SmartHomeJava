package interfaces.bot.telegram;

import information.Information;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pythonProgramms.Sonos;

public class TelegramBot extends TelegramLongPollingBot {

    Sonos sonos = new Sonos();


    @Override
    public String getBotUsername() {
        return Information.TELEGRAM_BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Information.TELEGRAM_BOT_TOKEN;
    }

    public void send(String msg, long chatId){
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(msg);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String msg = update.getMessage().getText();
            String response = "unknown command";

            if (msg.toLowerCase().startsWith("sonos")){
                response = sonosMsg(msg);
            }





                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                        .setChatId(update.getMessage().getChatId())
                        .setText(response);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public String sonosMsg(String msg){
        String[] params = msg.split(" ");

        if(params.length == 1){
            return "Please specify a sonos cmd";
        }

        String sonosCmd = params[1];

        if(sonosCmd.equalsIgnoreCase("volume")){
            if(params.length < 3 || params[2] == null){
                return "Sonos volume: " + sonos.getVolume();
            }
            try {
                int volume = Integer.parseInt(params[2]);
                sonos.setVolume(volume);
                return "Sonos volume: " + sonos.getVolume();
            }catch (NumberFormatException e){
                return "The new volume must be a number";
            }
        }

        return "Unknown Sonos Cmd";
    }

}
