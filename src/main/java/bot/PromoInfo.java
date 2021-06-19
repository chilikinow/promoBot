package bot;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PromoInfo {

    private static Map <String, String> promoMap;
    private static List <String> promoKeys;

    private PromoInfo(){
    }

    static {
        promoMap = new HashMap<>();
        promoKeys = new ArrayList<>();
    }

    //Singleton
    public static Map<String, String> getInstance(){

        if (promoMap.isEmpty()) {
            promoMap = new HashMap<>(addMap());
        }

        return promoMap;
    }

    public static List<String> getPromoKeys(){

        if (promoKeys.isEmpty()) {
            promoKeys = new ArrayList<>(addKeysList());
        }

        return promoKeys;
    }

    private static List<String> addKeysList(){

//        for (Map.Entry<String, String> entry : promoMap.entrySet()) {
//            promoKeys.add(String.valueOf(entry.getKey()));
//        }

        promoKeys.add("ТРЕЙД-ИН");
        promoKeys.add("Скидка до 5000 рублей на Galaxy Watch3 | Watch Active2 | Buds");
        promoKeys.add("Скидки по акции ценопад");
        promoKeys.add("Купи Galaxy Tab S6 или Tab A10.1 (2019) и получи чехол moonfish в подарок (только в рознице)");
        promoKeys.add("Купи Galaxy S21|S21+|S21Ultra и получи Buds Live в подарок");
        promoKeys.add("Купи ТВ и получи саундбар  в подарок");
        promoKeys.add("Скидка до 8 000 рублей на Galaxy Tab S7 | S7+ | S6 Lite | А7");
        promoKeys.add("Два чехла для Galaxy Buds|Buds+ по цене одного");
        promoKeys.add("Купи Frame ТВ и получи рамку в  подарок");
        promoKeys.add("4 месяца подписки на YouTube Premium");
        promoKeys.add("Купи Lifestyle или QLED 8K ТВ и получи акустику в подарок");
        promoKeys.add("Купи The Premiere и получи акустику  в подарок");

//        System.out.println("keys List");
//        for (int i = 0; i < promoKeys.size(); i++) {
//            System.out.println(promoKeys.get(i));
//        }

        return promoKeys;
    }

    private static Map<String, String> addMap() {

        Path PromoFilePath = Paths.get("src/main/resources/Samsung_Календарь акций.xlsx");
        XSSFWorkbook workBook = null;
        try (FileInputStream fIS = new FileInputStream(PromoFilePath.toFile())) {
            workBook = new XSSFWorkbook(fIS);//получили книгу exel
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        } catch (IOException e) {
            System.out.println("Файл не читается.");
        }
        XSSFSheet sheet = workBook.getSheetAt(0);//получили страницу книги

        ArrayList<Row> rowList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()){
            rowList.add(rowIterator.next());//получаем лист строк
        }

        Map <String, String> map = new HashMap<>();

        //перебираем лист строк без первой строки, где хронятся названия столбцов
        for (int i = 1; i < rowList.size(); i++) {
            ArrayList<Cell> cellList = new ArrayList<>();
            Iterator<Cell> cellIterator = rowList.get(i).iterator();
            //формируем лист ячеек из строки
            while (cellIterator.hasNext()){
                cellList.add(cellIterator.next());
            }

            //переменная для формирования информации со всех ячеек (подробности акции) кроме названия акции
            StringBuilder valueForMap = new StringBuilder();
            for (int j = 1; j < cellList.size(); j++) {
                String cellString = cellList.get(j).getStringCellValue();

                //проверяем значение ячейки на наличие символов, изключаем пустые
                Pattern pattern = Pattern.compile("\\w+");
                Matcher matcher = pattern.matcher(cellString);
                while (matcher.find()) {
                    //формируем подробную информацию об условиях и механики проведения акции
                    valueForMap.append(cellString + "\n\n");
                    break;
                }
            }
            //создаем элемент Map, где key- название акции, value- подробности акции
            map.put(cellList.get(0).getStringCellValue(), valueForMap.toString());

        }

//        блок для проверки вывода Map в консоль
//            for (Map.Entry<String, String> entry: map.entrySet()){
//                StringBuilder mapToString = new StringBuilder();
//                mapToString.append(entry.getKey())
//                        .append("\n\n")
//                        .append(entry.getValue());
//                System.out.println(mapToString.toString());
//            }

        return map;
    }
}