package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ApiClient;
import model.TypesForRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TypesHandler {
    private ApiClient apiClient;
    private TypesForRequest model;

    public TypesHandler(ApiClient apiClient, TypesForRequest model) {
        this.apiClient = apiClient;
        this.model = model;
    }

    public void loadData() throws IOException {
        String apiKey = apiClient.getApiKey();
        String apiUrl = "https://opendata.mkrf.ru/v2/?o=entityName";

        // Запрос к API
        String responseData = apiClient.fetchDataFromApi(apiUrl, "X-API-KEY", apiKey);
        
        // Парсинг JSON ответа и сохранение данных в модели
        Map<String, String> titleEntityMap = parseJson(responseData);
        System.out.println("До фильтрации: " + titleEntityMap);

        Set<String> filter = new HashSet<>();
        // filter.add("Цирки");
        filter.add("Парки");
        filter.add("Кинотеатры");
        filter.add("Концертные залы");
        filter.add("Образовательные учреждения");
        filter.add("Библиотеки");
        filter.add("Музеи и галереи");
        filter.add("Филармонии и концертные залы");

        titleEntityMap.keySet().retainAll(filter);  // Фильтрация

        // System.out.println("После фильтрации: " + titleEntityMap);

        model.setTitleEntityMap(titleEntityMap);
    }


    public Map<String, String> parseJson(String json) {
        // Создание объекта JSONObject из строки JSON
        JSONObject jsonObject = new JSONObject(json);
    
        // Получение массива JSON из поля "data"
        JSONArray dataArray = jsonObject.getJSONArray("data");
    
        // Извлечение значений "title" и "entityName" из каждого объекта в массиве и сохранение их в словаре
        Map<String, String> titleEntityMap = new HashMap<>();
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataObject = dataArray.getJSONObject(i);
            String title = dataObject.getString("title");
            String entityName = dataObject.getString("entityName");
            titleEntityMap.put(title, entityName);
        }
    
        return titleEntityMap;
    }

}