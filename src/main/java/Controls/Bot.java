package Controls;
import static Controls.Constants.*;
import static java.lang.Math.toIntExact;

import Utils.BotTimer;
import Utils.FileUtils;
import Utils.MyDate;
import database.MongoDbWork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Bot extends TelegramLongPollingBot implements Ibutton {

    private Logger logger = LogManager.getLogger(Bot.class);
    private Properties properties = new Properties();
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
    private FileUtils fileUtils = new FileUtils();
//    private String botName = fileUtils.decrypt(FileUtils.getBotName(properties));
    private String botName = FileUtils.getBotName(properties);
//    private String token = fileUtils.decrypt(FileUtils.getToken(properties));
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
            logger.info("Кнопки Установлены");
        }catch (TelegramApiException e){
            logger.error("Ошибка установки кнопок" + e.getMessage());
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message mes = update.getMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            userName = mes.getChat().getUserName();
            firstName = mes.getChat().getFirstName();
            chatId = mes.getChatId();
            userId = mes.getChat().getId();
            switch (mes.getText()) {
                case START:
                    boolean check = Boolean.valueOf((String) mongoDbWork.findFieldInDoc("userId", userId).get("check"));
                    if (check) {
                        MyDate.setBeginTime(MyDate.getTimeNow());
                        beginTime = MyDate.getBeginTime();
                        BotTimer botTimer = new BotTimer();
                        Timer timer = new Timer(true);
                        timer.schedule(botTimer,new Date(System.currentTimeMillis()+5000));


                        sendMsg(mes, "Начало работы :" + "\n" + beginTime.substring(0, 19));
                        mongoDbWork.updateDocument(toIntExact(userId), "false");


                    } else sendMsg(mes, "необходимо закончить начатое");
                    break;

                case STOP:
                    check = Boolean.valueOf((String) mongoDbWork.findFieldInDoc("userId", userId).get("check"));
                    if (!check) {
                        MyDate.setEndtime(MyDate.getTimeNow());
                        endTime = MyDate.getEndtime();
                        sendMsg(mes, "Время окончания работы: " + "\n" + endTime.substring(0, 19));
//                    sendMsg(mes,"Отработано за сегодня :" + "\n" + MyDate.SetwWorkingHours());
                        duration = MyDate.getDuration();
                        //обнавляем даты в массиве
                        mongoDbWork.updateDate(toIntExact(userId), beginTime.substring(0, 10), MyDate.SetwWorkingHours());
                        mongoDbWork.updateDocument(toIntExact(userId), "true");
                    } else sendMsg(mes, "сначала начните");
                    break;

                case INITIALIZATION:

                    mongoDbWork.addUser(userName, firstName, toIntExact(userId));
                    sendMsg(mes, firstName + ", " + "Инициализция успешна" + "\n" + "Можно работать");

                    //Установим Инлайн клавиатуру
                    SendMessage sendMessage = new SendMessage();
                    try {
                        setIlnineKeyboard(chatId,"Прочтете краткую инструкцию?","Да",sendMessage);
                        execute(sendMessage);
                        logger.info("Инлайн клавиатура установлена");
                    } catch (TelegramApiException e) {
                        logger.error("Ошибка установки клавиатуры"+e.getMessage());
                    }

                    break;

                case PORPFOLIO:

                    SendDocument sendDocument = new SendDocument().setChatId(chatId).setDocument(FILEID)
                            .setCaption("Резюме");
                    try{
                        sendDocument(sendDocument);
                        logger.info("Документ отправлен пользователю");

                    }catch (TelegramApiException e){
                       logger.error("Ошибка отправки документа"+ e.getMessage());
                    }


                default:
                    // по дефолту отрабатывает паттерн показа времени
                    String date = mes.getText();
                    if (date.matches("\\d+[-]?+\\d+[-]?+\\d+[-]?")) {
                        Document queryDoc = mongoDbWork.queryDoc(toIntExact(userId));
                        long sumSeconds = mongoDbWork.queryWorkingHourse(queryDoc, date);
                        long convertToSec = TimeUnit.MILLISECONDS.toSeconds(sumSeconds);
                        LocalTime sumwork = LocalTime.ofSecondOfDay(convertToSec);
//                   String parseSeconds =  String.format("%dчасов %dминут %dсекунд%n",
//                   TimeUnit.MILLISECONDS.toHours(sumSeconds),
//                   TimeUnit.MILLISECONDS.toMinutes(sumSeconds),
//                   TimeUnit.MILLISECONDS.toSeconds(sumSeconds));
                        sendMsg(mes, String.valueOf(sumwork));
                    }

            }
//Если пользователь нажал инлайн кнопку возвращается Callback
        } else if (update.hasCallbackQuery()) {
            //todo Вынести в отдельный метод
            String callData = update.getCallbackQuery().getData();
            long mesId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callData.equals("Да")) {
                EditMessageText messageText = new EditMessageText()
                        .setChatId(chatId)
                        .setMessageId(toIntExact(mesId))
                        .setText("Для начала отсчета времени нажмите кнопку Начать" + "\n" +
                                "Для того чтобы закончить нажмите кнопку Закночить" + "\n" +
                                "Для вывода суммарно отработаного времени введите в чат дату в формате yyyy-MM-dd");
                try {
                    execute(messageText);
                } catch (TelegramApiException e) {

                    e.printStackTrace();
                }

            }
            //отправим документ на сервер
        } else if (update.hasMessage() && update.getMessage().hasDocument() && update.getMessage()
                .getChat().getUserName().equals("ispylnev")) {
            org.telegram.telegrambots.api.objects.Document teleDoc = update.getMessage().getDocument();
            String docId = teleDoc.getFileId();
            sendMsg(mes, "feli id " + docId);
        }
    }



    public String getBotUsername() {

        return fileUtils.decrypt(botName);
    }

    public String getBotToken() {
        return fileUtils.decrypt(token);
    }





}
