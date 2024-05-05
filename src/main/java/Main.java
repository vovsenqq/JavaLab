import model.ApiClient;
import model.SelectedObjectModel;
import model.SelectedRegionModel;
import model.TypesForRequest;
import view.MainFrame;
import controller.MainController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApiClient apiClient = new ApiClient();
        TypesForRequest model = new TypesForRequest();
        SelectedRegionModel selectedRegionModel = new SelectedRegionModel();
        SelectedObjectModel selectedObjectModel = new SelectedObjectModel();
        MainFrame view = new MainFrame("super mega app", apiClient, model);

        // MainController mainController = new MainController(view, apiClient, selectedRegionModel, selectedObjectModel);

    }
}
