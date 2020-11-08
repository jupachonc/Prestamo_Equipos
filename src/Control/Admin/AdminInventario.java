/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;
import Control.LoginController;
import DAO.DBConnection;
import Entidad.Usuario;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author nguzman
 */
public class AdminInventario implements Initializable{
   
    private Usuario user = LoginController.getUsuario();

    public void loadTable(){
        // System.out.println("AdminInventario => setUser => " + this.user.getDocumento());
        Connection con;
        try {
            con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(
                "SELECT lab.* " + 
                "FROM laboratorio_administrador la " +
                "LEFT JOIN laboratorio lab ON la.IDLaboratorio = lab.ID " + 
                "WHERE la.IDAdministrador = '" + this.user.getDocumento() + "'"
            );
            while (rs.next()){
                oblist.add(new ModelTableLabs(rs.getString("ID"),rs.getString("Nombre")));
            }
            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        } catch (SQLException ex) {
            Logger.getLogger(AdminInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(oblist);
    }

    @FXML
    private TableView<ModelTableLabs> table;
    @FXML
    private TableColumn<ModelTableLabs, String> col_ID;
    @FXML
    private TableColumn<ModelTableLabs, String> col_Nombre;
    
    ObservableList<ModelTableLabs> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        System.out.println("AdminInventario => " + this.user.getDocumento());
        loadTable();
        /*
        */
    }
    
}
