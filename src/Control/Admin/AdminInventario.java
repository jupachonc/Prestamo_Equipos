/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;
import Control.LoginController;
import DAO.DBConnection;
import Entidad.Laboratorio;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
                oblist.add(new Laboratorio(rs.getInt("ID"),rs.getString("Nombre"), rs.getString("Telefono"), rs.getString("Ubicacion")));
            }
            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        } catch (SQLException ex) {
            Logger.getLogger(AdminInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(oblist);
    }
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
    public void goToCategorias(){
        ToPath("/Frontera/Admin/AdminCategorias.fxml");
    
    
    }
    public void goToInventary(){
        System.out.println("ir a inventario");
    
    
    }
    @FXML
    private JFXButton backbtn;

    @FXML
    private TableView<Laboratorio> table;
    @FXML
    private TableColumn<Laboratorio, String> col_ID;
    @FXML
    private TableColumn<Laboratorio, String> col_Nombre;
    
    ObservableList<Laboratorio> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        System.out.println("AdminInventario => " + this.user.getDocumento());
        loadTable();
   
        /*
        */
    }
    
}