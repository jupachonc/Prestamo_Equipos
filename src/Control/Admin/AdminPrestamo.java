/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import DAO.LaboratorioDAO;
import DAO.ReservasDAO;
import DAO.UsuarioDAO;
import Entidad.Categoria;
import Entidad.Elemento;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AdminPrestamo implements Initializable {

    private static Usuario estudiante = null;
    private static Laboratorio lab = AdminMenuController.currentLab;
    private static ObservableList<MacroCategoria> dataMCats;
    private static MacroCategoria MCSelected = null;
    private static ObservableList<Elemento> dataSearch = FXCollections.observableArrayList();
    private static ObservableList<Elemento> dataPrestamo = FXCollections.observableArrayList();
    private static ObservableList<Categoria> CatsReserva = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    private static JFXTreeTableView<Elemento> BusquedaTable;

    @FXML
    private static TreeTableColumn<Elemento, Integer> TIDL;

    @FXML
    private static TreeTableColumn<Elemento, String> TElementoL;

    @FXML
    private static TreeTableColumn<?, ?> TAnadirL;

    @FXML
    private static JFXTreeTableView<Elemento> PrestamoTable;

    @FXML
    private static TreeTableColumn<Elemento, Integer> TIDR;

    @FXML
    private static TreeTableColumn<Elemento, String> TNombreR;

    @FXML
    private static TreeTableColumn<?, ?> TQuitarR;

    @FXML
    private static JFXTextArea Observaciones;

    @FXML
    private static JFXComboBox<MacroCategoria> macroList;

    @FXML
    private static Label Name;
    @FXML
    private static Label LastName;
    @FXML
    private static Label Document;
    @FXML
    private static JFXTextField DocumentN;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getMCats();
        getPrestamo();
    }

    @FXML
    void BackToMenu(ActionEvent event) {

    }

    @FXML
    void Prestamo(ActionEvent event) {

    }

    @FXML
    void goToReservas(ActionEvent event) {
        CatsReserva = FXCollections.observableList(new ReservasDAO().getReserve(1));
        getSearch(false);

    }

    @FXML
    void searchDocument(ActionEvent event) {
        if (isNumeric(DocumentN.getText())) {
            System.out.println(Integer.parseInt(DocumentN.getText()));
            estudiante = new UsuarioDAO().getUser(Integer.parseInt(DocumentN.getText()));
            updateUser();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("El documento es inválido");
            alert.setContentText(null);
            alert.showAndWait();
        }

    }

    private void updateUser() {
        Name.setText(estudiante.getNombre());
        LastName.setText(estudiante.getApellido());
        Document.setText(String.valueOf(estudiante.getDocumento()));
    }

    private void getMCats() {
        dataMCats = FXCollections.observableList(new LaboratorioDAO().getMCats(lab.getID()));

        macroList.setItems(dataMCats);
        macroList.setConverter(new StringConverter<MacroCategoria>() {
            @Override
            public String toString(MacroCategoria object) {
                return object.getID() + "-" + object.getNombre();
            }

            @Override
            public MacroCategoria fromString(String string) {
                return findID(Integer.parseInt(string.split("-")[0]));
            }
        });

    }

    private MacroCategoria findID(int id) {
        for (MacroCategoria MCat : dataMCats) {
            if (MCat.getID() == id) {
                return MCat;
            }

        }
        return null;
    }

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    private void getPrestamo() {
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
                    Elemento elm = this.getTreeTableRow().getItem();
                    if (empty || elm.getID() == 0) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setButtonType(JFXButton.ButtonType.FLAT);
                        btn.setStyle("-fx-background-color:  #f44336; -fx-text-fill: #ffffff;");
                        btn.setOnAction(event -> {
                            dataPrestamo.remove(elm);
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
                    return cell;
                };

        settingsColumn.setCellFactory(cellFactory);

        PrestamoTable.getColumns().set(2, settingsColumn);

        TIDR.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        TNombreR.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );

        TreeItem<Elemento> root = new RecursiveTreeItem<>(dataPrestamo, RecursiveTreeObject::getChildren);

        PrestamoTable.setRoot(root);
        PrestamoTable.setShowRoot(false);
    }
    

    private static void getSearch(boolean flag) {
        JFXTreeTableColumn<Elemento, String> settingsColumn = new JFXTreeTableColumn<>("Añadir");
        settingsColumn.setPrefWidth(95);
        Callback<TreeTableColumn<Elemento, String>, TreeTableCell<Elemento, String>> cellFactory
                = //
                (final TreeTableColumn<Elemento, String> param) -> {
                    final TreeTableCell<Elemento, String> cell = new TreeTableCell<Elemento, String>() {

                final JFXButton btn = new JFXButton("Añadir");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Elemento elm = this.getTreeTableRow().getItem();
                    if (empty || elm.getID() == 0) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setButtonType(JFXButton.ButtonType.FLAT);
                        btn.setStyle("-fx-background-color:  #f44336; -fx-text-fill: #ffffff;");
                        btn.setOnAction(event -> {
                            if (!dataPrestamo.contains(elm)) {
                                dataPrestamo.add(elm);
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

        BusquedaTable.getColumns().set(2, settingsColumn);

        TIDL.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        TElementoL.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );
        
        dataSearch.clear();
        if (flag) {
            for (Categoria cat : new LaboratorioDAO().getCats(MCSelected)) {
                Elemento Main = new Elemento(0, cat.getNombre(), "", 0);
                for (Elemento element : new LaboratorioDAO().getElements(cat)) {
                    Main.getChildren().add(element);
                }
                dataSearch.add(Main);
            }
        } else {
            for (Categoria cat : CatsReserva) {
                Elemento Main = new Elemento(0, cat.getNombre(), "", 0);
                for (Elemento element : new LaboratorioDAO().getElements(cat)) {
                    Main.getChildren().add(element);
                }
                dataSearch.add(Main);
            }

        }
        TreeItem<Elemento> root = new RecursiveTreeItem<>(dataSearch, RecursiveTreeObject::getChildren);

        BusquedaTable.setRoot(root);
        BusquedaTable.setShowRoot(false);
    }

    @FXML
    private void onSelectedMC(ActionEvent event) {
        MCSelected = macroList.getSelectionModel().getSelectedItem();
        getSearch(true);
    }

}
