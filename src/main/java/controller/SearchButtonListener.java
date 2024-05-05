package controller;

import model.ApiClient;
import model.ObjectPointerModel;
import model.RegionsModel;
import model.SelectedObjectModel;
import model.SelectedRegionModel;
import view.MainFrame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.ServerResponseModel;
import org.json.JSONArray;
import org.json.JSONObject;


public class SearchButtonListener implements ActionListener {
    private MainFrame view;
    private ApiClient apiClient;
    private SelectedRegionModel selectedRegionModel; // Объявление экземпляра модели региона
    private SelectedObjectModel selectedObjectModel; // Объявление экземпляра модели объекта
    private ServerResponseModel serverResponseModel; // Объявление экземпляра модели ответа сервера
    private ObjectPointerModel objectPointerModel;
    private RegionsModel regionsModel;

    public SearchButtonListener(MainFrame view, ApiClient apiClient, SelectedRegionModel selectedRegionModel, 
    SelectedObjectModel selectedObjectModel, ServerResponseModel serverResponseModel, ObjectPointerModel objectPointerModel, RegionsModel regionsModel) {
        this.view = view;
        this.apiClient = apiClient;
        this.selectedRegionModel = selectedRegionModel; // Инициализация модели региона
        this.selectedObjectModel = selectedObjectModel; // Инициализация модели объекта
        this.serverResponseModel = serverResponseModel; // Инициализация модели ответа сервера
        this.objectPointerModel = objectPointerModel;
        this.regionsModel = regionsModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String apiKey = apiClient.getApiKey();
            // System.out.println(apiKey);
            
            // Получение выбранных элементов из selectBox и selectBox2
            String selectedObj = selectedObjectModel.getSelectedObject();
            String selectedRegion = selectedRegionModel.getSelectedRegion();

            // Проверка, что selectedObj и selectedRegion не равны null
            if (selectedObj == null || selectedRegion == null) {
                System.out.println("Выбранный объект или регион не заданы");
                return;
            }
            serverResponseModel.clearData();
            // System.out.println(selectedRegion);
            // System.out.println(selectedObj);

            // Замена пробелов на %20 в selectedCity для корректного формирования URL
            // selectedRegion = selectedRegion.replace(" ", "%20");

            // Получение списка городов для выбранного региона
            List<String> cities = regionsModel.getCitiesInRegion(selectedRegion);
            System.out.println(selectedRegion);
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

                // Сохранение данных в массив JSON объектов
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject dataObject = jsonArray.getJSONObject(i);
                    serverResponseModel.addData(dataObject); // Добавление данных в модель ответа сервера
                }
            }
            // Вывод ответа с сервера в консоль
            System.out.println("Ответ с сервера: " + serverResponseModel.getData());
        objectPointerModel.setCounter(0);

        // Получаем объект JSON по текущему индексу
        JSONObject jsonObject2 = serverResponseModel.getData().get(objectPointerModel.getCounter());
        // Выводим ссылку на изображение в консоль
        JSONObject image = jsonObject2.getJSONObject("data").getJSONObject("general").getJSONObject("image");
        String imageUrl = image.getString("url");
        System.out.println("Image URL: " + imageUrl);

        // Загрузка изображения из интернета
        URL url = new URL(imageUrl);
        BufferedImage bufferedImage = ImageIO.read(url);
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        // Масштабирование изображения
        Image scaledImage = imageIcon.getImage().getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        // Обновление изображения на JLabel
        view.updateImage(imageIcon);
        // Формируем строку с информацией для вывода в JLabel
        String infoString = "<html>";
        // Добавляем название
        infoString += "Название: " + jsonObject2.getString("nativeName") + "<br>";
        // Добавляем адрес
        JSONObject address = jsonObject2.getJSONObject("data").getJSONObject("general").getJSONObject("address");
        JSONObject website = jsonObject2.getJSONObject("data").getJSONObject("general").getJSONObject("contacts");
        String fullAddress = address.getString("fullAddress");
        String websiteurl = website.getString("website");
        infoString += "Адрес: " + fullAddress + "<br>";
        infoString += "Сайт: " + websiteurl + "<br>";
        // Добавляем описание, если оно есть
        if (jsonObject2.getJSONObject("data").getJSONObject("general").has("description")) {
            String description = jsonObject2.getJSONObject("data").getJSONObject("general").getString("description");
            infoString += "Описание: " + description + "<br>";
        }

        infoString += "</html>";
        // Выводим строку с информацией в консоль
        view.updateTextLabel(infoString);
        System.out.println(infoString);

        } catch (IOException ex) {
        ex.printStackTrace(); // Обработка ошибок в случае неудачного запроса
        }
    }
}

