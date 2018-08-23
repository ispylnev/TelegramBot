package Utils;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class MyDate {
    private static String beginTime;
    private static String endTime;
    private static long duration;

    public static long getDuration() {
        return duration;
    }


    public static String getBeginTime() {
        return beginTime;
    }

    public static void setBeginTime(String beginTime) {
        MyDate.beginTime = beginTime;
    }

    public static String getEndtime() {
        return endTime;
    }

    public static void setEndtime(String endtime) {
       MyDate.endTime = endtime;
    }

    public static String getTimeNow() {
        String localDate = String.valueOf(LocalDate.now());
        String localTime = String.valueOf(LocalTime.now());
        return localDate + " " + localTime;
    }
//Считает общую прожолжительность работы . И возвращает результат в секундах . Подумать на тем стоит ли добавлять переменные в метод
    public static long SetwWorkingHours(){
         try{
             DateTimeFormatter formatterProxy = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime start = LocalDateTime.parse(beginTime.substring(0,23), formatterProxy);
            LocalDateTime end = LocalDateTime.parse(endTime.substring(0,23), formatterProxy);
//             return MyDate.duration = Duration.between(start, end).toSeconds();
             return 0;

        } catch (DateTimeException e){

            e.printStackTrace();
//            DateTimeFormatter formatterServer = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//            LocalDateTime start = LocalDateTime.parse(beginTime, formatterServer);
//            LocalDateTime end = LocalDateTime.parse(endTime, formatterServer);
//            return MyDate.duration = String.valueOf(Duration.between(start, end));
//            //            System.out.println(gf);
//            return MyDate.duration =  String.format("%dчасов %dминут %dсекунд%n",
//                   duration.toDays(),
//                   duration.toHours() % 24,
//                   duration.toMinutes() % 60,
//                   duration.toMillis() % 10);
        }
        return duration = 0;

    }

}
