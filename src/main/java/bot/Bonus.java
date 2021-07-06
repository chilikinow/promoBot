package bot;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.api.objects.Message;

import static io.restassured.RestAssured.given;


public class Bonus {

    public void getCard(Message message){

        try {

            BotData botData = new BotData();

            RestAssured.baseURI = botData.getBonusBaseURI();

            String tokenResponseBodyString = RestAssured.given().get("/login").getBody().asString();

            Document document = Jsoup.parse(tokenResponseBodyString);
            Elements elements = document.select("input");

            String tokinValue = null;
            for (Element element : elements){
                if (element.attr("name").equals("YII_CSRF_TOKEN")) {
                    tokinValue = element.attr("value");
                }
            }
            System.out.println(tokinValue);

            JSONObject requestParams = new JSONObject();
            requestParams.put("YII_CSRF_TOKEN", tokinValue);
            requestParams.put("LoginForm[username]", botData.getBonusLogin());
            requestParams.put("LoginForm[password]", botData.getBonusPassword());

            System.out.println(requestParams);

            String loginResponseBodyString = given().body(requestParams.toString()).post("/login").getBody().asString();

            System.out.println(loginResponseBodyString);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
