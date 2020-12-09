package Control.SU;

import Control.ValidarRegistro;
import DAO.LaboratorioDAO;
import Entidad.Laboratorio;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

public class MLabs implements Initializable {

    private ObservableList<Laboratorio> data;

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<Laboratorio> labsTable;

    @FXML
    private TreeTableColumn<Laboratorio, Integer> TID;

    @FXML
    private TreeTableColumn<Laboratorio, String> TNombre;

    @FXML
    private TreeTableColumn<Laboratorio, Integer> TTelefono;

    @FXML
    private TreeTableColumn<Laboratorio, String> TUbicacion;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField location;
    @FXML
    private JFXButton backbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLabsTable();
    }

    private void getLabsTable() {
        data = FXCollections.observableList(new LaboratorioDAO().getLabs());

        JFXTreeTableColumn<Laboratorio, String> settingsColumn = new JFXTreeTableColumn<>("Eliminar");
        settingsColumn.setPrefWidth(95);
        Callback<TreeTableColumn<Laboratorio, String>, TreeTableCell<Laboratorio, String>> cellFactory
                = //
                (final TreeTableColumn<Laboratorio, String> param) -> {
                    final TreeTableCell<Laboratorio, String> cell = new TreeTableCell<Laboratorio, String>() {

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

                            Laboratorio lab = this.getTreeTableRow().getItem();

                            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION);
                            alertm.setHeaderText(null);
                            alertm.setTitle("Confirmación");
                            alertm.setContentText("Se eliminará " + lab.getNombre());
                            Optional<ButtonType> action = alertm.showAndWait();

                            if (action.get() == ButtonType.OK) {

                                if (new LaboratorioDAO().disableLab(lab)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("Laboratorio eliminado");
                                    alert.setContentText(null);
                                    alert.showAndWait();
                                    getLabsTable();

                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("No se pudo eliminar el laboratorio");
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

        labsTable.getColumns().set(4, settingsColumn);

        TID.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        TNombre.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );
        TTelefono.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Telefono")
        );
        TUbicacion.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Ubicacion")
        );

        TreeItem<Laboratorio> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);

        labsTable.setRoot(root);
        labsTable.setShowRoot(false);
    }

    @FXML
    private void createlab(ActionEvent event) {
        String respuesta = new ValidarRegistro().verificarLab(new Laboratorio(
                name.getText().replaceAll("\\s+",""), phone.getText().replaceAll("\\s+",""), location.getText().replaceAll("\\s+","")));
        if (respuesta.equals("Laboratorio creado")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(respuesta);
            alert.setContentText(null);
            alert.showAndWait();
            cleanForm();
            getLabsTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifica la información");
            alert.setContentText(respuesta);
            alert.showAndWait();
        }
    }

    private void cleanForm() {
        name.setText("");
        phone.setText("");
        location.setText("");
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
            Stage stage1 = (Stage) name.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
