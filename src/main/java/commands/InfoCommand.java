package commands;

import bot.MenuKeyboard;

public class InfoCommand implements Command{

    private CommandType type = CommandType.INFO;
    private String info;

    public InfoCommand(){
        info = init();
    }

    public String getInfo(){
        return info;
    }


    @Override
    public String init() {
        StringBuilder response = new StringBuilder();

        response.append("Привет!\n")
                .append("Бот позволяет узнать актуальную информацию ")
                .append("о проходящих в компании акциях, ")
                .append("так же узнать подробные характеристики ")
                .append("запрашиваемой техники.\n\n")
                .append("Список команд:\n\n");

        for (int i = 0; i < MenuKeyboard.getButtonFirstMenuList().size(); i++) {
            response.append(MenuKeyboard.getButtonFirstMenuList().get(i) + "\n");
        }
        response.append("\nТак же в любой момент вы можете отправить ")
                .append("любое сообщение для получения стартового меню.");

        return response.toString();
    }
}
