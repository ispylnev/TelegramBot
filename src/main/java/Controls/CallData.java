package Controls;

import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;

import static java.lang.Math.toIntExact;

public interface CallData {
    default EditMessageText setEditMessange(String mesText, long chatId, long mesId){
        return new EditMessageText().setChatId(chatId)
                .setMessageId(toIntExact(mesId))
                .setText(mesText);

    }
}
