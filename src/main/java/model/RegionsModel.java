package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionsModel {
    private Map<String, List<String>> regionsAndCities;
    private List<String> regions;

    public RegionsModel() {
        this.regionsAndCities = new HashMap<>();
        this.regions = new ArrayList<>(); // Инициализация списка регионов
    }

    public Map<String, List<String>> getRegionsAndCities() {
        return regionsAndCities;
    }

    public void setRegionsAndCities(Map<String, List<String>> regionsAndCities) {
        this.regionsAndCities = regionsAndCities;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getCitiesInRegion(String region) {
        return regionsAndCities.get(region);
    }

}