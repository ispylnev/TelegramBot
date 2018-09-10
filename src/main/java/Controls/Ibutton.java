package Controls;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;


public  interface Ibutton {
    //Creat inlineKeybord
    default SendMessage setIlnineKeyboard(long chatId, SendMessage sendMessage) throws TelegramApiException {
        sendMessage.setChatId(chatId);
        sendMessage.setText("Прочтете краткую инструкцию?");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(new InlineKeyboardButton().setText("Да").setCallbackData("Да"));
        inlineKeyboardButtonList.add(keyboardRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtonList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    //TODO подумай над рефлексией как с помощью анотаций сможешь добавить любое колличество кнопок.

    default  void setButtons(SendMessage sendMessage) throws TelegramApiException {
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

