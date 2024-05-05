package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TypesForRequest {
    
    private List<String> titles;
    private Map<String, String> titleEntityMap; // новый словарь

    public TypesForRequest() {
        this.titles = new ArrayList<>();
        this.titleEntityMap = new HashMap<>(); // инициализация словаря
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public Map<String, String> getTitleEntityMap() {
        return titleEntityMap;
    }

    public void setTitleEntityMap(Map<String, String> titleEntityMap) {
        this.titleEntityMap = titleEntityMap;
    }
}