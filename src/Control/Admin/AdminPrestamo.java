/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import Control.LoginController;
import DAO.LaboratorioDAO;
import DAO.PrestamoDAO;
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
import java.io.IOException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AdminPrestamo implements Initializable {

    public static Usuario estudiante = null;
    private static Laboratorio lab;
    private static Usuario admin = LoginController.getUsuario();
    public static int reserve = 0;
    public static boolean flag;
    private static MacroCategoria MCSelected = null;

    private static ObservableList<MacroCategoria> dataMCats;
    private static ObservableList<Elemento> dataSearch = FXCollections.observableArrayList();
    private static ObservableList<Elemento> dataPrestamo = FXCollections.observableArrayList();
    public static ObservableList<Categoria> CatsReserva = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<Elemento> BusquedaTable;
    @FXML
    private TreeTableColumn<Elemento, Integer> TIDL;
    @FXML
    private TreeTableColumn<Elemento, String> TElementoL;
    @FXML
    private JFXTreeTableView<Elemento> PrestamoTable;
    @FXML
    private TreeTableColumn<Elemento, Integer> TIDR;
    @FXML
    private TreeTableColumn<Elemento, String> TNombreR;
    @FXML
    private JFXTextArea Observaciones;
    @FXML
    private Label Name;
    @FXML
    private Label LastName;
    @FXML
    private Label Document;
    @FXML
    private JFXTextField DocumentN;
    @FXML
    private TreeTableColumn<?, ?> TAnadirL;
    @FXML
    private TreeTableColumn<?, ?> TQuitarR;
    @FXML
    private JFXComboBox<MacroCategoria> macroLista;
    @FXML
    private JFXButton Devolucionbtn;

    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) Name.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lab = AdminMenuController.currentLab;
        getMCats();
        getPrestamo();
        System.out.println(flag);
        if (!flag) {
            getSearch();
            updateUser();
            System.out.println("executed");
        }
    }

    @FXML
    void BackToMenu(ActionEvent event) {
        ToPath("/Frontera/Admin/AdminMenuUX.fxml");
    }

    @FXML
    void Prestamo(ActionEvent event) {
        if (!dataPrestamo.isEmpty() && estudiante != null) {
            int rs = new PrestamoDAO().doPrestamo(new ArrayList<Elemento>(dataPrestamo),
                    estudiante, admin, Observaciones.getText(), reserve, lab.getID());
            if (rs != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Se añadió el préstamo con el ID " + rs);
                alert.setContentText(null);
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("Ocurrió un error, inténtelo de nuevo");
                alert.setContentText(null);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifique la información");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    @FXML
    void goToReservas(ActionEvent event) {
        ToPath("/Frontera/Admin/AdminReservasUserUX.fxml");
    }

    @FXML
    void searchDocument(ActionEvent event) {
        if (isNumeric(DocumentN.getText())) {
            System.out.println(Integer.parseInt(DocumentN.getText()));
            estudiante = new UsuarioDAO().getUser(Integer.parseInt(DocumentN.getText()));

            if (estudiante == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("El documento es inválido");
                alert.setContentText(null);
                alert.showAndWait();
                return;
            }

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

        System.out.println(dataMCats);

        macroLista.setItems(dataMCats);
        macroLista.setConverter(new StringConverter<MacroCategoria>() {
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

    private static MacroCategoria findID(int id) {
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
                    if (empty) {
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

    private void getSearch() {
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
                    if (empty) {
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
                        if (elm.getID() != 0) {
                            setGraphic(btn);
                            setText(null);
                        }
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
        MCSelected = macroLista.getSelectionModel().getSelectedItem();
        getSearch();
    }

    @FXML
    private void goToDevolucion(ActionEvent event) {
        ToPath("/Frontera/Admin/AdminPrestamosUserUX.fxml");
    }
}
