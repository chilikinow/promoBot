package bot;//

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

class Promo {

    private Map <String, String> promoInfoMap;

    public Promo(){
            this.promoInfoMap = addMap();
    }

    public Map<String, String> getPromoInfoMap(){
        return this.promoInfoMap;
    }


    private Map<String, String> addMap() {

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

        //блок для проверки вывода Map в консоль
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