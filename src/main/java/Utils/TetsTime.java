package Utils;

import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Timer;

public class TetsTime implements Observer {

    public static void main(String[] args) throws InterruptedException {
        BotTimer botTimer = new BotTimer();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(botTimer, 1,1000);
        Thread.sleep(500);


    }

    @Override
    public  void update(Observable o, Object arg) {
        System.out.println("ВОт это да ");
    }
}
