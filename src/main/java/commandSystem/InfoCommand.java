package commandSystem;

import bot.StartMenu;

public class InfoCommand{

    public String create() {
        StringBuilder response = new StringBuilder();

        response.append("Список доступных устройств:\n\n");

//        for (String elementMenu: new StartMenu().getList()) {
//            response.append( elementMenu + "\n");
//        }

        response.append("\nДля вызова основного Меню достаточно отправить\n")
                .append("любое сообщение или использовать ссылку /startMenu в чате.");

        return response.toString();
    }
}
