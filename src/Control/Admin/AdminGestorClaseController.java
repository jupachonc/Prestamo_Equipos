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
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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
    void Gestión(ActionEvent event) {
        if(this.bloquearbtn.isSelected()){
            
       dao.disableLab(lab);
        
        }else if(this.permitirbtn.isSelected()){
       dao.enableLab(lab);
        
        }else{
        
        
        
        }

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
