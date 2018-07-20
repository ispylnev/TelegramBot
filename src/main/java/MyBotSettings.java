import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

public class MyBotSettings extends TelegramLongPollingBot {

//  public void onUpdateReceived(Update update) {
//        if (update.hasMessage()&&update.getMessage().hasText()){
//            String massageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//
//            SendMessage sendMessage = new SendMessage()
//                    .setChatId(chatId)
//                    .setText(massageText);
//            try{
//                execute(sendMessage);
//            }catch (TelegramApiException e){
//                e.printStackTrace();
//            }
//        }
//
//    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
           e.printStackTrace();
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

    public static void main(String[] args) {
        MyBotSettings myBotSettings=new MyBotSettings();

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(myBotSettings);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
