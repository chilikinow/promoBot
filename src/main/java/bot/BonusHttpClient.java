package bot;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class BonusHttpClient {

    public void getCard(Message message){

        BotData botData = new BotData();
        String urlBase = botData.getBonusBaseURI();

        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManager = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certificate, String str) {}
                    public void checkServerTrusted(X509Certificate[] certificate, String str) {}
                }
        };
        try {
            context.init(null, trustManager, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        CookieStore httpCookieStore = new BasicCookieStore();
        CloseableHttpClient httpСlient = HttpClientBuilder
                .create()
                .setSSLSocketFactory(socketFactory)
                .setDefaultCookieStore(httpCookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        HttpGet httpGetRequest = new HttpGet(urlBase+"/login");
        try {

            HttpResponse httpResponse = httpСlient.execute(httpGetRequest);

            String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));
            Document document = Jsoup.parse(httpResponseString);
            Elements elements = document.select("input");

            String tokinKey = "YII_CSRF_TOKEN";
            String tokinValue = null;
            for (Element element : elements){
                if (element.attr("name").equals(tokinKey)) {
                    tokinValue = element.attr("value");
                }
            }
            System.out.println(tokinValue);//Получили Токен

            HttpPost httpPostRequest = new HttpPost(urlBase+"/index");

            JSONObject requestBody = new JSONObject();
            requestBody.put("YII_CSRF_TOKEN", tokinValue);
            requestBody.put("LoginForm[username]", botData.getBonusLogin());
            requestBody.put("LoginForm[password]", botData.getBonusPassword());
            requestBody.put("LoginForm[rememberMe]", 0);
            StringEntity body = new StringEntity("details="+requestBody.toString(), ContentType.APPLICATION_JSON);
            httpPostRequest.setEntity(body);

            httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPostRequest.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpPostRequest.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPostRequest.addHeader("Connection", "keep-alive");


            httpResponse = httpСlient.execute(httpPostRequest);

            httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));
            System.out.println(httpResponseString);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
