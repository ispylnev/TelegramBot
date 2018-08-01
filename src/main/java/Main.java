import Controls.ServerProperties;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args)  {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        ServerProperties serverProperties = new ServerProperties();
        try {
            serverProperties.startGlobalServer(telegramBotsApi);
        } catch (TelegramApiException e) {
            System.out.println("Запуск на удаленном сервере не удался"); //TODO Разобраться как добавить в обработку логами
            try {
                System.out.println("Запукаем через Proxy....");
                serverProperties.startProxyServer(telegramBotsApi);
                System.out.println("Запуск через Proxy выполнен успешно");
            } catch (TelegramApiException e1) {
                System.out.println("Проверьте настройки подключения и доступность вашего сервера ");
                e1.printStackTrace();
            }
        }
    }
}