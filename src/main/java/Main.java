import Controls.ServerProperties;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        ServerProperties serverProperties = new ServerProperties();
//        serverProperties.startProxyServer(telegramBotsApi);
        serverProperties.startGlobalServer(telegramBotsApi);
    }

}