
import controllers.AppController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class App extends Application {
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource("fxml/app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 550);
        stage.setTitle("Neurodegenerative Diagnosis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> handleClose(event));
        stage.show();

        AppController controller = fxmlLoader.getController();
        controller.setHostServices(getHostServices());
    }

    public static void main(String[] args) {
        launch();
    }
    private void handleClose(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to close the application?");
//        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AppController controller = fxmlLoader.getController();
            controller.handleClose();
        } else {
            // Cancelar el cierre de la ventana
            event.consume();
        }
    }
}