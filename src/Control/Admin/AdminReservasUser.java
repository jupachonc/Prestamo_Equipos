/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import static Control.Admin.AdminPrestamo.isNumeric;
import DAO.ReservasDAO;
import DAO.UsuarioDAO;
import Entidad.Elemento;
import Entidad.Reserva;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AdminReservasUser implements Initializable {

    private Usuario estudiante = null;
    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();

    @FXML
    private JFXTreeTableView<Reserva> reservaTable;
    @FXML
    private TreeTableColumn<Reserva, Integer> idreserva;
    @FXML
    private TreeTableColumn<Reserva, Timestamp> timereserva;
    @FXML
    private TreeTableColumn<Reserva, String> usarreserva;
    @FXML
    private Label Name;
    @FXML
    private Label LastName;
    @FXML
    private Label Document;
    @FXML
    private JFXTextField DocumentN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void searchDocument(ActionEvent event) {
        if (isNumeric(DocumentN.getText())) {
            System.out.println(Integer.parseInt(DocumentN.getText()));
            estudiante = new UsuarioDAO().getUser(Integer.parseInt(DocumentN.getText()));
            
            if(estudiante == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Información");
                alert.setHeaderText("El documento es inválido");
                alert.setContentText(null);
                alert.showAndWait();
                return;
            }
            
            updateUser();
            getReservas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("El documento es inválido");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    private void getReservas() {
        Date date = new Date();
        Timestamp nw = new Timestamp(date.getTime());
        date.setHours(0);
        date.setMinutes(0);
        Timestamp ts = new Timestamp(date.getTime());
        for (Reserva rv : new ReservasDAO().getReservasUser(estudiante)) {
            if (rv.getTiempoReserva().after(ts) && rv.getTiempoReserva().before(nw)) {
                reservas.add(rv);
            }
            JFXTreeTableColumn<Reserva, String> settingsColumn = new JFXTreeTableColumn<>("Usar");
            settingsColumn.setPrefWidth(95);
            Callback<TreeTableColumn<Reserva, String>, TreeTableCell<Reserva, String>> cellFactory
                    = //
                    (final TreeTableColumn<Reserva, String> param) -> {
                        final TreeTableCell<Reserva, String> cell = new TreeTableCell<Reserva, String>() {

                    final JFXButton btn = new JFXButton("Usar");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Reserva rsv = this.getTreeTableRow().getItem();
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.FLAT);
                            btn.setStyle("-fx-background-color:  #f44336; -fx-text-fill: #ffffff;");
                            btn.setOnAction(event -> {
                                try {
                                    AdminPrestamo.flag = false;
                                    AdminPrestamo.estudiante = estudiante;
                                    AdminPrestamo.reserve = rsv.getID();
                                    AdminPrestamo.CatsReserva
                                            = FXCollections.observableArrayList(new ReservasDAO().getReserve(rsv.getID()));
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/Admin/AdminPrestamoUX.fxml"));
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
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                        return cell;
                    };

            settingsColumn.setCellFactory(cellFactory);

            reservaTable.getColumns().set(2, settingsColumn);

            idreserva.setCellValueFactory(
                    new TreeItemPropertyValueFactory<>("ID")
            );
            timereserva.setCellValueFactory(
                    new TreeItemPropertyValueFactory<>("tiempoReserva")
            );

            TreeItem<Reserva> root = new RecursiveTreeItem<>(reservas, RecursiveTreeObject::getChildren);

            reservaTable.setRoot(root);
            reservaTable.setShowRoot(false);

        }
    }

    private void updateUser() {
        Name.setText(estudiante.getNombre());
        LastName.setText(estudiante.getApellido());
        Document.setText(String.valueOf(estudiante.getDocumento()));
    }

    @FXML
    private void BackToMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/Admin/AdminPrestamoUX.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) Name.getScene().getWindow();
            stage1.close();
            AdminPrestamo.flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
