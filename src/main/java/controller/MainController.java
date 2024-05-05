package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.json.JSONObject;

import model.ApiClient;
import model.ObjectPointerModel;
import model.RegionsModel;
import model.SelectedObjectModel;
import model.SelectedRegionModel;
import model.ServerResponseModel;
import view.MainFrame;

public class MainController {
    private MainFrame view;
    private ApiClient apiClient;
    private SelectedRegionModel selectedRegionModel;
    private SelectedObjectModel selectedObjectModel;
    private ServerResponseModel serverResponseModel;
    private ObjectPointerModel objectPointerModel;
    private RegionsModel regionsModel;

    public MainController(MainFrame view, ApiClient apiClient, SelectedRegionModel selectedRegionModel, 
                          SelectedObjectModel selectedObjectModel, ServerResponseModel serverResponseModel, ObjectPointerModel objectPointerModel, RegionsModel regionsModel) {
        this.view = view;
        this.apiClient = apiClient;
        this.selectedRegionModel = selectedRegionModel;
        this.selectedObjectModel = selectedObjectModel;
        this.serverResponseModel = serverResponseModel;
        this.objectPointerModel = objectPointerModel;
        this.regionsModel = regionsModel;
        
        initController();
    }

    private void initController() {
        // Добавляем слушателя событий на кнопку поиска
        view.addSearchButtonListener(new SearchButtonListener(view, apiClient, selectedRegionModel, selectedObjectModel, serverResponseModel, objectPointerModel, regionsModel));
        // view.addSearchButtonListener(new DataForGraphController(regionsModel, selectedRegionModel, selectedObjectModel, apiClient));
    
        // Добавляем слушателя событий на левую кнопку
    view.addLeftButtonListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Уменьшаем индекс в модели ObjectPointerModel
            if (objectPointerModel.getCounter() - 1 >= 0) {
                objectPointerModel.setCounter(objectPointerModel.getCounter() - 1);
            }
            // Получаем объект JSON по текущему индексу
            JSONObject jsonObject = serverResponseModel.getData().get(objectPointerModel.getCounter());
            // Выводим ссылку на изображение в консоль
            JSONObject image = jsonObject.getJSONObject("data").getJSONObject("general").getJSONObject("image");
            String imageUrl = image.getString("url");
            System.out.println("Image URL: " + imageUrl);

            // Загрузка изображения из интернета
            try {
                URL url = new URL(imageUrl);
                BufferedImage bufferedImage = ImageIO.read(url);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                // Масштабирование изображения
                Image scaledImage = imageIcon.getImage().getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
                // Установка изображения на JLabel
                view.updateImage(imageIcon);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // Формируем строку с информацией для вывода в JLabel
            String infoString = "<html>";
            // Добавляем название
            infoString += "Название: " + jsonObject.getString("nativeName") + "<br>";
            // Добавляем адрес
            JSONObject address = jsonObject.getJSONObject("data").getJSONObject("general").getJSONObject("address");
            JSONObject website = jsonObject.getJSONObject("data").getJSONObject("general").getJSONObject("contacts");
            String fullAddress = address.getString("fullAddress");
            String websiteurl = website.getString("website");
            infoString += "Адрес: " + fullAddress + "<br>";
            infoString += "Сайт: " + websiteurl + "<br>";
            // Добавляем полный адрес
            // String street = address.getString("street");
            // String fiasRegionId = address.getString("fiasRegionId");
            // String fiasCityId = address.getString("fiasCityId");
            // String fiasStreetId = address.getString("fiasStreetId");
            // String fullAddresss = "обл " + fiasRegionId + ",г " + fiasCityId + ",ул " + street;
            // infoString += "Полный адрес: " + fullAddress + "<br>";
            // Добавляем описание, если оно есть
            if (jsonObject.getJSONObject("data").getJSONObject("general").has("description")) {
                String description = jsonObject.getJSONObject("data").getJSONObject("general").getString("description");
                infoString += "Описание: " + description + "<br>";
            }
            
            infoString += "</html>";
            // Выводим строку с информацией в консоль
            view.updateTextLabel(infoString);
            System.out.println(infoString);
        }
    });

    // Добавляем слушателя событий на правую кнопку
    view.addRightButtonListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Увеличиваем индекс в модели ObjectPointerModel
            if (objectPointerModel.getCounter() + 1 < serverResponseModel.getSize()) {
                objectPointerModel.setCounter(objectPointerModel.getCounter() + 1);
            }
            // Получаем объект JSON по текущему индексу
            JSONObject jsonObject = serverResponseModel.getData().get(objectPointerModel.getCounter());
            // Выводим ссылку на изображение в консоль
            JSONObject image = jsonObject.getJSONObject("data").getJSONObject("general").getJSONObject("image");
            String imageUrl = image.getString("url");
            System.out.println("Image URL: " + imageUrl);

            // Загрузка изображения из интернета
            try {
                URL url = new URL(imageUrl);
                BufferedImage bufferedImage = ImageIO.read(url);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                // Масштабирование изображения
                Image scaledImage = imageIcon.getImage().getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
                // Установка изображения на JLabel
                view.updateImage(imageIcon);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // Формируем строку с информацией для вывода в JLabel
            String infoString = "<html>";
            // Добавляем название
            infoString += "Название: " + jsonObject.getString("nativeName") + "<br>";
            // Добавляем адрес
            JSONObject address = jsonObject.getJSONObject("data").getJSONObject("general").getJSONObject("address");
            JSONObject website = jsonObject.getJSONObject("data").getJSONObject("general").getJSONObject("contacts");
            String fullAddress = address.getString("fullAddress");
            String websiteurl = website.getString("website");
            infoString += "Адрес: " + fullAddress + "<br>";
            infoString += "Сайт: " + websiteurl + "<br>";
            // Добавляем полный адрес
            // String street = address.getString("street");
            // String fiasRegionId = address.getString("fiasRegionId");
            // String fiasCityId = address.getString("fiasCityId");
            // String fiasStreetId = address.getString("fiasStreetId");
            // String fullAddresss = "обл " + fiasRegionId + ",г " + fiasCityId + ",ул " + street;
            // infoString += "Полный адрес: " + fullAddress + "<br>";
            // Добавляем описание, если оно есть
            if (jsonObject.getJSONObject("data").getJSONObject("general").has("description")) {
                String description = jsonObject.getJSONObject("data").getJSONObject("general").getString("description");
                infoString += "Описание: " + description + "<br>";
            }
            
            infoString += "</html>";
            // Выводим строку с информацией в консоль
            System.out.println(infoString);
            view.updateTextLabel(infoString);
        }
    });


    }
    
}






