package Utils;

import Controls.Bot;

import java.util.TimerTask;


//Напоминания
public class BotTimer extends TimerTask   {
    public String string;



    public class Ob extends java.util.Observable {
        public void  obs(){
           int i = 3;
           setChanged();
           notifyObservers(Integer.valueOf(i));
           System.out.println(i);
        }
    }

    @Override
    public void run() {
        Bot bot = new Bot();
        BotTimer.Ob test = new Ob();
        test.addObserver(bot);
        test.obs();
    }

}