package bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Device {

    private List<String> categoryDeviceList;
    private Path deviceInfoDBDirectory;

    {
        this.deviceInfoDBDirectory = Paths.get("src/main/resources/dataBaseProducts/mobile");
    }

    public List<String> getCategoryDeviceList(){

        this.categoryDeviceList = new ArrayList<>();

        this.categoryDeviceList.add("galaxy");
        this.categoryDeviceList.add("s");
        this.categoryDeviceList.add("note");
        this.categoryDeviceList.add("z");
        this.categoryDeviceList.add("a");
        this.categoryDeviceList.add("tab");
        this.categoryDeviceList.add("buds");
        this.categoryDeviceList.add("smart");
        this.categoryDeviceList.add("watch");

        return categoryDeviceList;
    }

    public List<Path> findDeviceInfo(Message message){


        String messageText = message.getText().toLowerCase(Locale.ROOT).replaceAll(" ", "");

        messageText = messageText.replace("galaxy", "");

        messageText = messageText.replace("samsung", "");

        List<Path> deviceInfoFilesList = new ArrayList<>();

        try {
            deviceInfoFilesList =  Files.walk(this.deviceInfoDBDirectory)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Path> resultDeviceInfoList = new ArrayList<>();
        for (Path deviceInfoFile : deviceInfoFilesList){
            Pattern pattern = Pattern.compile(messageText);
            Matcher matcher = pattern.matcher(deviceInfoFile.getFileName().toString());
            if (matcher.find()){
                resultDeviceInfoList.add(deviceInfoFile);
            }
        }

        return resultDeviceInfoList;
    }

    public List<String> getDeviceInfoFilesName(){

        List<Path> deviceInfoFilesList = new ArrayList<>();

        try {
            deviceInfoFilesList =  Files.walk(this.deviceInfoDBDirectory)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> deviceInfoFilesNameList = new ArrayList<>();

        for(Path deviceInfo : deviceInfoFilesList){
            deviceInfoFilesNameList.add(deviceInfo.getFileName().toString().split(".")[0]);
        }

        return deviceInfoFilesNameList;
    }
}
