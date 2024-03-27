package controller;

import model.TextModel;
import view.MainFrame;

public class MainController {
    private MainFrame view;
    private TextModel model;

    public MainController(MainFrame view, TextModel model) {
        this.view = view;
        this.model = model;
        initView();
        initController();
    }

    private void initView() {
        view.updateLabelText(model.getText());
    }

    private void initController() {
        // В этом примере нет действий контроллера, но обычно здесь добавляются слушатели событий
    }
}