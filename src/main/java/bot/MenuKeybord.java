package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

class MenuKeybord {


    public ReplyKeyboardMarkup getFirstMenu(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
        replyKeyboardMarkup.setResizeKeyboard(false);//размер клавиатуры адаптируется под количество клавиш
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрыть клавиатуру после использования
        replyKeyboardMarkup.setSelective(false);//персонолизация клавиатуры

        keyboard.clear();

        List<String> buttonList = new ArrayList<>();
        buttonList.add("Инфо");
        buttonList.add("Помощь");
        buttonList.add("Справка");
        buttonList.add("Акции");

        for (int i = 0; i < buttonList.size(); i++) {
            KeyboardRow keyboardRowBuffer = new KeyboardRow();
            keyboardRowBuffer.add(buttonList.get(i));
            keyboard.add(keyboardRowBuffer);
        }

//        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    //            keyboard.clear();
//            for (Map.Entry<String, String> entry: promoInfoMap.entrySet()) {
//                keyboardFirstRow.add(entry.getKey());
//            }
//            keyboard.add(keyboardFirstRow);
//            replyKeyboardMarkup.setKeyboard(keyboard);
//            sendMessageWIthKeyboard("Акции");

}
