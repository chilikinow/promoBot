package bot;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.*;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;


public class Bonus {

    public void getCard(Message message){

        try {

            BotData botData = new BotData();

            RestAssured.baseURI = botData.getBonusBaseURI();

            Response tokenResponse = RestAssured.given().get("/login");
            String tokenResponseBodyString = tokenResponse.getBody().asString();

            Document document = Jsoup.parse(tokenResponseBodyString);
            Elements elements = document.select("input");

            String tokinValue = null;
            for (Element element : elements){
                if (element.attr("name").equals("YII_CSRF_TOKEN")) {
                    tokinValue = element.attr("value");
                }
            }
            System.out.println(tokinValue);

            JSONObject requestBody = new JSONObject();
            requestBody.put("YII_CSRF_TOKEN", tokinValue);
            requestBody.put("LoginForm[username]", botData.getBonusLogin());
            requestBody.put("LoginForm[password]", botData.getBonusPassword());
            requestBody.put("LoginForm[rememberMe]", 0);

            Response indexResponse = given()
                    .body(requestBody)
                    .redirects()
                    .follow(true)
                    .post("/index");

//            Map<String, String> headersMap = new HashMap<>();
//            List<Header> headersList = new ArrayList<>(indexResponse.getHeaders().asList());
//            for (Header header: headersList){
//                headersMap.put(header.getName(), header.getValue());
//            }
//            Response indexResponse = null;
//            if (indexResponse.getStatusCode() == 302)
//                indexResponse = findBody(indexResponse);



            System.out.println(indexResponse.body().asString());
//            System.out.println("Cookies");
//            System.out.println(indexResponse.getCookies());
//            System.out.println("Heders");
//            indexResponse.getHeaders().forEach(System.out::println);
//            System.out.println("SessionId");
//            System.out.println(indexResponse.getSessionId());
//            System.out.println("StatusCode");
//            System.out.println(indexResponse.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private Response findBody(Response response) {
        while (response.cookie("at") == null) {

            Cookies cookies = response.detailedCookies();
            String urlString = "https://www.rightway.omnichannel.ru/samsung/site/index";
            if (urlString == null) {
                throw new IllegalStateException("Url for parse is null.");
            }
            Map<String, String> params = new HashMap<>();
            Map<String, String> headersMap = new HashMap<>();
            List<Header> headersList = new ArrayList<>(response.getHeaders().asList());
            for (Header header: headersList){
                headersMap.put(header.getName(), header.getValue());
            }


            response = with().spec(new RequestSpecBuilder()
                    .setBaseUri(urlString)
                    .addParams(params)
                    .setConfig(RestAssuredConfig.newConfig().redirect(RedirectConfig.redirectConfig().followRedirects(true)))
                    .addCookies(cookies)
                    .addHeaders(headersMap)
                    .build())
                    .get();
        }
        return response;
    }

}
