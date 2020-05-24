package interfaces.bot.telegram;

import information.Information;
import logger.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pythonProgramms.FritzBox;
import pythonProgramms.Sonos;
import fritzBox.*;

public class TelegramBot extends TelegramLongPollingBot {

    Sonos sonos = new Sonos();
    FritzBox fritzBox = new FritzBox();


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

        long chatId = update.getMessage().getChatId();
        try{
            Logger.log("Telegram", "Info", "New Message from Channel: " + chatId + " Message:" + update.getMessage().getText());
        }catch (Exception ignored){}

        boolean allowed = false;





        for (long id : Information.TELEGRAM_ALLOWED_CHATS) {
            if(id == chatId){
                allowed = true;
                break;
            }
        }
        if(!allowed){
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {

            String msg = update.getMessage().getText();
            String response = "unknown command";

            if(msg.startsWith("/")){
                msg = msg.substring(1);
            }

            if (msg.toLowerCase().startsWith("sonos")){
                response = sonosMsg(msg);
            }

            if(msg.toLowerCase().startsWith("fritz")){
                response = fritzMsg(msg);
            }

            if(msg.equalsIgnoreCase("help")){
                response = "Für mehr Infos zu den Commands: \n /sonos help\n/fritz help";
            }


            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(chatId)
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

        String sonosCmd;
        try{
            sonosCmd = params[1];
        } catch (IndexOutOfBoundsException e){
            sonosCmd = "help";
        }





        if(sonosCmd.equalsIgnoreCase("help")){
            String result = "Fogende Sonos Commands gibt es:\n";
            result += "/sonos volume\n";
            result += "/sonos volume <new volume>\n";
            result += "/sonos louder\n";
            result += "/sonos quieter";
            return  result;
        }

        if(sonosCmd.equalsIgnoreCase("volume")){
            if(params.length < 3 || params[2] == null){
                return "Sonos volume: " + sonos.getVolume();
            }
            try {
                int volume = Integer.parseInt(params[2]);
                sonos.setVolume(volume);
                return "New Sonos volume: " + sonos.getVolume();
            }catch (NumberFormatException e){
                return "The new volume must be a number";
            }
        }

        if(sonosCmd.equalsIgnoreCase("louder")){
            if(params.length < 3 || params[2] == null){
                sonos.louder(5);
                return "New Sonos Volume: " + sonos.getVolume();
            }
            try {
                int volume = Integer.parseInt(params[2]);
                sonos.louder(volume);
                return "New Sonos Volume: " + sonos.getVolume();
            }catch (NumberFormatException e){
                sonos.louder(5);
                return "New Sonos Volume: " + sonos.getVolume();
            }
        }

        if(sonosCmd.equalsIgnoreCase("quieter")){
            if(params.length < 3 || params[2] == null){
                sonos.quieter(5);
                return "New Sonos Volume: " + sonos.getVolume();
            }
            try {
                int volume = Integer.parseInt(params[2]);
                sonos.quieter(volume);
                return "New Sonos Volume: " + sonos.getVolume();
            }catch (NumberFormatException e){
                sonos.quieter(5);
                return "New Sonos Volume: " + sonos.getVolume();
            }
        }

        if(sonosCmd.equalsIgnoreCase("info")){
            StringBuilder result = new StringBuilder("Current Track: " + sonos.getCurrectTrack() +
                    "\nQueue: \n");
            for(String s: sonos.getQueue()){
                result.append(s).append("\n");
            }

            return result.toString();
        }

        return "Unknown Sonos Cmd";
    }


    public String fritzMsg(String msg){
        String[] params = msg.split(" ");

        String fritzCmd;
        try{
            fritzCmd = params[1];
        } catch (IndexOutOfBoundsException e){
            fritzCmd = "help";
        }

        if(fritzCmd.equalsIgnoreCase("help")){
            return "Bisher noch keine FritzBox-Befehle";
        }

        if(fritzCmd.equalsIgnoreCase("status")){
            StringBuilder result = new StringBuilder();
            for(FritzBoxInformations i : fritzBox.getWLanState()){
                result.append(i.getSSID())
                        .append(": ")
                        .append(i.getState())
                        .append("\n");
            }
            return result.toString();
        }

        if(fritzCmd.equalsIgnoreCase("devices")){
            StringBuilder result = new StringBuilder();
            for(FritzBoxDevice device : fritzBox.getOnlineDevices(fritzBox.getKnownDevices())){
                result.append(device.getName())
                        //.append(" --> ")
                        //.append(device.getIp())
                        .append("\n");

            }
            return result.toString();
        }



        return "unknown fritz command";
    }
}
