package Control.Admin;

import static Control.Admin.AdminPrestamo.isNumeric;
import DAO.PrestamoDAO;
import DAO.UsuarioDAO;
import Entidad.Elemento;
import Entidad.Prestamo;
import Entidad.Usuario;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
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

public class AdminPrestamoUser implements Initializable {

    private Usuario estudiante;
    private ObservableList<Elemento> elementos = FXCollections.observableArrayList();
    private ObservableList<String> options = FXCollections.observableArrayList();
    private int[] devstates;

    @FXML
    private JFXTreeTableView<Elemento> devoTable;
    @FXML
    private JFXTextArea observText;
    @FXML
    private TreeTableColumn<Elemento, Integer> idColumn;
    @FXML
    private TreeTableColumn<Elemento, String> namePrestamo;
    @FXML
    private TreeTableColumn<Elemento, String> descColumn;
    @FXML
    private Label Name;
    @FXML
    private Label LastName;
    @FXML
    private Label Document;
    @FXML
    private JFXTextField DocumentN;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOptions();
    }

    @FXML
    private void searchDocument(ActionEvent event) {
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
            getElementos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("El documento es inválido");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    private void getElementos() {
        Prestamo prst = new PrestamoDAO().getPrestamo(estudiante);

        if (prst == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Este usuario no tiene préstamos activos");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }

        elementos = FXCollections.observableList(new PrestamoDAO().getHistorySpecific(prst.getID()));
        
        devstates = new int[elementos.size()];

        JFXTreeTableColumn<Elemento, String> settingsColumn = new JFXTreeTableColumn<>("Estado del Elemento");
        settingsColumn.setPrefWidth(173);
        Callback<TreeTableColumn<Elemento, String>, TreeTableCell<Elemento, String>> cellFactory
                = //
                (final TreeTableColumn<Elemento, String> param) -> {
                    final TreeTableCell<Elemento, String> cell = new TreeTableCell<Elemento, String>() {

                final JFXComboBox opts = new JFXComboBox(options);

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        opts.setStyle("-jfx-label-float: true;");
                        opts.getSelectionModel().getSelectedIndex();
                        setGraphic(opts);
                        opts.setOnAction(e -> {
                            int idx = this.getTreeTableRow().getIndex();
                            JFXComboBox cb = (JFXComboBox) opts;
                            devstates[idx] = cb.getSelectionModel().getSelectedIndex();
                            System.out.println(devstates[idx]);
                        });
                        setText(null);

                    }
                }
            };
                    return cell;
                };

        settingsColumn.setCellFactory(cellFactory);

        devoTable.getColumns().set(3, settingsColumn);

        idColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );

        namePrestamo.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        descColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("descripción")
        );

        TreeItem<Elemento> root = new RecursiveTreeItem<>(elementos, RecursiveTreeObject::getChildren);

        devoTable.setRoot(root);
        devoTable.setShowRoot(false);

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

    private void loadOptions() {
        options.add("Devolución Completa");
        options.add("Devolución Incompleta");
        options.add("Devolución con Daño");
    }

    @FXML
    private void makeDevolution(ActionEvent event) {

        if (elementos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Este usuario no tiene préstamos activos");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }

//        for (int i = 0; i < elementos.size(); i++) {
//            System.out.println(devoTable.g);
////            int idx = cmb.getSelectionModel().getSelectedIndex();
////            System.out.println(idx);
//
//        }

//        if(prestamoReserva >= 0){
//            if(new PrestamoDAO().makeDevolution(reservas.get(0).getID(), prestamoReserva + 1, commens)){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Información");
//                alert.setHeaderText("Devolución Completa");
//                alert.setContentText(null);
//                alert.showAndWait();
//            }
//            else{
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Información");
//                alert.setHeaderText("Ha ocurrido un error al actualizar el préstamo.");
//                alert.setContentText(null);
//                alert.showAndWait();
//            }
//        }
    }
}
