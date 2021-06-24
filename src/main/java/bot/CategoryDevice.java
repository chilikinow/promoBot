package bot;

import java.util.ArrayList;
import java.util.List;

public class CategoryDevice {

    List<String> categoryDeviceList;

    {
        this.categoryDeviceList = new ArrayList<>();

        this.categoryDeviceList.add("galaxy");
        this.categoryDeviceList.add("s");
        this.categoryDeviceList.add("a");
        this.categoryDeviceList.add("tab");
        this.categoryDeviceList.add("buds");
        this.categoryDeviceList.add("smart");
    }

    public List<String> getList(){
        return categoryDeviceList;
    }

}
