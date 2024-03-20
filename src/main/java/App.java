
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 550);
        stage.setTitle("Neurodegenerative Diagnosis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        //hola soy maria vivas
    }

    public static void main(String[] args) {
        launch();
    }
}