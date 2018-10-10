package Utils;


import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.TimerTask;

//Напоминания
public class BotTimer extends TimerTask  {
   private String string;


    @Override
    public void run() {
        System.out.println("Test");
//        this.string = "test";
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText("test");


    }

    public String getString(){
        return string;
    }
}
