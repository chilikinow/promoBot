package commands;

public enum CommandType {

    HELP("Использовать в критических ситуациях!)"),
    INFO("Описание возможностей бота.");

    private String description;

    CommandType(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
