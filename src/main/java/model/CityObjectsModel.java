package model;

import java.util.HashMap;
import java.util.Map;

public class CityObjectsModel {
    private Map<String, Integer> cityObjectsCount;

    public CityObjectsModel() {
        this.cityObjectsCount = new HashMap<>();
    }

    public void addObjectCount(String city, int count) {
        cityObjectsCount.put(city, count);
    }

    public Map<String, Integer> getCityObjectsCount() {
        return cityObjectsCount;
    }
}
