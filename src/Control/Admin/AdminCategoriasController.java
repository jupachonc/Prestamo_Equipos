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

import Control.Admin.AdminInventario; 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Control.LoginController;
import DAO.CategoriasDAO;
import DAO.DAOMacroCategorias;
import DAO.DBConnection;
import Entidad.Categoria;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.control.TreeItem;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author nguzman
 */
public class AdminCategoriasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private MacroCategoria mc;
    private Categoria selectedCategoria = null;
    @FXML
    private JFXButton backbtn;
    @FXML
    private JFXTreeTableView<Categoria> CateTable;
    @FXML
    private TreeTableColumn<Categoria, Integer> col_ID;
    @FXML
    private TreeTableColumn<Categoria, String> col_Nombre;
    @FXML
    private TreeTableColumn<Categoria, String> col_Descripcion;
    @FXML
    private TreeTableColumn<Categoria, String> col_CantMax;
    @FXML
    private Label lblAccion;
    @FXML
    private JFXButton btnGoto;
    @FXML
    private JFXButton btnAccion;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField description;
    @FXML
    private JFXTextField MaxElements;
    
    private String accion = "crear";
    private ObservableList<Categoria> oblist = FXCollections.observableArrayList();
    
    public void accionCategory(){
        if(accion == "crear"){
            
            selectedCategoria=new Categoria(0,Integer.parseInt(MaxElements.getText()),Integer.parseInt(MaxElements.getText()),name.getText(),description.getText());
            selectedCategoria.setMacroCategoriaID(mc.getID());
            new CategoriasDAO().create(selectedCategoria);
            name.setText("");
            description.setText("");
        }
        else if (accion == "guardar"){
            selectedCategoria.setNombre(name.getText());
            selectedCategoria.setDescripción(description.getText());
            new CategoriasDAO().update(selectedCategoria);
                       
        }
        emptyTable();
        loadTable();
    }
    public void emptyTable(){
        System.out.println(oblist.size());
        while(oblist.size()>0) oblist.remove(0);
    }
    public void loadTable(){
        // System.out.println("AdminInventario => setUser => " + this.user.getDocumento());
        Connection con;
        try {
            con = DBConnection.getConnection();
            String sql="SELECT * " + 
                "FROM categoria " + 
                "WHERE MacroCategoriaID = '" + mc.getID() + "'";
            System.out.println(sql);
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                oblist.add(new Categoria(rs.getInt("ID"),rs.getInt("CantidadMax"), rs.getInt("CantidadMax"), rs.getString("Nombre"),rs.getString("Descripción")));
            }
            //col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            //col_Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        } catch (SQLException ex) {
            Logger.getLogger(AdminInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFXTreeTableColumn<Categoria, String> settingsColumn = new JFXTreeTableColumn<>("Eliminar");
        settingsColumn.setPrefWidth(95);
        Callback<TreeTableColumn<Categoria, String>, TreeTableCell<Categoria, String>> cellFactory
                = //
                (final TreeTableColumn<Categoria, String> param) -> {
                    final TreeTableCell<Categoria, String> cell = new TreeTableCell<Categoria, String>() {

                final JFXButton btn = new JFXButton("Eliminar");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setButtonType(JFXButton.ButtonType.FLAT);
                        btn.setStyle("-fx-background-color:  #f44336; -fx-text-fill: #ffffff;");
                        btn.setOnAction(event -> {

                            Categoria cat = this.getTreeTableRow().getItem();

                            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION);
                            alertm.setHeaderText(null);
                            alertm.setTitle("Confirmación");
                            alertm.setContentText("Se eliminará " + cat.getNombre());
                            Optional<ButtonType> action = alertm.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {

                                if (new CategoriasDAO().delete(cat)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("Categoría eliminada");
                                    alert.setContentText(null);
                                    alert.showAndWait();
                                    emptyTable();
                                    loadTable();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("No se pudo eliminar la Categoría.");
                                    alert.setContentText(null);
                                    alert.showAndWait();
                                }
                            }

                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
                    return cell;
                };
        settingsColumn.setCellFactory(cellFactory);

        CateTable.getColumns().set(4, settingsColumn);


        col_ID.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        col_Nombre.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );
        col_Descripcion.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Descripción")
        );
        col_CantMax.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("CantidadMax")
        );
        
        TreeItem<Categoria> root = new RecursiveTreeItem<>(oblist, RecursiveTreeObject::getChildren);
        
        System.out.println(oblist);
        CateTable.setRoot(root);
        CateTable.setShowRoot(false);
    
        CateTable.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
          System.out.println(o);
          System.out.println(oldVal);
          System.out.println(newVal);

            if (newVal != null && newVal.getValue() != null) {
                boolean itemWasSelected = true;
                lblAccion.setText("Editar Macrocategoría");
                //btnGoto.setVisible(true);
                btnAccion.setText("Guardar");
                selectedCategoria = newVal.getValue();
                name.setText(selectedCategoria.getNombre());
                accion = "guardar";
                //name = MacroTable.getSelectionModel().getSelectedItem().getValue();
                description.setText(selectedCategoria.getDescripción());
                MaxElements.setText(String.valueOf(selectedCategoria.getCantidadMax()));
            }
            else{
                lblAccion.setText("Crear Categoría");
                btnGoto.setVisible(false);
                btnAccion.setText("Crear Categoría");
                name.setText("");
                description.setText("");
                accion = "crear";
                selectedCategoria=null;
            }
        });
    }   

    

        
        
        
        
        
        
    public void setMacroCategoria(MacroCategoria mc) {
            this.mc = mc;
            loadTable();
    }
    
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    @FXML
    private void BackToMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/Admin/AdminInventarioUX.fxml"));
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
    
}