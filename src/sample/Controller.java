package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.Locale;

public class Controller {

    private MainApp mainApp;

    private Locale startLocale = new Locale("en");

    @FXML
    private ComboBox<Locale> comboBox;

    @FXML
    private Label label;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

}
