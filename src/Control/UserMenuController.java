/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class UserMenuController implements Initializable {

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
                try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/LoginUX.fxml"));
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
    private void Config(MouseEvent event) {
        System.out.println("Clicked");
    }
    
}
