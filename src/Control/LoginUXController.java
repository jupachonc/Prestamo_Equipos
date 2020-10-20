/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;


public class LoginUXController implements Initializable {

    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    //Login Button
    @FXML
    private void Login(ActionEvent event) {
        System.out.println(user.getText());
        System.out.println(password.getText());
    }

    //Register Label Click
    @FXML
    private void ToRegister(MouseEvent event) {
        System.out.println("To Register");
    }
    
}
