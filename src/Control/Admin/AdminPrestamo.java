/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import DAO.UsuarioDAO;
import Entidad.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AdminPrestamo implements Initializable {
    
    private Usuario estudiante = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void searchDocument(ActionEvent event) {
        int Documento = 1000000000;
        estudiante = new UsuarioDAO().getUser(Documento);
        System.out.println(estudiante.getNombre());

    }    
    
}
