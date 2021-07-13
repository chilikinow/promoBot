package bot;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class BonusOkHttpClient {

    public void getBonusInfo(Message message){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.rightway.omnichannel.ru/samsung/site/login")
                .method("GET", null)
                .addHeader("Cookie", "PHPSESSID=405525bd8409bd2b0942eeb837534790")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
