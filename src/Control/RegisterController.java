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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;




public class RegisterController implements Initializable {

    @FXML
    private JFXButton registerbtn;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField confirm_password;
    @FXML
    private Hyperlink loginlink;
    @FXML
    private JFXTextField documento;
    @FXML
    private JFXTextField nombres;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField apellidos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Register(ActionEvent event) {
        String nombre = nombres.getText();
        String apellido = apellidos.getText();
        int intDocumento = Integer.parseInt(documento.getText());
        String strEmail = email.getText();
        String contrasena = password.getText();
        String reContrasena = confirm_password.getText();
        ValidarRegistro validar = new ValidarRegistro();
        String respuesta = validar.verificarRegistro(nombre, apellido, intDocumento, strEmail, contrasena, reContrasena);
        System.out.println(respuesta);
        if(respuesta.equals("Usuario registrado")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(respuesta);
            alert.setContentText(null);
            alert.showAndWait();
            cleanForm();
        
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifica la información");
            alert.setContentText(respuesta);
            alert.showAndWait();
        }
    }
    
    private void cleanForm(){
        nombres.setText("");
        apellidos.setText("");
        documento.setText("");
        email.setText("");
        password.setText("");
        confirm_password.setText(""); 
    }

    @FXML
    private void ToLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/LoginUX.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) loginlink.getScene().getWindow();
            stage1.close();

        } catch(Exception e) {
        e.printStackTrace();
        }
    }
    


  
}
