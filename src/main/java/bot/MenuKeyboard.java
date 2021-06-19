package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MenuKeyboard {

    public ReplyKeyboardMarkup getStartMenu(){

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

        List<KeyboardRow> keyboard = new ArrayList<>();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        List<String> buttonList = new ArrayList<>(PromoInfo.getPromoKeys());

        keyboard.clear();

        buttonList.add("Все акции");

        for (int i = 0; i < buttonList.size(); i++) {
            KeyboardRow keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
