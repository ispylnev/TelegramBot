import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

public class MyBotSettings extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()&&update.getMessage().hasText()){
            String massageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage sendMessage = new SendMessage()
                    .setChatId(chatId)
                    .setText(massageText);
            try{
                execute(sendMessage);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

    }

    public void onUpdatesReceived(List<Update> updates) {


    }

    public String getBotUsername() {
        return "Pylnevbot";
    }

    public String getBotToken() {

        return "648680294:AAFKL6j52_tC4E_a-me8Z0-YPOQD_dgiFH4";
    }
}
