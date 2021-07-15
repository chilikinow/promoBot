package bot;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Bonus {

    private CloseableHttpClient httpСlient;
    private String tokenValue;
    private String tokenKey;

    public String getInfo(String messageText) {

        BotData botData = new BotData();
        String urlBase = botData.getBonusBaseURI();

        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManager = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certificate, String str) {
                    }

                    public void checkServerTrusted(X509Certificate[] certificate, String str) {
                    }
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

        this.httpСlient = HttpClientBuilder
                .create()
                .setSSLSocketFactory(socketFactory)
                .setDefaultCookieStore(httpCookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        HttpGet httpGetRequest = new HttpGet(urlBase + "/site/login");
        try {

            HttpResponse httpResponse = this.httpСlient.execute(httpGetRequest);

            String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));
            Document document = Jsoup.parse(httpResponseString);
            Elements elements = document.select("input");

            this.tokenKey = "YII_CSRF_TOKEN";
            this.tokenValue = null;
            for (Element element : elements) {
                if (element.attr("name").equals(tokenKey)) {
                    this.tokenValue = element.attr("value");
                }
            }
//            System.out.println(this.tokenValue);//Получили Токен

            HttpPost httpPostRequest = new HttpPost(urlBase + "/site/login");
            StringEntity body = new StringEntity(this.tokenKey + "=" + this.tokenValue
                    + "&LoginForm[username]=" + botData.getBonusUserName()
                    + "&LoginForm[password]=" + botData.getBonusPassword());
            httpPostRequest.setEntity(body);
            httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
            this.httpСlient.execute(httpPostRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // закончили авторизацию


        String validPhoneNumber = "+7("
                + messageText.substring(0, 3)
                + ")"
                + messageText.substring(3, 6)
                + "-"
                + messageText.substring(6);

        String validCardNumber = messageText;


        if (messageText.startsWith("9")) {

            this.httpСlient = HttpClientBuilder
                    .create()
                    .setSSLSocketFactory(socketFactory)
                    .setDefaultCookieStore(httpCookieStore)
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build();

            try {
                HttpPost httpPostRequest = new HttpPost(urlBase + "/card/search");
                httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");

                StringEntity body = new StringEntity(this.tokenKey + "=" + this.tokenValue
                        + "&CardSearchFormModel[phone]=" + validPhoneNumber + "&yt0=Найти");
                httpPostRequest.setEntity(body);
                HttpResponse httpResponse = this.httpСlient.execute(httpPostRequest);
                String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));

                Document document = Jsoup.parse(httpResponseString);
                Elements elements = document.select("form");

                for (Element element : elements) {
                    if (element.attr("target").equals("_blank")) {
                        validCardNumber = element.attr("id");
                    }
                }
                validCardNumber = validCardNumber.replace("cardForm_", "");
//                System.out.println(validCardNumber);//Получили номер карты

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        this.httpСlient = HttpClientBuilder
                    .create()
                    .setSSLSocketFactory(socketFactory)
                    .setDefaultCookieStore(httpCookieStore)
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build();

        StringBuilder bonusInfo = new StringBuilder();

            try {
                HttpPost httpPostRequest = new HttpPost(urlBase + "/card/info");
                httpPostRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");

                StringEntity body = new StringEntity(this.tokenKey + "=" + this.tokenValue
                        + "&CardInfoFormModel[search]=" + validCardNumber);
                httpPostRequest.setEntity(body);
                HttpResponse httpResponse = this.httpСlient.execute(httpPostRequest);
                String httpResponseString = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                Document document = Jsoup.parse(httpResponseString);
                Elements elements = document.select("label");

                int counterLine = 0;
                for (Element element : elements) {

                    Pattern pattern = Pattern.compile(">(.+)</");
                    Matcher matcher = pattern.matcher(element.toString());
                    while (matcher.find()){
                        String matcherBuffer = matcher.group(1).trim();

                        if (matcherBuffer.startsWith("<")
                                || matcherBuffer.startsWith("Поиск")
                                || matcherBuffer.startsWith("Программа"))
                            continue;
                        bonusInfo.append(matcher.group(1).trim() + " ");
                        counterLine++;
                        if (counterLine % 2 == 0)
                            bonusInfo.append("\n");
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        return bonusInfo.toString();
    }
}
