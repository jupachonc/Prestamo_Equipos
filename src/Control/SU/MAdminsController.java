package Control.SU;

import Control.LoginController;
import Control.ValidarRegistro;
import DAO.UsuarioDAO;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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

public class MAdminsController implements Initializable {

    private Usuario user = LoginController.getUsuario();
    private ObservableList<Usuario> data;

    @FXML
    private JFXTreeTableView<Usuario> adminTable;
    @FXML
    private TreeTableColumn<Usuario, String> TNombres;
    @FXML
    private TreeTableColumn<Usuario, String> TApellidos;
    @FXML
    private TreeTableColumn<Usuario, Integer> TDocumento;
    @FXML
    private TreeTableColumn<Usuario, String> TEmail;
    @FXML
    private JFXTextField names;
    @FXML
    private JFXTextField lastnames;
    @FXML
    private JFXTextField document;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField cpassword;
    @FXML
    private JFXButton backbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getAdminTable();
    }

    private void getAdminTable() {
        data = FXCollections.observableList(new UsuarioDAO().getAdmins());

        JFXTreeTableColumn<Usuario, String> settingsColumn = new JFXTreeTableColumn<>("Deshabilitar");
        settingsColumn.setPrefWidth(95);
        Callback<TreeTableColumn<Usuario, String>, TreeTableCell<Usuario, String>> cellFactory
                = //
                (final TreeTableColumn<Usuario, String> param) -> {
                    final TreeTableCell<Usuario, String> cell = new TreeTableCell<Usuario, String>() {

                final JFXButton btn = new JFXButton("Deshabilitar");

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

                            Usuario usr = this.getTreeTableRow().getItem();

                            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION);
                            alertm.setHeaderText(null);
                            alertm.setTitle("Confirmación");
                            alertm.setContentText("Se deshabilitará el usuario " + usr.getEmail());
                            Optional<ButtonType> action = alertm.showAndWait();

                            if (action.get() == ButtonType.OK) {

                                if (new UsuarioDAO().disableAdmin(usr)) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("Administrador deshabilitado");
                                    alert.setContentText(null);
                                    alert.showAndWait();
                                    getAdminTable();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Información");
                                    alert.setHeaderText("No se pudo deshabilitar el Administrador");
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

        adminTable.getColumns().set(4, settingsColumn);

        TNombres.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        TApellidos.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("apellido")
        );
        TEmail.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("email")
        );
        TDocumento.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("documento")
        );

        TreeItem<Usuario> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);

        adminTable.setRoot(root);
        adminTable.setShowRoot(false);
    }

    @FXML
    private void create(ActionEvent event) {
        String respuesta = new ValidarRegistro().verificarRegistro(names.getText(),
                lastnames.getText(), Integer.parseInt(document.getText()), email.getText(),
                password.getText(), cpassword.getText(), 1);
        if (respuesta.equals("Usuario registrado") || respuesta.equals("Usuario reactivado con la contraseña asignada")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(respuesta);
            alert.setContentText(null);
            alert.showAndWait();
            cleanForm();
            getAdminTable();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("Verifica la información");
            alert.setContentText(respuesta);
            alert.showAndWait();
        }

    }

    private void cleanForm() {
        names.setText("");
        lastnames.setText("");
        document.setText("");
        email.setText("");
        password.setText("");
        cpassword.setText("");
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
            Stage stage1 = (Stage) names.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
