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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class LoginController implements Initializable {
    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginbtn;
    @FXML
    private Label registerlabel;
    @FXML
    private Hyperlink registerlink;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    //Login Button
    @FXML
    private void Login(ActionEvent event) {
        System.out.println(user.getText());
        System.out.println(password.getText());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/UserMenuUX.fxml"));
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

    //Register Label Click
    @FXML
    private void ToRegisterLink(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/RegisterUX.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) registerlink.getScene().getWindow();
            stage1.close();

        } catch(Exception e) {
        e.printStackTrace();
        }
    }
    
}
