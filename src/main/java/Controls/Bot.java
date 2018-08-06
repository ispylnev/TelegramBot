package Controls;

import Utils.FileUtils;
import Utils.MyDate;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;


public class Bot extends TelegramLongPollingBot  {
    private Properties properties = new Properties();
    protected Bot(DefaultBotOptions options) {
        super(options);
    }
    private String beginTime;
    private String endtime;
    private String botName = FileUtils.getBotName(properties);
    private String token = FileUtils.getToken(properties);

    private String getBeginTime() {
        return beginTime;
    }

    private void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    private String getEndtime() {
        return endtime;
    }

    private void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Bot() {
    }


    public void sendMsg(Message massage, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(massage.getChatId().toString());
        sendMessage.setReplyToMessageId(massage.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message mes = update.getMessage();
        if (mes!=null && mes.hasText()){
            switch (mes.getText()){
                case "НАЧАТЬ":
                    setBeginTime(MyDate.getTimeNow());
                    sendMsg(mes,"Начало работы :" + "\n" + beginTime.substring(0,19));
                    break;

                case "ЗАКОНЧИТЬ":
                    setEndtime(MyDate.getTimeNow());
                    sendMsg(mes,"Время окончания работы: " + "\n" +  endtime.substring(0,19));
                    sendMsg(mes,"Отработано за сегодня :" + "\n" + MyDate.workingHours(beginTime,endtime));
                    break;

                case "/start":
                    sendMsg(mes,"Инициализция успешна"+"\n"+ "Можно работать");

            }

        }

    }


    public String getBotUsername() {

        return botName;
    }

    public String getBotToken() {

        return token;
    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        видимость сообщения
        replyKeyboardMarkup.setSelective(true);
//        подгонка клавиатуры под клиента
        replyKeyboardMarkup.setResizeKeyboard(true);
//       скртие кнопки после ввода
        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        создаем кнопки
        List<KeyboardRow> keyboardRowsList= new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("НАЧАТЬ"));
        keyboardFirstRow.add(new KeyboardButton("ЗАКОНЧИТЬ"));
        keyboardRowsList.add(keyboardFirstRow);
//        Устанавливаем список клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboardRowsList);

    }

}
