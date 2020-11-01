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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import DAO.UsuarioDAO;
import Control.ValidarLogin;
import Entidad.Usuario;

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
    @FXML
    private Hyperlink registerlink;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    private void ToPath(String path){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) loginbtn.getScene().getWindow();
            stage1.close();

        } catch(Exception e) {
        e.printStackTrace();
        }
    }
    //Login Button
    @FXML
    private void Login(ActionEvent event) {
        
        if(user.getText().equals("admin")){
            ToPath("/Frontera/AdminMenuUX.fxml");
        }else if (dao.leer(user.getText() , password.getText())) {
            ToPath("/Frontera/UserMenuUX.fxml");
        } 
    }
    
    //Register Label Click
    @FXML
    private void ToRegisterLink(ActionEvent event) {
        ToPath("/Frontera/RegisterUX.fxml");
    }
    
}
