/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.SU;

import DAO.LaboratorioDAO;
import DAO.UsuarioDAO;
import Entidad.Laboratorio;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Optional;
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
public class MAssign implements Initializable {

    private ObservableList<Laboratorio> dataLabs;
    private ObservableList<Usuario> dataAdminsIn;
    private ObservableList<Usuario> dataAdminsOut;

    @FXML
    private JFXButton backbtn;
    @FXML
    private JFXComboBox<Laboratorio> labList;
    @FXML
    private JFXTreeTableView<Usuario> InTable;
    @FXML
    private TreeTableColumn<Usuario, String> INombres;
    @FXML
    private TreeTableColumn<Usuario, String> IApellidos;
    @FXML
    private TreeTableColumn<Usuario, Integer> IDocumento;
    @FXML
    private JFXTreeTableView<Usuario> OutTable;
    @FXML
    private TreeTableColumn<Usuario, String> ONombres;
    @FXML
    private TreeTableColumn<Usuario, String> OApellidos;
    @FXML
    private TreeTableColumn<Usuario, Integer> ODocumento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLabs();
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

    private void getAdminsInLabTable(Laboratorio lab) {

        dataAdminsIn = FXCollections.observableList(new LaboratorioDAO().getAdminsinLab(lab));

        INombres.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        IApellidos.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("apellido")
        );
        IDocumento.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("documento")
        );

        TreeItem<Usuario> root = new RecursiveTreeItem<>(dataAdminsIn, RecursiveTreeObject::getChildren);

        InTable.setRoot(root);
        InTable.setShowRoot(false);
    }

    private void getAdminsOutLabTable(Laboratorio lab) {

        dataAdminsOut = FXCollections.observableList(new LaboratorioDAO().getAdminsOutLab(lab));

        ONombres.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        OApellidos.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("apellido")
        );
        ODocumento.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("documento")
        );

        TreeItem<Usuario> root = new RecursiveTreeItem<>(dataAdminsOut, RecursiveTreeObject::getChildren);

        OutTable.setRoot(root);
        OutTable.setShowRoot(false);
    }

    private Laboratorio findID(int id) {
        for (Laboratorio lab : dataLabs) {
            if (lab.getID() == id) {
                return lab;
            }

        }
        return null;
    }

    @FXML
    private void BackToMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/SU/SUMenuUX.fxml"));
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

    @FXML
    private void onSelectItem(ActionEvent event) {
        Laboratorio lab = labList.getSelectionModel().getSelectedItem();
        getAdminsInLabTable(lab);
        getAdminsOutLabTable(lab);

    }

    @FXML
    private void toLeft(ActionEvent event) {
        try {
            Usuario user = OutTable.getSelectionModel().getSelectedItem().getValue();
            Laboratorio lab = labList.getSelectionModel().getSelectedItem();
            if (new LaboratorioDAO().addAdmintoLab(user, lab)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Se añadió el usuario " + user.getNombre() + " "
                        + user.getApellido() + " al laboratorio " + lab.getNombre());
                alert.setContentText(null);
                alert.showAndWait();
                getAdminsInLabTable(lab);
                getAdminsOutLabTable(lab);
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se pudo añadir el usuario al laboratorio");
                alert.setContentText(null);
                alert.showAndWait();

            }
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Ocurrió un error");
            alert.setContentText("Debe seleccionar un laboratorio y un usuario");
            alert.showAndWait();
        }
    }

    @FXML
    private void toRight(ActionEvent event) {
        try {
            Usuario user = InTable.getSelectionModel().getSelectedItem().getValue();
            Laboratorio lab = labList.getSelectionModel().getSelectedItem();
            if (new LaboratorioDAO().removeAdminfromLab(user, lab)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Se eliminó el usuario " + user.getNombre() + " "
                        + user.getApellido() + " del laboratorio " + lab.getNombre());
                alert.setContentText(null);
                alert.showAndWait();
                getAdminsInLabTable(lab);
                getAdminsOutLabTable(lab);
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("No se pudo eliminar el usuario del laboratorio");
                alert.setContentText(null);
                alert.showAndWait();

            }
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Ocurrió un error");
            alert.setContentText("Debe seleccionar un laboratorio y un usuario");
            alert.showAndWait();
        }
    }

}
