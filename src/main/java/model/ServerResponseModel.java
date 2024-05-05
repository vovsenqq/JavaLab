package model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ServerResponseModel {
    private List<JSONObject> data;

    public ServerResponseModel() {
        this.data = new ArrayList<>();
    }

    public void addData(JSONObject jsonObject) {
        this.data.add(jsonObject);
    }

    public List<JSONObject> getData() {
        return this.data;
    }

    public void clearData() {
        this.data.clear();
    }

    public int getSize() {
        return this.data.size();
    }

}