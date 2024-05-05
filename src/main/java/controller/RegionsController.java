package controller;

import model.RegionsModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegionsController {
    private RegionsModel model;

    public RegionsController(RegionsModel model) {
        this.model = model;
    }

    public void loadRegionsFromJson() throws IOException {
        // Чтение файла JSON
        InputStream is = getClass().getClassLoader().getResourceAsStream("regions.json");
        if (is == null) {
            throw new FileNotFoundException("File not found: regions.json");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); // Указание кодировки
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        
        // Парсинг JSON
        JSONArray jsonArray = new JSONArray(sb.toString());

        // Извлечение регионов и городов и сохранение их в модели
        Map<String, List<String>> regionsAndCities = new HashMap<>();
        Set<String> regionsSet = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String region = jsonObject.getString("region");
            String city = jsonObject.getString("city");
            if (!regionsAndCities.containsKey(region)) {
                regionsAndCities.put(region, new ArrayList<>());
            }
            regionsAndCities.get(region).add(city);
            regionsSet.add(region);
        }
        model.setRegionsAndCities(regionsAndCities);
        // System.out.println(regionsAndCities);
        // System.out.println(regionsSet);
        // Сохранение списка регионов в regions
        List<String> sortedRegions = new ArrayList<>(regionsSet);
        Collections.sort(sortedRegions);
        model.setRegions(sortedRegions);
        }
}
