/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import Control.LoginController;
import DAO.LaboratorioDAO;
import Entidad.Laboratorio;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANDRES CAMILO
 */
public class AdminGestorClaseController implements Initializable {
    
    private LaboratorioDAO dao = new LaboratorioDAO();
    private Usuario user = LoginController.getUsuario();
    private Laboratorio lab = AdminMenuController.currentLab;
    
    @FXML
    private JFXButton gestionbtn;

    @FXML
    private RadioButton bloquearbtn;

    @FXML
    private RadioButton permitirbtn;
    
    @FXML
    private JFXButton volver;
    
    @FXML
    private JFXButton logoutbtn;
    
    @FXML
    private Label labelDesc;

    @FXML
    void Gestion(ActionEvent event) {
        if(this.bloquearbtn.isSelected()){
            
        dao.disableLab(lab);
        
        }else if(this.permitirbtn.isSelected()){
        dao.enableLab(lab);
        
        }else{
        
        this.labelDesc.setText("No se ha seleccionado alguna opción");
        
        }

    }
    
    
    @FXML
    void goToMenu(ActionEvent event) {
        
    ToPath("/Frontera/Admin/AdminMenuUX.fxml");

    }
    
    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            stage.show();
            Stage stage1 = (Stage) volver.getScene().getWindow();
            stage1.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void desBloq(ActionEvent event) {

        this.labelDesc.setText("Al aplicar esto, no \n se podrán realizar prestamos \n en el laboratorio");
        
        
    }
    
    @FXML
    void desPermitir(ActionEvent event) {
        
        this.labelDesc.setText("Al aplicar esto, se \n habilitarán los prestamos");
    }
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup tg = new ToggleGroup();
        this.bloquearbtn.setToggleGroup(tg);
        this.permitirbtn.setToggleGroup(tg);
    }    
    
    
    
    
}

