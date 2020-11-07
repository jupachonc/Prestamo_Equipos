package Control.SU;

import Control.LoginController;
import Control.ValidarRegistro;
import DAO.UsuarioDAO;
import Entidad.Usuario;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MAdminsController implements Initializable {

    private Usuario user = LoginController.getUsuario();


    @FXML
    private JFXTextField names;
    @FXML
    private JFXTextField lastnames;
    @FXML
    private JFXTextField document;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField cpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Usuario> admins = new UsuarioDAO().getAdmins();
    }

    @FXML
    private void backbtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/SU/SUMenuUX.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            SUMenuController controlersu = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) names.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void create(ActionEvent event) {
        String respuesta = new ValidarRegistro().verificarRegistro(names.getText(),
                lastnames.getText(), Integer.parseInt(document.getText()), email.getText(),
                password.getText(), cpassword.getText(), 1);
        if (respuesta.equals("Usuario registrado") || respuesta.equals("Usuario reactivado con la contraseña asignada")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(respuesta);
            alert.setContentText(null);
            alert.showAndWait();
            cleanForm();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifica la información");
            alert.setContentText(respuesta);
            alert.showAndWait();
        }

    }

    private void cleanForm() {
        names.setText("");
        lastnames.setText("");
        document.setText("");
        email.setText("");
        password.setText("");
        cpassword.setText("");
    }

}
