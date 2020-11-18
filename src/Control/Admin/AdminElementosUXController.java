/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import DAO.ElementoDAO;
import DAO.LaboratorioDAO;
import Entidad.Categoria;
import Entidad.Elemento;
import Entidad.MacroCategoria;
import Entidad.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ANDRES CAMILO
 */
public class AdminElementosUXController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Categoria cat; 
    private Elemento selectedElement = null;
    
    private ObservableList<Elemento> oblist = FXCollections.observableArrayList();
    
    //col_ID.setCellValueFactory(
      //          new TreeItemPropertyValueFactory<>("ID")
       // );
    
    
    
   private ElementoDAO dao = new ElementoDAO();
    
    @FXML
    private TreeTableView<Elemento> elementsTable;

    @FXML
    private TreeTableColumn<Elemento, Integer> colId;

    @FXML
    private TreeTableColumn<Elemento, String> col_Nombre;

    @FXML
    private TreeTableColumn<Elemento, Elemento> col_Descrip;

    @FXML
    private TreeTableColumn<Elemento, Integer> col_Estado;

    @FXML
    private JFXTextField txtid;

    @FXML
    private JFXTextField txtnombre;

    @FXML
    private JFXTextField txtdesc;

    @FXML
    private JFXTextField txtestado;
    
    
    //this.col_id.setCellValueFactory(new TreeItemPropertyValueFactory<>("ID");
    

       @FXML
    void createElement(ActionEvent event) {
        
        if(this.txtid.getText()== null || this.txtestado.getText()==null || this.txtnombre.getText()==null){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("Debe llenar los espacios obligatorios");
        alert.showAndWait();
        
        
        
        }else{
        
        try{
        
        int id = Integer.parseInt( this.txtid.getText());
        String nombre = this.txtnombre.getText();
        String desc = this.txtdesc.getText();
        int estado = Integer.parseInt( this.txtestado.getText());
        
        Elemento el = new Elemento(id, nombre, desc, estado);
        
        dao.create(el, cat.getID());
        
        }catch(NumberFormatException e){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("formato incorrecto");
        alert.showAndWait();
        
        
        
        
        }
        }
        
        this.txtdesc.setText(null);
        this.txtestado.setText(null);
        this.txtid.setText(null);
        this.txtnombre.setText(null);
        
    }
    public void getElementos (){
    
    this.oblist=FXCollections.observableList(new ElementoDAO().getElements(cat.getID()));
    
    TreeItem<Elemento> root = new RecursiveTreeItem<>(oblist, RecursiveTreeObject::getChildren);
    
    elementsTable.setRoot(root);
    elementsTable.setShowRoot(false);
    
    }
    
    

    @FXML
    void eliminarElemento(ActionEvent event) {
        Elemento el= this.elementsTable.getSelectionModel().getSelectedItem().getValue();
        if (el==null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("Debe seleccionar un elemento");
        alert.showAndWait();
        
        }else{
        
        dao.delete(el);
        
        
        
        
        
        }
        
        
        
        
        

    }

    @FXML
    void modificarElemento(ActionEvent event) {
        Elemento el= this.elementsTable.getSelectionModel().getSelectedItem().getValue();
        if (el==null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("Debe seleccionar un elemento");
        alert.showAndWait();
        
        }else{
            
        if(this.txtid.getText()== null || this.txtestado.getText()==null || this.txtnombre.getText()==null){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("Debe llenar los espacios obligatorios");
        alert.showAndWait();
        
        
        
        }else{
        
        try{
        
        int id = Integer.parseInt( this.txtid.getText());
        String nombre = this.txtnombre.getText();
        String desc = this.txtdesc.getText();
        int estado = Integer.parseInt( this.txtestado.getText());
        
        Elemento el2 = new Elemento(id, nombre, desc, estado);
        
        dao.update(el2);
        
        }catch(NumberFormatException e){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("formato incorrecto");
        alert.showAndWait();
        
        
        
        
        }
        }    
            
            
            
        
        
        
        
        
        }

    }
    
        @FXML
    void seleccionar(MouseEvent event) {
        
        Elemento el= this.elementsTable.getSelectionModel().getSelectedItem().getValue();
        if (el!=null){
            
            this.colId.setText(el.getID()+"");
            this.col_Descrip.setText(el.getDescripción());
            this.col_Estado.setText(el.getEstado()+"");
            this.col_Nombre.setText(el.getNombre());      
        
        }
        

    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getElementos();
        
        this.colId.setCellValueFactory( new TreeItemPropertyValueFactory<>("ID") );
        this.col_Nombre.setCellValueFactory( new TreeItemPropertyValueFactory<>("nombre") );
        this.col_Descrip.setCellValueFactory( new TreeItemPropertyValueFactory<>("descripción") );
        this.col_Estado.setCellValueFactory( new TreeItemPropertyValueFactory<>("Estado") );
    }    
    
}
