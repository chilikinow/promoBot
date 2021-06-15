package commands;//

public class InfoCommand implements Command{

    private CommandType type = CommandType.INFO;

    @Override
    public String init() {
        StringBuilder response = new StringBuilder();

        response.append("Бот позволяет узнать актуальную информацию\n")
                .append("о действующих акция\n")
                .append("и\n")
                .append("Оперативно получить подробную информацию о технике.");

//        sendMessage(message, "Доступные команды:\n"
//                + "/help\n"
//                + "/info\n"
//                + "Акции\n"
//                + "Модель устройства");
//
//        for (CommandType type: CommandType.values()) {
//            String command = type.toString().toLowerCase(Locale.ROOT);
//            response.append(String.format("/%s - %s%n", command, type.getDescription()));
//        }

        return response.toString();
    }
}
