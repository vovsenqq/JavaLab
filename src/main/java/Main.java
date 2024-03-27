import model.TextModel;
import view.MainFrame;
import controller.MainController;

public class Main {
    public static void main(String[] args) {
        TextModel model = new TextModel();
        MainFrame view = new MainFrame("Vladimir Senyukov senior java dev");

        MainController controller = new MainController(view, model);
    }
}