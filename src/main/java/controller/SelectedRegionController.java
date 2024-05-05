// Контроллер для выбранного региона
package controller;

import model.SelectedRegionModel;
import view.MainFrame;

public class SelectedRegionController {
    private MainFrame view;
    private SelectedRegionModel model;

    public SelectedRegionController(MainFrame view, SelectedRegionModel model) {
        this.view = view;
        this.model = model;
    }

    public void updateSelectedRegion() {
        String selectedRegion = (String) view.getSelectBox2().getSelectedItem();
        model.setSelectedRegion(selectedRegion);
        // System.out.println(model.getSelectedRegion());
    }
}