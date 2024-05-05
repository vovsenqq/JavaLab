// Контроллер для выбранного объекта
package controller;

import model.SelectedObjectModel;
import model.TypesForRequest;
import view.MainFrame;

public class SelectedObjectController {
    private MainFrame view;
    private SelectedObjectModel model;
    private TypesForRequest typesForRequest;

    public SelectedObjectController(MainFrame view, SelectedObjectModel model, TypesForRequest typesForRequest) {
        this.view = view;
        this.model = model;
        this.typesForRequest = typesForRequest;
    }

    public void updateSelectedObject() {
        String selectedKey = (String) view.getSelectBox().getSelectedItem();
        String selectedObject = typesForRequest.getTitleEntityMap().get(selectedKey);
        model.setSelectedObject(selectedObject);
        // System.out.println(model.getSelectedObject());
    }
}