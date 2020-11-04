/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import Control.ValidarLogin;
import Entidad.Usuario;
import javafx.scene.control.Alert;

public class LoginController implements Initializable {

    private UsuarioDAO dao = new UsuarioDAO();
    private Usuario VL = new Usuario();
    private ValidarLogin JC = new ValidarLogin();

    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void ToPath(String path, Usuario user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            if (user != null) {
                switch (user.getType()) {
                    case 0:
                        UserMenuController controlerusr = fxmlLoader.getController();
                        controlerusr.updateUser(user);
                        break;
                    case 1:
                        AdminMenuController controleradm = fxmlLoader.getController();
                        controleradm.updateUser(user);
                        break;
                    default:
                        break;
                }
            }
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
        Usuario usuario = dao.leer(user.getText(), password.getText());
        if (usuario == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifica la información");
            alert.setContentText(null);
            alert.showAndWait();
        } else {
            switch (usuario.getType()) {
                case 0:
                    ToPath("/Frontera/UserMenuUX.fxml", usuario);
                    break;
                case 1:
                    ToPath("/Frontera/AdminMenuUX.fxml", usuario);
                    break;
                case 2:
                    ToPath("/Frontera/SUMenuUX.fxml", usuario);
                    break;

            }

        }
    }

    //Register Label Click
    @FXML
    private void ToRegisterLink(ActionEvent event) {
        ToPath("/Frontera/RegisterUX.fxml", null);
    }

}
