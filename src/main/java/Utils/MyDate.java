package Utils;
import Controls.ServerProperties;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.zip.DataFormatException;


public class MyDate {
    public static String getTimeNow() {
        String localDate = String.valueOf(LocalDate.now());
        String localTime = String.valueOf(LocalTime.now());
        return localDate + " " + localTime;
    }

    public static String workingHours(String beginTime, String endTime){
         try{
             DateTimeFormatter formatterProxy = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
            LocalDateTime start = LocalDateTime.parse(beginTime, formatterProxy);
            LocalDateTime end = LocalDateTime.parse(endTime, formatterProxy);
            Duration duration = Duration.between(start, end);
            //            System.out.println(gf);
            return String.format("%dчасов %dминут %dсекунд(ы)%n",
//                    duration.toDays(),
                    duration.toHours() % 24,
                    duration.toMinutes() % 60,
                    duration.toMillis() % 10);

        } catch (DateTimeException e){
            DateTimeFormatter formatterServer = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime start = LocalDateTime.parse(beginTime, formatterServer);
            LocalDateTime end = LocalDateTime.parse(endTime, formatterServer);
            Duration duration = Duration.between(start, end);
            //            System.out.println(gf);
            return String.format("%dчасов %dминут %dсекунд%n",
//                    duration.toDays(),
                    duration.toHours() % 24,
                    duration.toMinutes() % 60,
                    duration.toMillis() % 10);
        }

    }
}