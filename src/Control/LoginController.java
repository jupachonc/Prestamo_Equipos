package Control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAO.UsuarioDAO;
import Entidad.Usuario;
import javafx.scene.control.Alert;

public class LoginController implements Initializable {

    private UsuarioDAO dao = new UsuarioDAO();
    private static Usuario usuario = new Usuario();
    private ValidarLogin JC = new ValidarLogin();

    public static Usuario getUsuario() {
        return usuario;
    }

    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) loginbtn.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Login Button
    @FXML
    private void Login(ActionEvent event) {
        usuario = dao.leer(user.getText(), password.getText());
        if (usuario == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifica la información");
            alert.setContentText(null);
            alert.showAndWait();
        } else {
            switch (usuario.getType()) {
                case 0:
                    ToPath("/Frontera/User/UserMenuUX.fxml");
                    break;
                case 1:
                    ToPath("/Frontera/Admin/AdminMenuUX.fxml");
                    break;
                case 2:
                    ToPath("/Frontera/SU/SUMenuUX.fxml");
                    break;

            }

        }
    }

    //Register Label Click
    @FXML
    private void ToRegisterLink(ActionEvent event) {
        ToPath("/Frontera/RegisterUX.fxml");
    }

}
