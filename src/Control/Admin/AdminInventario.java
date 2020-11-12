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
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.control.TreeItem;


import javafx.util.Callback;

/**
 *
 * @author nguzman
 */
public class AdminInventario implements Initializable{
   
    private Usuario user = LoginController.getUsuario();
    @FXML
    private JFXButton backbtn;
    @FXML
    private JFXTreeTableView<Laboratorio> Table;
    @FXML
    private TreeTableColumn<Laboratorio, Integer> col_ID;
    @FXML
    private TreeTableColumn<Laboratorio, String> col_Nombre;
    
    private ObservableList<Laboratorio> oblist = FXCollections.observableArrayList();

    public void loadTable(){
        // System.out.println("AdminInventario => setUser => " + this.user.getDocumento());
        Connection con;
        try {
            con = DBConnection.getConnection();
            String sql="SELECT lab.* " + 
                "FROM laboratorio_administrador la " +
                "LEFT JOIN laboratorio lab ON la.LaboratorioID = lab.ID " + 
                "WHERE la.AdministradorDocumento = '" + this.user.getDocumento() + "'";
            System.out.println(sql);
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                oblist.add(new Laboratorio(rs.getInt("ID"),rs.getString("Nombre"), rs.getString("Telefono"), rs.getString("Ubicacion")));
            }
            //col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            //col_Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        } catch (SQLException ex) {
            Logger.getLogger(AdminInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        //table.setItems(oblist);
        /*
        Callback<TreeTableColumn<Laboratorio, String>, TreeTableCell<Laboratorio, String>> cellFactory
                = //
                (final TreeTableColumn<Laboratorio, String> param) -> {
                    return cell;
                };
        */
        col_ID.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        col_Nombre.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );

        TreeItem<Laboratorio> root = new RecursiveTreeItem<>(oblist, RecursiveTreeObject::getChildren);
        
        System.out.println(oblist);
        
        Table.setRoot(root);
        Table.setShowRoot(false);
        
    }
    private void ToMacroCategorias(String path, Laboratorio lab) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            
            AdminMacroCategoriasController amc  = fxmlLoader.getController();
            amc.setLaboratorio(lab);
            
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
    public void goToMacroCategorias(){
        System.out.println(Table);
        TreeItem ti =  Table.getSelectionModel().selectedItemProperty().get();
        //Si object es nulo saque alerta pidiendo selección
        Laboratorio lab = Table.getSelectionModel().getSelectedItem().getValue();
        
        System.out.println(lab.getID());
        ToMacroCategorias("/Frontera/Admin/AdminMacroCategorias.fxml", lab); 
    }
    public void goToInventary(){
        System.out.println("ir a inventario");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        System.out.println("AdminInventario => " + this.user.getDocumento());
        loadTable();
   
        /*
        */
    }
    
}
