package Control.Admin;

import DAO.CategoriasDAO;
import DAO.ElementoDAO;
import Entidad.Categoria;
import Entidad.Elemento;
import Entidad.MacroCategoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminElementosUXController implements Initializable {
    
    private static Categoria cat; 
    private MacroCategoria mc;
    private Elemento selectedElement = null;
    
    private ObservableList<Elemento> oblist = FXCollections.observableArrayList();
    

    
    //col_ID.setCellValueFactory(
      //          new TreeItemPropertyValueFactory<>("ID")
       // );
    
    
    
   private ElementoDAO dao = new ElementoDAO();
    
    @FXML
    private JFXTreeTableView<Elemento> elementsTable;

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
    
     @FXML
    private JFXButton volver;
     
     @FXML
    private JFXButton crearBtn;
     
    @FXML
    private JFXComboBox<String> comboEstados;
    
    private ObservableList<String> estados = FXCollections.observableArrayList();
    
    
    
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
        this.comboEstados.setValue(null);
        getElementos();
        
    }
    public void getElementos (){
    
    this.oblist=FXCollections.observableList(new ElementoDAO().getElements(cat.getID()));
    
    
        JFXTreeTableColumn<Elemento, String> settingsColumn = new JFXTreeTableColumn<>("Eliminar");
        settingsColumn.setPrefWidth(95);
        Callback<TreeTableColumn<Elemento, String>, TreeTableCell<Elemento, String>> cellFactory
                = //
                (final TreeTableColumn<Elemento, String> param) -> {
                    final TreeTableCell<Elemento, String> cell = new TreeTableCell<Elemento, String>() {

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

                            Elemento elem = this.getTreeTableRow().getItem();

                            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION);
                            alertm.setHeaderText(null);
                            alertm.setTitle("Confirmación");
                            alertm.setContentText("Se eliminará " + elem.getNombre());
                            Optional<ButtonType> action = alertm.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {

                                if (dao.delete(elem)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("Elemento eliminado");
                                    alert.setContentText(null);
                                    alert.showAndWait();
                                    //emptyTable();
                                    getElementos();
                                    limpiar();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("No se pudo eliminar el elemento.");
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

        elementsTable.getColumns().set(4, settingsColumn);

    
    
    
    
    
    this.colId.setCellValueFactory( new TreeItemPropertyValueFactory<>("ID") );
        this.col_Nombre.setCellValueFactory( new TreeItemPropertyValueFactory<>("nombre") );
        this.col_Descrip.setCellValueFactory( new TreeItemPropertyValueFactory<>("descripción") );
        this.col_Estado.setCellValueFactory( new TreeItemPropertyValueFactory<>("Estado") );
    
    
    TreeItem<Elemento> root = new RecursiveTreeItem<>(oblist, RecursiveTreeObject::getChildren);
    
    elementsTable.setRoot(root);
    elementsTable.setShowRoot(false);
    
    }
    
    

    @FXML
    void eliminarElemento(ActionEvent event) {
      
        if (selectedElement==null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("Debe seleccionar un elemento");
        alert.showAndWait();
        
        }else{
        selectedElement= this.elementsTable.getSelectionModel().getSelectedItem().getValue();
        dao.delete(selectedElement);
          }
        
        this.txtdesc.setText(null);
        this.txtestado.setText(null);
        this.txtid.setText(null);
        this.txtnombre.setText(null);
        this.comboEstados.setValue(null);
        
        this.crearBtn.setVisible(true);
        
        
        
getElementos();
    }

    @FXML
    void modificarElemento(ActionEvent event) {
        
        
        if (selectedElement==null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("error");
        alert.setContentText("Debe seleccionar un elemento para modificar");
        alert.showAndWait();
        
            }else if(this.txtid.getText().length()==0||  this.txtestado.getText().length()==0 || this.txtnombre.getText().length()==0 || this.txtnombre.getText()==" "){
            
            //selectedElement= this.elementsTable.getSelectionModel().getSelectedItem().getValue(); 
            //if(this.txtid.getText()== null|| this.txtid.getText()== "" || this.txtestado.getText()==null || this.txtestado.getText()=="" || this.txtnombre.getText()==null || this.txtnombre.getText()==""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("error");
            alert.setContentText("Debe llenar los espacios obligatorios para modificar");
            alert.showAndWait();
            
      
            }else{
            
           
                
        try{
        
                int id = Integer.parseInt( this.txtid.getText());
                String nombre = this.txtnombre.getText();
                String desc = this.txtdesc.getText();
                int estado = Integer.parseInt( this.txtestado.getText());

                Elemento el2 = new Elemento(id, nombre, desc, estado);

                dao.update(el2);

                }catch(Exception e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("error");
                alert.setContentText("formato incorrecto al modificar ");
                alert.showAndWait();

                }
        }    
         
        /*this.txtdesc.setText(null);
        this.txtestado.setText(null);
        this.txtid.setText(null);
        this.txtnombre.setText(null);
        this.comboEstados.setValue(null);
        
        */
        limpiar();
        
        getElementos();
        
    }
    
        @FXML
    void seleccionar(MouseEvent event) {
        
        selectedElement= this.elementsTable.getSelectionModel().getSelectedItem().getValue();
        if (selectedElement!=null){
            
            this.txtid.setText(selectedElement.getID()+"");
            this.txtdesc.setText(selectedElement.getDescripción());
            this.txtestado.setText(selectedElement.getEstado()+"");
            this.txtnombre.setText(selectedElement.getNombre()); 
           this.comboEstados.setValue(selectedElement.getEstado());
        
        }
    this.crearBtn.setVisible(false);

    }
    
    
    @FXML
    void onSelectItem(ActionEvent event) {
        
        
        this.txtestado.setText(resolucion(this.comboEstados.getSelectionModel().getSelectedItem())+"");
        

    }
    
    @FXML
    void BackToMenu(ActionEvent event) {
        
        ToPath("/Frontera/Admin/AdminCategoriasUX.fxml");
        
    }
    
    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            AdminCategoriasController ca = fxmlLoader.getController();
            ca.setMacroCategoria(mc);
            
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
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      estados.add("Disponible");
      estados.add("Prestamo");
      estados.add("Mantenimiento");
      estados.add("Inhabilitado");
      comboEstados.setItems(estados);
      
      this.txtestado.setVisible(false);
        
    }    
    
    public void setCategoria(Categoria cat) {
            this.cat = cat;
            getElementos();
            
    }
    
    public void setMacroCategoria(MacroCategoria mc) {
            this.mc = mc;
            
    }
    
    public int resolucion(String a){
    
    int b =0;
    
        switch (a){
            case "Disponible":
            b=1;
            break;
            case "Prestamo":
            b=2;
            break;
            case "Mantenimiento":
            b=3;
            break;
            case "Inhabilitado":
            b=0;
            break;
        }
    
    return b;
    }
    
    public String resolucn(int a){
    String b="";
        switch (a){
            case 0:
            b="Inhabilitado";
            break;
            case 1:
            b="Disponible";
            break;
            case 2:
            b="Prestamo";
            break;
            case 3:
            b="Mantenimiento";
            break;
        }
    return b;
    }
    
    public void limpiar(){
    
        this.txtdesc.setText(null);
        this.txtestado.setText(null);
        this.txtid.setText(null);
        this.txtnombre.setText(null);
        this.comboEstados.setValue(null);
        selectedElement = null;
        this.crearBtn.setVisible(true);
    
    
    }
    
}
