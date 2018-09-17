package Utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

public class TetsTime {
    public static void main(String[] args) {

        BotTimer botTimer = new BotTimer();
        Timer timer = new Timer(true);
        timer.schedule(botTimer,new Date(System.currentTimeMillis()+10000));
        Scanner scanner = new Scanner(System.in);

        String s = scanner.nextLine();
        System.out.println(botTimer.getString());


    }
}