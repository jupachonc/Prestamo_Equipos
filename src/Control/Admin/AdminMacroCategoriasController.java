/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

/**
 *
 * @author nguzman
 */
import Entidad.Laboratorio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nguzman
 */
public class AdminMacroCategoriasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Laboratorio lab = null;
    
    public void createCategory(){
    
    } 
    
    public void setLaboratorio(Laboratorio lab){
        this.lab=lab;
        System.out.println(this.lab.getNombre());
        
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
