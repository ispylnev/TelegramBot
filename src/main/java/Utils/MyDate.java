package Utils;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class MyDate {
    public static String getTimeNow()  {
        String localDate = String.valueOf(LocalDate.now());
        String localTime = String.valueOf(LocalTime.now());
        return localDate + " " + localTime;
    }

        public static String workingHours (String beginTime, String endTime) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime start = LocalDateTime.parse(beginTime);
            LocalDateTime end = LocalDateTime.parse(endTime);
            Duration duration = Duration.between(start, end);
            //            System.out.println(gf);
            return String.format("%dчасов %dминут %dсекунд%n",
//                    duration.toDays(),
                    duration.toHours() % 24,
                    duration.toMinutes() % 60,
                    duration.toMillis()%100);

        }

    }
