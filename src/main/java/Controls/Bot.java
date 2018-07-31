package Controls;

import Utils.FileUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Properties;


public class Bot extends TelegramLongPollingBot {
    Properties properties = new Properties();
    protected Bot(DefaultBotOptions options) {
        super(options);
    }
    private String botName = FileUtils.getBotName(properties);
    private String token = FileUtils.getToken(properties);

    public Bot() {

    }


    public void sendMsg(Message massage, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(massage.getChatId().toString());
        sendMessage.setReplyToMessageId(massage.getMessageId());
        sendMessage.setText(text);
        try{
            sendMessage(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message mes = update.getMessage();
        if (mes!=null && mes.hasText()){
            switch (mes.getText()){
                case "/help":
                    sendMsg(mes,"Привет! Я новый бот, скоро я буду гораздно функциональнее");
                    break;
            }

        }

    }


    public String getBotUsername() {


        return botName;
    }

    public String getBotToken() {

        return token;

    }

}
