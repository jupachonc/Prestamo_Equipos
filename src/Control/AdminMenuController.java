package Control;

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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AdminMenuController implements Initializable {

    @FXML
    private Label labelname;

    public void updateUser(Usuario user) {
        labelname.setText(MessageFormat.format("Está registrado como {0} {1}", user.getNombre(), user.getApellido()));

    }
    
        private void ToPath(String path){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) logoutbtn.getScene().getWindow();
            stage1.close();

        } catch(Exception e) {
        e.printStackTrace();
        }
    }

    @FXML
    private JFXButton logoutbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Logout(ActionEvent event) {
        ToPath("/Frontera/LoginUX.fxml");
    }

    @FXML
    private void Config(MouseEvent event) {
    }
    
}
