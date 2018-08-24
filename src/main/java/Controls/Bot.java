package Controls;
import static java.lang.Math.toIntExact;
import Utils.FileUtils;
import Utils.MyDate;
import database.MongoDbWork;
import org.bson.Document;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class Bot extends TelegramLongPollingBot  {
    private Properties properties = new Properties();
    private boolean check = true;
    protected Bot(DefaultBotOptions options) {
        super(options);
    }
    private MongoDbWork mongoDbWork = new MongoDbWork();
    private String userName;
    private String firstName;
    private long chatId;
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
//        Message mes = update.getMessage();
//        if (mes!=null && mes.hasText()){
          if (update.hasMessage() && update.getMessage().hasText()){
              Message mes = update.getMessage();
              userName = mes.getChat().getUserName();
              firstName = mes.getChat().getFirstName();
              chatId = mes.getChatId();
              userId = mes.getChat().getId();
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

                    mongoDbWork.addUser(userName,firstName, toIntExact(userId));
                    sendMsg(mes, firstName + ", " + "Инициализция успешна"+"\n"+ "Можно работать");
                    SendMessage sendMessage = new SendMessage();
                    setIlnineKeyboard(sendMessage);
                    break;

                default:
                    String date = mes.getText();
                    Document queryDoc = mongoDbWork.queryDoc(toIntExact(userId));
                    Long sumSeconds = mongoDbWork.queryWorkingHourse(queryDoc,date);
                   String parseSeconds =  String.format("%dчасов %dминут %dсекунд%n",
                   TimeUnit.MILLISECONDS.toHours(sumSeconds),
                   TimeUnit.MILLISECONDS.toMinutes(sumSeconds),
                   TimeUnit.MILLISECONDS.toSeconds(sumSeconds));
                   sendMsg(mes, parseSeconds);




            }

        }else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();

            long mesId = update.getCallbackQuery().getMessage().getMessageId();
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                if (callData.equals("test")){
                    EditMessageText messageText = new EditMessageText()
                            .setChatId(chatId)
                            .setMessageId(toIntExact(mesId))
                            .setText("Для начала отсчета времени нажмите кнопку Начать"+"\n"+
                            "Для того чтобы закончить нажмите кнопку Закночить"+"\n"+
                                    "Для вывода суммарно отработаного времени введите в чат дату в формате yyyy-MM-dd");
                try{
                    editMessageText(messageText);
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }

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

    public SendMessage setIlnineKeyboard(SendMessage sendMessage){
        sendMessage.setChatId(chatId);
        sendMessage.setText("Прочтете краткую инструкцию?");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(new InlineKeyboardButton().setText("Да").setCallbackData("test"));
        inlineKeyboardButtonList.add(keyboardRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtonList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try{
          execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
        return sendMessage;

    }

}
