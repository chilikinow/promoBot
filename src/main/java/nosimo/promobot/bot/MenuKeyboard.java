package nosimo.promobot.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

public class MenuKeyboard {

    public ReplyKeyboardMarkup getStartMenu(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        replyKeyboardMarkup.setResizeKeyboard(false);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        keyboard.clear();

        List<String> buttonList = new ArrayList<>(new StartMenu().getList());

        KeyboardRow keyboardRowBuffer = new KeyboardRow();
        for (int i = 0; i < 2; i++) {
            keyboardRowBuffer.add(buttonList.get(i));
        }
        keyboard.add(keyboardRowBuffer);

        keyboardRowBuffer = new KeyboardRow();
        for (int i = 2; i <= 2; i++) {
            keyboardRowBuffer.add(buttonList.get(i));
        }
        keyboard.add(keyboardRowBuffer);

        keyboardRowBuffer = new KeyboardRow();
        for (int i = 3; i <= 5; i++) {
            keyboardRowBuffer.add(buttonList.get(i));
        }
        keyboard.add(keyboardRowBuffer);

        keyboardRowBuffer = new KeyboardRow();
        for (int i = 6; i <= 6; i++) {
            keyboardRowBuffer.add(buttonList.get(i));
        }
        keyboard.add(keyboardRowBuffer);

        keyboardRowBuffer = new KeyboardRow();
        for (int i = 7; i <= 7; i++) {
            keyboardRowBuffer.add(buttonList.get(i));
        }
        keyboard.add(keyboardRowBuffer);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMobileTVPromoMenu(){

        Set<String> set = new HashSet<>(PromoInfo.getInstancePromoMobileTV().keySet());

        return createPromoMenu(set);
    }

    public ReplyKeyboardMarkup getAppliancesMenu(){

        Set<String> set = new HashSet<>(PromoInfo.getInstancePromoAppliances().keySet());

        return createPromoMenu(set);
    }

    private ReplyKeyboardMarkup createPromoMenu(Set mapKeySet){

        List<KeyboardRow> keyboard = new ArrayList<>();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        List<String> buttonList = new ArrayList<>(mapKeySet);

        keyboard.clear();

        for (int i = 0; i < buttonList.size(); i++) {
            KeyboardRow keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
