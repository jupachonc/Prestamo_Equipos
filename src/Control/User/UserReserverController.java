package Control.User;

import Control.LoginController;
import Control.UserConfigController;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserReserverController {
    private Usuario user = LoginController.getUsuario();
    
    @FXML
    private JFXButton backToMenu1;
    
    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) backToMenu1.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void BackToMenu(ActionEvent event) {
        ToPath("/Frontera/User/UserMenuUX.fxml");
    }
}
