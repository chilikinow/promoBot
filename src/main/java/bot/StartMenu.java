package bot;

import java.util.ArrayList;
import java.util.List;

public class StartMenu {

    private List<String> startMenuList;

    {
        this.startMenuList = new ArrayList<>();

        this.startMenuList.add("Акции Мобайл ТВ");
        this.startMenuList.add("Акции Бытовая техника");
        this.startMenuList.add("Характеристики устройств");
        this.startMenuList.add("Инфо");
        this.startMenuList.add("Помощь");
    }

    public List<String> getList(){
        return startMenuList;
    }

}
