package Control.User;

import Control.LoginController;
import DAO.LaboratorioDAO;
import DAO.ReservasDAO;
import Entidad.Categoria;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class UserReserverController implements Initializable {

    private Usuario user = LoginController.getUsuario();
    private ObservableList<Laboratorio> dataLabs;
    private ObservableList<MacroCategoria> dataCats;
    private ObservableList<Categoria> dataElems = FXCollections.observableList(new ArrayList<>());
    private ObservableList<Categoria> reserveElems = FXCollections.observableList(new ArrayList<>());

    @FXML
    private JFXComboBox<Laboratorio> labList;
    @FXML
    private JFXComboBox<MacroCategoria> macroList;
    @FXML
    private JFXTreeTableView<Categoria> elemsTable;
    @FXML
    private JFXTreeTableView<Categoria> reserveTable;
    @FXML
    private TreeTableColumn<Categoria, String> TElementoL;
    @FXML
    private TreeTableColumn<Categoria, String> TNombreR;   
    @FXML
    private TreeTableColumn<Categoria, String> TCantidadR;
    @FXML
    private TreeTableColumn<Categoria, String> TCantidadL;
    @FXML
    private TreeTableColumn<Categoria, Integer> TMaxL;
    @FXML
    private JFXButton backToMenu1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLabs();
        initReserveTable();
    }

    private Laboratorio findID(int id) {
        for (Laboratorio lab : dataLabs) {
            if (lab.getID() == id) {
                return lab;
            }

        }
        return null;
    }

    private MacroCategoria findCID(int id) {
        for (MacroCategoria cat : dataCats) {
            if (cat.getID() == id) {
                return cat;
            }

        }
        return null;
    }

    private void getLabs() {
        dataLabs = FXCollections.observableList(new LaboratorioDAO().getLabs());

        labList.setItems(dataLabs);
        labList.setConverter(new StringConverter<Laboratorio>() {
            @Override
            public String toString(Laboratorio object) {
                return object.getID() + "-" + object.getNombre();
            }

            @Override
            public Laboratorio fromString(String string) {
                return findID(Integer.parseInt(string.split("-")[0]));
            }
        });

    }

    private void getMacro() {
        int selectedLabID = labList.getSelectionModel().getSelectedItem().getID();
        dataCats = FXCollections.observableList(new LaboratorioDAO().getMCats(selectedLabID));

        macroList.setItems(dataCats);
        macroList.setConverter(new StringConverter<MacroCategoria>() {
            @Override
            public String toString(MacroCategoria object) {
                return object.getID() + "-" + object.getNombre();
            }

            @Override
            public MacroCategoria fromString(String string) {
                return findCID(Integer.parseInt(string.split("-")[0]));
            }
        });

    }

    private void getCatsTable() {
        MacroCategoria Mcat = macroList.getSelectionModel().getSelectedItem();

        dataElems = FXCollections.observableList(new LaboratorioDAO().getCats(Mcat));
        
        JFXTreeTableColumn<Categoria, String> TAnadirp = new JFXTreeTableColumn<>("Añadir");
        TAnadirp.setPrefWidth(95);
        Callback<TreeTableColumn<Categoria, String>, TreeTableCell<Categoria, String>> cellFactory
                = //
                (final TreeTableColumn<Categoria, String> param) -> {
                    final TreeTableCell<Categoria, String> cell = new TreeTableCell<Categoria, String>() {

                final JFXButton btn = new JFXButton("Añadir");

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
                            
                            
                            
                            if(reserveElems.contains(this.getTreeTableRow().getItem())){
                                int i = reserveElems.indexOf(this.getTreeTableRow().getItem());
                                int m = reserveElems.get(i).getCantidadMax() + 1;
                                if(this.getTreeTableRow().getItem().getCantidadMax() >= m || this.getTreeTableRow().getItem().getCantidadLibre() >= m){
                                    reserveElems.get(i).setCantidadMax(m);
                                }else{
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setHeaderText(null);
                                    alert.setTitle("Error");
                                    alert.setContentText("No hay más elementos disponibles.");
                                    alert.showAndWait();
                                }
                            }
                            else{
                                if(this.getTreeTableRow().getItem().getCantidadMax() > 0 && this.getTreeTableRow().getItem().getCantidadLibre() > 0){
                                    Categoria o = this.getTreeTableRow().getItem();
                                    o.setCantidadMax(1);
                                    reserveElems.add(o);
                                }else{
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setHeaderText(null);
                                    alert.setTitle("Error");
                                    alert.setContentText("No hay más elementos disponibles.");
                                    alert.showAndWait();
                                }
                            }
                            reserveTable.refresh();
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
                    return cell;
                };

        TAnadirp.setCellFactory(cellFactory);

        elemsTable.getColumns().set(3, TAnadirp);

        TElementoL.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        TCantidadL.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("CantidadLibre")
        );
        TMaxL.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("CantidadMax")
        );

        TreeItem<Categoria> root = new RecursiveTreeItem<>(dataElems, RecursiveTreeObject::getChildren);
        
        elemsTable.setRoot(root);
        elemsTable.setShowRoot(false);
    }

    private void initReserveTable(){
        JFXTreeTableColumn<Categoria, String> TQuitarp = new JFXTreeTableColumn<>("Añadir");
        TQuitarp.setPrefWidth(95);
        Callback<TreeTableColumn<Categoria, String>, TreeTableCell<Categoria, String>> cellFactory
                = //
                (final TreeTableColumn<Categoria, String> param) -> {
                    final TreeTableCell<Categoria, String> cell = new TreeTableCell<Categoria, String>() {

                final JFXButton btn = new JFXButton("Quitar");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setButtonType(JFXButton.ButtonType.FLAT);
                        btn.setStyle("-fx-background-color:  #1a237e; -fx-text-fill: #ffffff;");
                        btn.setOnAction(event -> {
                            
                            this.getTreeTableRow().getItem().setCantidadMax(this.getTreeTableRow().getItem().getCantidadMax() - 1);
                            
                            if(this.getTreeTableRow().getItem().getCantidadMax() == 0){
                                reserveElems.remove(this.getTreeTableRow().getItem());
                            }
                            
                            reserveTable.refresh();
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        TQuitarp.setCellFactory(cellFactory);

        reserveTable.getColumns().set(2, TQuitarp);
        
        TCantidadR.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("CantidadMax")
        );
        
        TNombreR.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        
        TreeItem<Categoria> root = new RecursiveTreeItem<>(reserveElems, RecursiveTreeObject::getChildren);

        reserveTable.setRoot(root);
        reserveTable.setShowRoot(false);
    }
    
    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) backToMenu1.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void BackToMenu(ActionEvent event) {
        ToPath("/Frontera/User/UserMenuUX.fxml");
    }

    @FXML
    private void updateMacro(ActionEvent event) {
        dataElems.clear();
        elemsTable.refresh();
        reserveElems.clear();
        reserveTable.refresh();
        getMacro();
    }

    @FXML
    private void updateCats(ActionEvent event) {
        getCatsTable();
    }
    
    @FXML
    private void makeReserve(ActionEvent event) {
        if(!new ReservasDAO().checkReservasUser(LoginController.getUsuario(), labList.getSelectionModel().getSelectedItem().getID())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Ya tienes una reserva para este laboratorio.");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }
        
        if(reserveElems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No tienes elementos en la lista de reserva.");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else {
            if (new ReservasDAO().makeReserve(reserveElems, labList.getSelectionModel().getSelectedItem().getID(), LoginController.getUsuario().getDocumento())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Reserva realizada.");
                alert.setContentText(null);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se pudo realizar la reserva.");
                alert.setContentText(null);
                alert.showAndWait();
            }
        }
    }
}
