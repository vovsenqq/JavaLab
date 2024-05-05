package view;

import javax.swing.*;

import controller.DataForGraphController;
import controller.MainController;
import controller.RegionsController;
import controller.SelectedObjectController;
import controller.SelectedRegionController;
import controller.TypesHandler;
import model.ApiClient;
import model.ObjectPointerModel;
import model.RegionsModel;
import model.SelectedObjectModel;
import model.SelectedRegionModel;
import model.ServerResponseModel;
import model.TypesForRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.List;
import view.MultiLineComboBoxRenderer;

public class MainFrame extends JFrame {
    private JLabel imageLabel;
    private JButton searchButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton upButton;
    private JButton downButton;
    private JComboBox<String> selectBox;
    private JComboBox<String> selectBox2;
    private JLabel textLabel;

    // Метод для получения selectBox
    public JComboBox<String> getSelectBox() {
        return selectBox;
    }

    // Метод для получения selectBox2
    public JComboBox<String> getSelectBox2() {
        return selectBox2;
    }

    // Метод для обновления текста в JLabel
    public void updateTextLabel(String newText) {
        textLabel.setText(newText);
    }

    public void updateImage(ImageIcon newImage) {
        imageLabel.setIcon(newImage);
    }

    public MainFrame(String title, ApiClient apiClient, TypesForRequest model) {
        super(title);

        // Создание компонентов
        imageLabel = new JLabel();
        searchButton = new JButton("Search");
        // Создание кнопок со стрелочками
        leftButton = new JButton("<");
        rightButton = new JButton(">");
        upButton = new JButton("^");
        downButton = new JButton("v");
        selectBox = new JComboBox<>();
        selectBox2 = new JComboBox<>();

        JButton graphButton = new JButton("Show Graph");
        
        // Установка максимального размера для селектбоксов
        selectBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchButton.getPreferredSize().height));
        selectBox2.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchButton.getPreferredSize().height));

        // Установка менеджера компоновки
        setLayout(new BorderLayout(10, 10)); // Отступы между компонентами

        URL imageUrl = getClass().getClassLoader().getResource("2.png");

        // Загрузка изображения
        ImageIcon imageIcon = new ImageIcon(imageUrl);

        // Масштабирование изображения
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        // Установка изображения на JLabel
        imageLabel.setIcon(imageIcon);

        // Создание панели для кнопок и выпадающих списков
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(selectBox2);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Отступ между компонентами
        panel.add(searchButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Отступ между компонентами
        panel.add(selectBox);
        panel.add(Box.createVerticalGlue());
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Отступ между компонентами
        panel.add(graphButton);
        
        // Создание нового JLabel для текста
        // textLabel = new JLabel("<html>Строка 1<br>Строка 2<br>Строка 3<br>Строка 3<br>Строка 3<br>Строка 3<br>Строка 3<br>Строка 3<br>Строка 3<br>Строка 3<br>Строка 3</html>");
        textLabel = new JLabel("<html><br><br><br><br><br><br></html>");
        // Создание панели для картинки и кнопок
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(leftButton, BorderLayout.WEST);
        imagePanel.add(rightButton, BorderLayout.EAST);
        // imagePanel.add(upButton, BorderLayout.NORTH);
        // imagePanel.add(downButton, BorderLayout.SOUTH);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Добавление панели с картинкой и кнопками в основной макет
        this.add(imagePanel, BorderLayout.WEST);
        this.add(panel, BorderLayout.EAST);
        this.add(textLabel, BorderLayout.SOUTH);

        // // Изменение цвета фона
        // panel.setBackground(Color.GREEN);

        this.setSize(1200, 750); // Установка фиксированного размера окна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Окно теперь неизменяемо
        this.setResizable(false); 
        this.setVisible(true);
    

        // Создание модели и контроллера для регионов
        RegionsModel regionsModel = new RegionsModel();
        RegionsController regionsController = new RegionsController(regionsModel);

        // Загрузка данных о регионах
        try {
            regionsController.loadRegionsFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Заполнение selectBox2 значениями регионов
        List<String> regions = regionsModel.getRegions();
        for (String region : regions) {
            selectBox2.addItem(region);
        }

        // Создание контроллера данных и загрузка данных
        TypesHandler dataController = new TypesHandler(apiClient, model);
        try {
            dataController.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Заполнение selectBox значениями ключей из словаря titleEntityMap
        Map<String, String> titleEntityMap = model.getTitleEntityMap();
        for (String title2 : titleEntityMap.keySet()) {
            System.out.println(title2);
            selectBox.addItem(title2);
        }
        
        selectBox.setRenderer(new MultiLineComboBoxRenderer());

        // Создание JLabel для отображения полного текста выбранного элемента
        JLabel selectedItemLabel = new JLabel();

        // Создание моделей и контроллеров для выбранных регионов и объектов
        SelectedRegionModel selectedRegionModel = new SelectedRegionModel();
        SelectedObjectModel selectedObjectModel = new SelectedObjectModel();
        SelectedRegionController selectedRegionController = new SelectedRegionController(this, selectedRegionModel);
        SelectedObjectController selectedObjectController = new SelectedObjectController(this, selectedObjectModel, model);
        ServerResponseModel serverResponseModel = new ServerResponseModel();
        ObjectPointerModel objectPointerModel = new ObjectPointerModel();
        // RegionsModel regionsModel2 = new RegionsModel();
        MainController mainController = new MainController(this, apiClient, selectedRegionModel, selectedObjectModel, serverResponseModel, objectPointerModel, regionsModel);
        // Добавление слушателя к JComboBox
        selectBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Получение выбранного элемента
                String selectedItem = (String) selectBox.getSelectedItem();
                
                // Установка текста JLabel в выбранный элемент
                selectedItemLabel.setText(selectedItem);
                selectedObjectController.updateSelectedObject();
                // System.out.println(selectedObjectModel.getSelectedObject());
            }
        });

        // // Добавление JLabel на панель
        // constraints.gridx = 0;
        // constraints.gridy = 4;
        // constraints.insets = new Insets(10, 10, 10, 10); // Отступы: 10 пикселей сверху, слева, снизу и справа
        // add(selectedItemLabel, constraints);

        // DataForGraphController dataForGraphController = new DataForGraphController(regionsModel, selectedRegionModel, selectedObjectModel, apiClient);
        // graphButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         // Вызов метода actionPerformed контроллера
        //         dataForGraphController.actionPerformed(e);
      
        //         // Создание нового окна
        //         JFrame graphFrame = new JFrame("Graph");
        //         graphFrame.setSize(600, 400);
        //         graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Закрытие только этого окна, а не всего приложения
      
        //         // Здесь вы можете добавить код для создания и добавления графика
      
        //         graphFrame.setVisible(true);
        //     }
        // });

        DataForGraphController dataForGraphController = new DataForGraphController(regionsModel, selectedRegionModel, selectedObjectModel, apiClient);
        graphButton.addActionListener(dataForGraphController);

        
        // Добавление слушателей к selectBox и selectBox2
        selectBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRegionController.updateSelectedRegion();
            }
        });

        // selectBox.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         selectedObjectController.updateSelectedObject();
        //         System.out.println(selectedObjectModel.getSelectedObject());
        //     }
        // });
            
    }

    // Метод для установки слушателя событий на кнопку поиска
    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addLeftButtonListener(ActionListener listener) {
        leftButton.addActionListener(listener);
    }

    public void addRightButtonListener(ActionListener listener) {
        rightButton.addActionListener(listener);
    }

    public void addUpButtonListener(ActionListener listener) {
        upButton.addActionListener(listener);
    }

    public void addDownButtonListener(ActionListener listener) {
        downButton.addActionListener(listener);
    }

}


