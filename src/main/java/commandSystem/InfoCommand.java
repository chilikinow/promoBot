package commandSystem;

import bot.StartMenu;

public class InfoCommand{

    public String create() {
        StringBuilder response = new StringBuilder();

        response.append("Привет!\n")
                .append("Бот позволяет узнать актуальную информацию ")
                .append("о проходящих в компании акциях, ")
                .append("так же узнать подробные характеристики ")
                .append("запрашиваемой техники.\n\n")
                .append("Список команд:\n\n");

        for (String elementMenu: new StartMenu().getList()) {
            response.append( elementMenu + "\n");
        }
        response.append("\nДля вызова основного Меню вы можете нажать на любую ")
                .append("ссылку /menu в чате, или отправить любое сообщение.");

        return response.toString();
    }
}
