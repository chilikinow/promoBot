package bot;

import java.util.ArrayList;
import java.util.List;

public class StartMenu {

    private List<String> startMenuList;

    {
        this.startMenuList = new ArrayList<>();

        this.startMenuList.add("Акции Мобайл ТВ");
        this.startMenuList.add("Акции БТ");
        this.startMenuList.add("Характеристики устройств");
        this.startMenuList.add("Инфо БТ");
        this.startMenuList.add("Инфо Мобайл");
        this.startMenuList.add("Инфо ТВ");
        this.startMenuList.add("Сервис");
    }

    public List<String> getList(){
        return startMenuList;
    }

}
