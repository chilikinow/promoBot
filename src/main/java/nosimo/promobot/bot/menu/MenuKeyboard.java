package nosimo.promobot.bot.menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface MenuKeyboard {
    ReplyKeyboard getStartMenu();
    ReplyKeyboard getInfoMenu();
    ReplyKeyboard getMobileTVPromoMenu();
    ReplyKeyboard getAppliancesMenu();
}
