/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.UsuarioDAO;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.apache.commons.codec.digest.DigestUtils;

public class UserConfigController implements Initializable {

    private Usuario user;
    private UsuarioDAO dao = new UsuarioDAO();
    @FXML
    private Label names;
    @FXML
    private Label lastnames;
    @FXML
    private Label documento;
    @FXML
    private JFXPasswordField currentpss;
    @FXML
    private JFXPasswordField newpss;
    @FXML
    private JFXPasswordField cnewpss;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public boolean verificarLongitud(String x, int largo) {
        return (x.length() > 3 && x.length() <= largo);
    }

    public boolean verificarContrasenas(String contrasena, String reContrasena) {
        return contrasena.equals(reContrasena);
    }

    public void updateUser() {
        names.setText(user.getNombre());
        lastnames.setText(user.getApellido());
        documento.setText(user.getDocumento());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void changePss(ActionEvent event) {
        ValidarRegistro validar = new ValidarRegistro();
        if (!user.getContrasena().equals(DigestUtils.sha256Hex(currentpss.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Contraseña Incorrecta");
            alert.setContentText(null);
            alert.showAndWait();
        } else if (!validar.verificarContrasenas(newpss.getText(), cnewpss.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.setContentText(null);
            alert.showAndWait();
        } else if (!validar.verificarLongitud(newpss.getText(), 30)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Longitud de nueva contraseña incorrecta");
            alert.setContentText(null);
            alert.showAndWait();
        } else if (DigestUtils.sha256Hex(newpss.getText()).equals(user.getContrasena())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("La nueva contraseña no puede ser igual a la anterior");
            alert.setContentText(null);
            alert.showAndWait();
        } else {
            if (!dao.changePassword(user, newpss.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo actualizar la contraseña");
                alert.setContentText(null);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("La contraseña fue actualizada");
                alert.setContentText(null);
                alert.showAndWait();
                currentpss.setText("");
                newpss.setText("");
                cnewpss.setText("");
            }
        }

    }

}
