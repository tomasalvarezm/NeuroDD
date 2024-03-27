package app;

import controllers.AppController;
import javafx.application.Application;
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
        fxmlLoader = new FXMLLoader(app.App.class.getResource("/fxml/app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 550);
        stage.setTitle("Neurodegenerative Diagnosis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private void handleClose(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cierre");
        alert.setHeaderText("¿Estás seguro de que quieres cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Obtener el controlador de la ventana
            AppController controller = fxmlLoader.getController();
            // Llamar al método handleClose del controlador
            controller.handleClose(event);
        } else {
            // Cancelar el cierre de la ventana
            event.consume();
        }
    }
}