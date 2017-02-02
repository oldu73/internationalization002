package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX offers a simple way to translate UI components. In this example, a simple gui implements
 * a language switch. The switch is basically a {@link ComboBox} with two languages to choose. The
 * content-part of the UI is loaded from an fxml file. In this file, the label's text is set to
 * "%label" which means that the text is going to be translated to whatever the set resource
 * bundle provides.
 *
 * SRC: https://stevenschwenke.de/howToI18nInJavaFXAndIntelliJIDEA
 */
public class MainApp extends Application {

    //private Locale toLocale = new Locale("de", "DE");

    private Locale startLocale = new Locale("en");

    BorderPane borderPane = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // SRC: https://www.jmdoudoux.fr/java/dej/chap-i18n.htm
        // L'objet Locale par défaut qui est initialisé par la machine virtuelle avec les paramètres de la machine hôte.
        // La méthode Locale.getDefault() permet de connaître cet objet Locale.
        //System.out.println(Locale.getDefault());

        // SRC: http://stackoverflow.com/questions/12072454/in-java-how-do-i-find-out-what-languages-i-have-available-my-resource-bundle
        //Arrays.stream(Locale.getAvailableLocales()).forEach(System.out::println);

        loadView(startLocale);
        borderPane.setTop(createComboBox());
        stage.setScene(new Scene(borderPane));
        stage.setTitle("Internationalization");
        stage.show();
    }

    private ComboBox<Locale> createComboBox() {
        ComboBox<Locale> comboBox = new ComboBox<>();

        //ObservableList<Locale> options = FXCollections.observableArrayList(Locale.ENGLISH, Locale.GERMAN);
        //ObservableList<Locale> options = FXCollections.observableArrayList(new Locale("en", "GB"), new Locale("de", "DE"), new Locale("en", "US"), new Locale("fr", "CH"));
        //ObservableList<Locale> options = FXCollections.observableArrayList(Locale.getAvailableLocales());

        ObservableList<Locale> options = FXCollections.observableArrayList(Locale.ENGLISH, Locale.FRENCH);

        comboBox.setItems(options);
        comboBox.setConverter(new StringConverter<Locale>() {
            @Override
            public String toString(Locale object) {

                //return object.getDisplayLanguage();
                //return object.getLanguage() + " - " + object.getCountry();
                //return object.getDisplayLanguage(toLocale) + " - " + object.getDisplayCountry(toLocale);

                String objectString  = object.getDisplayLanguage(object);
                objectString = objectString.substring(0,1).toUpperCase() + objectString.substring(1).toLowerCase();

                return objectString;

            }

            @Override
            public Locale fromString(String string) {
                return null;
            }
        });
        comboBox.setCellFactory(p -> new LanguageListCell());
        //comboBox.getSelectionModel().selectFirst();
        comboBox.getSelectionModel().select(startLocale);

        comboBox.setOnAction(event -> loadView(comboBox.getSelectionModel().getSelectedItem()));
        return comboBox;
    }

    private void initView() {

    }

    public void loadView(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();

            // Here, just the resource bundles name is mentioned. You can add support for more languages
            // by adding more properties-files with language-specific endings like
            // "E_13_Internationalization_fr.properties".
            fxmlLoader.setResources(ResourceBundle.getBundle("text", locale));

            Controller controller = fxmlLoader.getController();
            controller.setMainApp(this);

            Pane pane = (BorderPane) fxmlLoader.load(this.getClass().getResource("sample.fxml").openStream());
            borderPane.setCenter(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    class LanguageListCell extends ListCell<Locale> {
        @Override protected void updateItem(Locale item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {

                //setText(item.getDisplayLanguage());
                //setText(item.getLanguage() + " - " + item.getCountry());
                //setText(item.getDisplayLanguage(toLocale) + " - " + item.getDisplayCountry(toLocale));

                // SRC: http://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
                // WordUtils.capitalize("i am FINE") = "I Am FINE" from Apache Commons

                String itemString  = item.getDisplayLanguage(item);
                itemString = itemString.substring(0,1).toUpperCase() + itemString.substring(1).toLowerCase();

                setText(itemString);

            }
        }
    }
}
