package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class MenuKeybord {

    private static List <String> buttonPromoList;

    static {
        buttonPromoList = new ArrayList<>();
    }

    public static List<String> getButtonPromoList(){
        return buttonPromoList;
    }


    public ReplyKeyboardMarkup getFirstMenu(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        replyKeyboardMarkup.setResizeKeyboard(false);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        keyboard.clear();

        List<String> buttonList = new ArrayList<>();
        buttonList.add("Акции");
        buttonList.add("Характеристики устройств");
        buttonList.add("Инфо");
        buttonList.add("Помощь");


        for (int i = 0; i < buttonList.size(); i++) {
            KeyboardRow keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getPromoMenu(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        replyKeyboardMarkup.setResizeKeyboard(true);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        Map <String, String> promoInfoMap = Promo.getInstance();
        List<String> buttonList = new ArrayList<>();

        keyboard.clear();

        buttonList.add("ТРЕЙД-ИН");
        buttonList.add("Скидка до 5000 рублей на Galaxy Watch3 | Watch Active2 | Buds");
        buttonList.add("Скидки по акции ценопад");
        buttonList.add("Купи Galaxy Tab S6 или Tab A10.1 (2019) и получи чехол moonfish в подарок (только в рознице)");
        buttonList.add("Купи Galaxy S21|S21+|S21Ultra и получи Buds Live в подарок");
        buttonList.add("Купи ТВ и получи саундбар  в подарок");
        buttonList.add("Скидка до 8 000 рублей на Galaxy Tab S7 | S7+ | S6 Lite | А7");
        buttonList.add("Два чехла для Galaxy Buds|Buds+ по цене одного");
        buttonList.add("Купи Frame ТВ и получи рамку в  подарок");
        buttonList.add("4 месяца подписки на YouTube Premium");
        buttonList.add("Купи Lifestyle или QLED 8K ТВ и получи акустику в подарок");
        buttonList.add("Купи The Premiere и получи акустику  в подарок");
        buttonList.add("Все акции");

//        for (Map.Entry<String, String> entry: promoInfoMap.entrySet()) {
//                buttonList.add(entry.getKey());
//        }
//        buttonList.add("Все акции");

//        System.out.println("Button List");
//        for (int i = 0; i < buttonList.size(); i++) {
//            System.out.println(buttonList.get(i));
//        }


        buttonPromoList = buttonList;


        for (int i = 0; i < buttonList.size(); i++) {
            KeyboardRow keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
