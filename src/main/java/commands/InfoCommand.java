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

        for (int i = 0; i < new MenuKeyboard().getStartMenu().getKeyboard().size(); i++) {
            response.append( new MenuKeyboard().getStartMenu().getKeyboard().get(i) + "\n");
        }
        response.append("\nДля вызова основного Меню вы можете нажать на любую ")
                .append("ссылку /menu в чате, или отправить любое сообщение.");

        return response.toString();
    }
}
