package controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import model.ApiClient;
import model.CityObjectsModel;
import model.RegionsModel;
import model.SelectedObjectModel;
import model.SelectedRegionModel;

public class DataForGraphController implements ActionListener {
    private RegionsModel regionsModel;
    private SelectedRegionModel selectedRegionModel;
    private SelectedObjectModel selectedObjectModel;
    private ApiClient apiClient;

    public DataForGraphController(RegionsModel regionsModel, SelectedRegionModel selectedRegionModel,
    SelectedObjectModel selectedObjectModel, ApiClient apiClient) {
        this.regionsModel = regionsModel;
        this.regionsModel = regionsModel;
        this.selectedRegionModel = selectedRegionModel;
        this.selectedObjectModel = selectedObjectModel;
        this.apiClient = apiClient;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        try {
            String apiKey = apiClient.getApiKey();
            String selectedObj = selectedObjectModel.getSelectedObject();
            String selectedRegion = selectedRegionModel.getSelectedRegion();
            // Получение списка городов для выбранного региона
            List<String> cities = regionsModel.getCitiesInRegion(selectedRegion);

            // Создание новой модели для хранения количества объектов
            CityObjectsModel cityObjectsModel = new CityObjectsModel();

            // Выполнение поиска по каждому городу
            for (String city : cities) {
                city = city.replace(" ", "%20");

                // Формирование URL
                String apiUrl = "https://opendata.mkrf.ru/v2/" + selectedObj + "/$?f={\"data.general.locale.name\":{\"$search\":\"" + city + "\"}}&l=0";

                // Запрос к API
                String responseData = apiClient.fetchDataFromApi(apiUrl, "X-API-KEY", apiKey);
                // Парсинг строки JSON
                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                // Сохранение количества объектов в модели
                cityObjectsModel.addObjectCount(city, jsonArray.length());
            }

            // Вывод количества объектов в каждом городе
            System.out.println("Количество объектов в каждом городе: " + cityObjectsModel.getCityObjectsCount());

            // Создание датасета для графика
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Integer> entry : cityObjectsModel.getCityObjectsCount().entrySet()) {
                String cityName = entry.getKey().replace("%20", " "); // Замена %20 на пробелы
                dataset.addValue(entry.getValue(), "Objects", cityName);
            }
            

            // Создание графика
            JFreeChart chart = ChartFactory.createBarChart(
                    "Количество объектов в каждом городе",
                    "Район",
                    "Количество объектов",
                    dataset);

            // Создание панели для графика и добавление ее в новое окно
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(760, 567));
            CategoryPlot plot = chart.getCategoryPlot();
            CategoryAxis axis = plot.getDomainAxis();
            axis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10)); // Установка размера шрифта в 10
            // Создание нового окна
            JFrame graphFrame = new JFrame("Graph");
            graphFrame.setSize(800, 600);
            graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Закрытие только этого окна, а не всего приложения
            graphFrame.setContentPane(chartPanel);
            graphFrame.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace(); // Обработка ошибок в случае неудачного запроса
        }
    }
}
