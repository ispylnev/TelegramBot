package Utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

public class TetsTime {
    public static void main(String[] args) throws InterruptedException {

        BotTimer botTimer = new BotTimer();
        Timer timer = new Timer(true);
        timer.schedule(botTimer,new Date(System.currentTimeMillis()+5000));
        System.out.println("d");
        Thread.sleep(20000);


    }
}