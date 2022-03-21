package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CurrenciesAndRatiosGenerator {

    public List<Cube> generateCurrenciesAndRatios() {
        String filePath = System.getProperty("user.dir") + "/eurofxref-daily.xml";
        File file = new File(filePath);
        List<Cube> cubeList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("currency")) {
                    List<String> fieldsValues = mapToFieldsList(line);
                    cubeList.add(mapToCube(fieldsValues));
                }
            }
            cubeList.get(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cubeList;
    }

    private Cube mapToCube(List<String> fieldsValues) {
        return new Cube(fieldsValues.get(0), Double.parseDouble(fieldsValues.get(1)));
    }

    private List<String> mapToFieldsList(String line) {
        String firstTextToRemove = "<Cube currency=";
        String secondTextToRemove = " rate=";
        String thirdTextToRemove = "/>";
        String replaced = line.replace(firstTextToRemove, "").replace(secondTextToRemove, "").replace(thirdTextToRemove, "");
        return Arrays.stream(replaced.trim().split("\"\"")).map(field -> field.replace("\"", "")).collect(Collectors.toList());
    }
}
