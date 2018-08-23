import Utils.FileUtils;
import Utils.MyDate;
import database.MongoDbWork;
import org.bson.Document;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;


public class Bot extends TelegramLongPollingBot  {
    private Properties properties = new Properties();
    private boolean check = true;
    protected Bot(DefaultBotOptions options) {
        super(options);
    }
    private MongoDbWork mongoDbWork = new MongoDbWork();
    private String userName;
    private String firstName;
    private long userId;
    private String beginTime;
    private String endTime;
    private Long duration;
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
            setButtons(sendMessage);
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message mes = update.getMessage();
        userName = mes.getChat().getUserName();
        firstName = mes.getChat().getFirstName();
        userId = mes.getChat().getId();
        if (mes!=null && mes.hasText()){
            switch (mes.getText()){
                case "НАЧАТЬ":
                    if (check) {
                        MyDate.setBeginTime(MyDate.getTimeNow());
                        beginTime = MyDate.getBeginTime();
                        sendMsg(mes, "Начало работы :" + "\n" + beginTime.substring(0, 19));
                        check = false;
                    }else sendMsg(mes,"необходимо закончить начатое");
                        break;


                case "ЗАКОНЧИТЬ":

                    if(!check) {
                        MyDate.setEndtime(MyDate.getTimeNow());
                        endTime = MyDate.getEndtime();
                        sendMsg(mes, "Время окончания работы: " + "\n" + endTime.substring(0, 19));
//                    sendMsg(mes,"Отработано за сегодня :" + "\n" + MyDate.SetwWorkingHours());
                        duration = MyDate.getDuration();
                        //обнавляем даты в массиве
                        mongoDbWork.updateDate(toIntExact(userId),beginTime.substring(0,10),MyDate.SetwWorkingHours());
                        check = true;


                    }else sendMsg(mes,"сначала начните");
                        break;


                case "/start":
//
                    mongoDbWork.addUser(userName,firstName, toIntExact(userId));
                    sendMsg(mes, firstName + ", " + "Инициализция успешна"+"\n"+ "Можно работать");
                    break;

                default:
                    String date = mes.getText();
                    Document queryDoc = mongoDbWork.queryDoc(toIntExact(userId));
                    Long sumSeconds = mongoDbWork.queryWorkingHourse(queryDoc,date);
                   String parseSeconds =  String.format("%dчасов %dминут %dсекунд%n",
                   TimeUnit.MILLISECONDS.toDays(sumSeconds),
                   TimeUnit.MILLISECONDS.toHours(sumSeconds),
                   TimeUnit.MILLISECONDS.toMinutes(sumSeconds),
                   TimeUnit.MILLISECONDS.toSeconds(sumSeconds));
                   sendMsg(mes, parseSeconds);


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
