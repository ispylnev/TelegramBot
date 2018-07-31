package Controls;

import Utils.FileUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.Properties;

public class ServerProperties {

    private static Properties properties = new Properties();
    private static String bot = "Pylnevbot";
    private static String token = "648680294:AAFKL6j52_tC4E_a-me8Z0-YPOQD_dgiFH4";
    private static String urlProxy = FileUtils.getUrlProxy(properties);
    private static Integer proxyPort = FileUtils.geProxyPort(properties);
    private static String proxyUser = FileUtils.getUserProxy(properties);
    private static String proxyPassword = FileUtils.getProxyPassword(properties);

    public void startProxyServer(TelegramBotsApi telegramBotsApi) {

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(urlProxy, proxyPort),
                new UsernamePasswordCredentials(proxyUser, proxyPassword));
        HttpHost httpHost = new HttpHost(urlProxy, proxyPort);
        RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(true).build();
        botOptions.setRequestConfig(requestConfig);
        botOptions.setCredentialsProvider(credentialsProvider);
        botOptions.setHttpProxy(httpHost);
        try {
            telegramBotsApi.registerBot(new Bot(botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void startGlobalServer(TelegramBotsApi telegramBotsApi) {
        Bot bot = new Bot();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
