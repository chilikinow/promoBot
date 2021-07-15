//package bot;
//
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//import org.telegram.telegrambots.meta.api.objects.Message;
//
//import java.io.IOException;
//
//public class BonusOkHttpClient {
//
//    public void getBonusInfo(Message message){
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://www.rightway.omnichannel.ru/samsung/site/login")
//                .method("GET", null)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            System.out.println(response.body());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
