/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;
import Control.LoginController;
import DAO.DAOMacroCategorias;
import DAO.DBConnection;
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
 *
 * @author nguzman
 */
public class AdminInventario implements Initializable{
   
    private Usuario user = LoginController.getUsuario();
    private Laboratorio lab;
    
    private MacroCategoria selectedMacroCategoria = null;
            
    @FXML
    private JFXButton backbtn;
    @FXML
    private JFXTreeTableView<MacroCategoria> MacroTable;
    @FXML
    private TreeTableColumn<MacroCategoria, Integer> col_ID;
    @FXML
    private TreeTableColumn<MacroCategoria, String> col_Nombre;
    @FXML
    private TreeTableColumn<MacroCategoria, String> col_Descripcion;
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
    
    private String accion = "crear";
    private ObservableList<MacroCategoria> oblist = FXCollections.observableArrayList();
    
    public void accionCategory(){
        if(accion == "crear"){
            selectedMacroCategoria=new MacroCategoria(0,name.getText(),description.getText(), lab.getID() );
            String result=selectedMacroCategoria.validar();
            if(result=="OK"){
                new DAOMacroCategorias().create(selectedMacroCategoria);
                alerta("MacroCategoría creada correctamente");
                name.setText("");
                description.setText("");
                emptyTable();
                loadTable();
            }
            else{
                alerta(result);
            }
        }
            
        else if (accion == "guardar"){
            selectedMacroCategoria.setNombre(name.getText());
            selectedMacroCategoria.setDescripción(description.getText());
            String result2=selectedMacroCategoria.validar();
            if(result2=="OK"){
                new DAOMacroCategorias().update(selectedMacroCategoria);
                alerta("MacroCategoría editada correctamente");
                name.setText("");
                description.setText("");
                emptyTable();
                loadTable();
            }
            else{
                alerta(result2);
            }
        }
        
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
                "FROM macrocategoria " + 
                "WHERE LaboratorioID = '" + this.lab.getID() + "'";
            System.out.println(sql);
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                oblist.add(new MacroCategoria(rs.getInt("ID"),rs.getString("Nombre"), rs.getString("Descripción"), rs.getInt("LaboratorioID")));
            }
            //col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            //col_Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        } catch (SQLException ex) {
            Logger.getLogger(AdminInventario.class.getName()).log(Level.SEVERE, null, ex);
        }


        JFXTreeTableColumn<MacroCategoria, String> settingsColumn = new JFXTreeTableColumn<>("Eliminar");
        settingsColumn.setPrefWidth(95);
        Callback<TreeTableColumn<MacroCategoria, String>, TreeTableCell<MacroCategoria, String>> cellFactory
                = //
                (final TreeTableColumn<MacroCategoria, String> param) -> {
                    final TreeTableCell<MacroCategoria, String> cell = new TreeTableCell<MacroCategoria, String>() {

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

                            MacroCategoria macCat = this.getTreeTableRow().getItem();

                            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION);
                            alertm.setHeaderText(null);
                            alertm.setTitle("Confirmación");
                            alertm.setContentText("Se eliminará " + macCat.getNombre());
                            Optional<ButtonType> action = alertm.showAndWait();

                            if (action.get() == ButtonType.OK) {

                                if (new DAOMacroCategorias().delete(macCat)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("Macrocategoría eliminada");
                                    alert.setContentText(null);
                                    alert.showAndWait();
                                    emptyTable();
                                    loadTable();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("No se pudo eliminar la macrocategoría.");
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

        MacroTable.getColumns().set(3, settingsColumn);


        col_ID.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        col_Nombre.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );
        col_Descripcion.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Descripción")
        );
        TreeItem<MacroCategoria> root = new RecursiveTreeItem<>(oblist, RecursiveTreeObject::getChildren);
        
        System.out.println(oblist);
        
        MacroTable.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
          System.out.println(o);
          System.out.println(oldVal);
          System.out.println(newVal);

            if (newVal != null && newVal.getValue() != null) {
                boolean itemWasSelected = true;
                lblAccion.setText("Editar Macrocategoría");
                btnGoto.setVisible(true);
                btnAccion.setText("Guardar");
                selectedMacroCategoria = newVal.getValue();
                name.setText(selectedMacroCategoria.getNombre());
                accion = "guardar";
                //name = MacroTable.getSelectionModel().getSelectedItem().getValue();
                description.setText(selectedMacroCategoria.getDescripción());
            }
            else{
                lblAccion.setText("Crear Macrocategoría");
                btnGoto.setVisible(false);
                btnAccion.setText("Crear Macrocategoría");
                name.setText("");
                description.setText("");
                accion = "crear";
                selectedMacroCategoria=null;
            }
        });
        
        MacroTable.setRoot(root);
        MacroTable.setShowRoot(false);
        
    }
    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            
            AdminCategoriasController ac  = fxmlLoader.getController();
            ac.setMacroCategoria(selectedMacroCategoria);
            
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
    @FXML
    private void goToCategories(ActionEvent event) {
        ToPath("/Frontera/Admin/AdminCategoriasUX.fxml");
    }
    public void goToInventary(){
        System.out.println("ir a inventario");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lab = AdminMenuController.currentLab;
        System.out.println("AdminInventario => " + this.user.getDocumento());
        loadTable();
   
        /*
        */
    }
    @FXML
    private void BackToMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/Admin/AdminMenuUX.fxml"));
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
    
    private void alerta(String text){
        System.out.println("Display error");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(text);
        alert.setContentText(null);
        alert.showAndWait();
    }
    
}
