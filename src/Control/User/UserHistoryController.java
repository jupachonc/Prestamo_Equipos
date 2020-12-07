package Control.User;

import Control.LoginController;
import DAO.LaboratorioDAO;
import DAO.PrestamoDAO;
import Entidad.Laboratorio;
import Entidad.Prestamo;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
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

public class UserHistoryController implements Initializable {
    private Usuario user = LoginController.getUsuario();
    public Laboratorio currLab;
    public static Prestamo pres;
    private ObservableList<Laboratorio> dataLabs;
    private static ObservableList<Prestamo> dataPres;
    
    @FXML
    private JFXButton backToMenu1;
    @FXML
    private JFXComboBox<Laboratorio> labList;
    @FXML
    private JFXTreeTableView<Prestamo> historialTable;
    @FXML
    private TreeTableColumn<Prestamo, Integer> IDColumn;
    @FXML
    private TreeTableColumn<Prestamo, String> LabColumn;   
    @FXML
    private TreeTableColumn<Prestamo, String> stateColumn;
    @FXML
    private TreeTableColumn<Prestamo, String> dateColumn;
    @FXML
    private JFXDatePicker datePickerInit;
    @FXML
    private JFXDatePicker datePickerEnd;
    
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
    
    private Laboratorio findID(int id) {
        for (Laboratorio lab : dataLabs) {
            if (lab.getID() == id) {
                return lab;
            }

        }
        return null;
    }
    
    private void getReserveTable() {
        currLab = labList.getSelectionModel().getSelectedItem();
        dataPres = FXCollections.observableList(new PrestamoDAO().getHistory(user, currLab, datePickerInit.getValue().toString(), datePickerEnd.getValue().toString()));
        
        JFXTreeTableColumn<Prestamo, String> TAnadirp = new JFXTreeTableColumn<>("Ver Préstamo");
        TAnadirp.setPrefWidth(95);
        Callback<TreeTableColumn<Prestamo, String>, TreeTableCell<Prestamo, String>> cellFactory
                = //
                (final TreeTableColumn<Prestamo, String> param) -> {
                    final TreeTableCell<Prestamo, String> cell = new TreeTableCell<Prestamo, String>() {

                final JFXButton btn = new JFXButton("Ver Préstamo");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btn.setButtonType(JFXButton.ButtonType.FLAT);
                        btn.setStyle("-fx-background-color: #1a237e; -fx-text-fill: #ffffff;");
                        btn.setOnAction(event -> {
                            pres = this.getTreeTableRow().getItem();
                            goToReview();
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        TAnadirp.setCellFactory(cellFactory);

        historialTable.getColumns().set(4, TAnadirp);

        IDColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        LabColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Lab")
        );
        stateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("estado")
        );
        
        dateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("tiempoReserva")
        );

        TreeItem<Prestamo> root = new RecursiveTreeItem<>(dataPres, RecursiveTreeObject::getChildren);
        
        historialTable.setRoot(root);
        historialTable.setShowRoot(false);

    }
    
    private void goToReview() {
        ToPath("/Frontera/User/UserHistoryReviewUX.fxml");
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLabs();
        if(dataPres != null){
            historialTable.refresh();
        }
    }
    
    @FXML
    private void BackToMenu(ActionEvent event) {
        ToPath("/Frontera/User/UserMenuUX.fxml");
    }
    
    @FXML
    private void makeHistory(ActionEvent event) {
        if(datePickerInit.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Seleccione una fecha de inicio.");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }
        
        if(datePickerEnd.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Seleccione una fecha final.");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }
        
        if(datePickerInit.getValue().isAfter(datePickerEnd.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("La fecha inicial es después de la fecha final.");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }
        
        getReserveTable();
    }
}
