package nosimo.promobot.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.*;

public class MenuKeyboard {

    public ReplyKeyboardMarkup getStartMenu(){
        return createStartMenuWithRKM();
//        return createStartMenuWithIKM();
    }

    public ReplyKeyboardMarkup getMobileTVPromoMenu(){

        Set<String> set = new HashSet<>(PromoInfo.getInstancePromoMobileTV().keySet());

        return createPromoMenuRKM(set);
//        return createPromoMenuIKM(set);
    }

    public ReplyKeyboardMarkup getAppliancesMenu(){

        Set<String> set = new HashSet<>(PromoInfo.getInstancePromoAppliances().keySet());

        return createPromoMenuRKM(set);
//        return createPromoMenuIKM(set);
    }

    private ReplyKeyboardMarkup createPromoMenuRKM(Set mapKeySet){

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

    private ReplyKeyboardMarkup createStartMenuWithRKM(){

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



    //InlineKeyboardMarkup



    public InlineKeyboardMarkup getMenuIKM(String buttonName, String callbackData){
        Map <String,String> map = new HashMap<>();
        map.put(buttonName, callbackData);
        return createMenuIKM(map);
    }

    public InlineKeyboardMarkup getMenuIKM(Map<String,String> map){
        return createMenuIKM(map);
    }

    private InlineKeyboardMarkup createMenuIKM(Map<String,String> map){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Map.Entry<String,String> entry : map.entrySet()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(entry.getKey());
            button.setCallbackData(entry.getValue());


            List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
            keyboardRow.add(button);
            rowList.add(keyboardRow);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createStartMenuWithIKM(){

        List<String> buttonList = new ArrayList<>(new StartMenu().getList());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(0));
        button1.setCallbackData(buttonList.get(0));
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(buttonList.get(1));
        button2.setCallbackData(buttonList.get(1));

        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        keyboardRow.add(button2);
        rowList.add(keyboardRow);

        button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(2));
        button1.setCallbackData(buttonList.get(2));

        keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        rowList.add(keyboardRow);

        button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(3));
        button1.setCallbackData(buttonList.get(3));
        button2 = new InlineKeyboardButton();
        button2.setText(buttonList.get(4));
        button2.setCallbackData(buttonList.get(4));
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText(buttonList.get(5));
        button3.setCallbackData(buttonList.get(5));

        keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        keyboardRow.add(button2);
        keyboardRow.add(button3);
        rowList.add(keyboardRow);

        button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(6));
        button1.setCallbackData(buttonList.get(6));

        keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        rowList.add(keyboardRow);

        button1 = new InlineKeyboardButton();
        button1.setText(buttonList.get(7));
        button1.setCallbackData(buttonList.get(7));

        keyboardRow = new ArrayList<>();
        keyboardRow.add(button1);
        rowList.add(keyboardRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createPromoMenuIKM(Set mapKeySet){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<String> buttonList = new ArrayList<>(mapKeySet);

        for (int i = 0; i < buttonList.size(); i++) {

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonList.get(i));
            if (buttonList.get(i).length() > 30) {
                button.setCallbackData(buttonList.get(i).substring(0, 30));
            } else {
                button.setCallbackData(buttonList.get(i));
            }

            List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
            keyboardRow.add(button);
            rowList.add(keyboardRow);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
