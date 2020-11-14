/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;
import Entidad.Laboratorio;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author nguzman
 */
public class AdminMacroCat_Elemento implements Initializable{
    @FXML
    private JFXButton backbtn;
    private Laboratorio lab = null;
    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) backbtn.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void goToMacroCategorias(ActionEvent event){
        System.out.println("goToMacro");
        ToPath("/Frontera/Admin/AdminInventarioUX.fxml");
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
