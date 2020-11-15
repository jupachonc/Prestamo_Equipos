package Control.Admin;

import Control.LoginController;
import DAO.LaboratorioDAO;
import Entidad.Elemento;
import Entidad.Laboratorio;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.text.MessageFormat;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AdminMenuController implements Initializable {

    private Usuario user = LoginController.getUsuario();
    private ObservableList<Laboratorio> dataLabs;
    private ObservableList<Elemento> dataPrestamo;
    public static Laboratorio currentLab = null;

    @FXML
    private Label labelname;
    @FXML
    private JFXButton logoutbtn;
    @FXML
    private JFXComboBox<Laboratorio> labList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateUser();
        getLabs();
    }

    public void updateUser() {
        labelname.setText(MessageFormat.format("Está registrado como {0} {1}", user.getNombre(), user.getApellido()));

    }

    private void getLabs() {
        dataLabs = FXCollections.observableList(new LaboratorioDAO().getLabsperAdmin(user));

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

    private void ToPath(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) logoutbtn.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SafeRoute(String path) {
        if (currentLab != null) {
            ToPath(path);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No se ha seleccionado un laboratorio");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    @FXML
    private void Logout(ActionEvent event) {
        ToPath("/Frontera/LoginUX.fxml");
    }

    @FXML
    private void gotoInventary(ActionEvent event) {
        AdminPrestamo.flag = true;
        SafeRoute("/Frontera/Admin/AdminInventarioUX.fxml");
    }

    @FXML
    private void Config(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/UserConfigUX.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void onSelectItem(ActionEvent event) {
        currentLab = labList.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void goToPrestamos(ActionEvent event) {
        SafeRoute("/Frontera/Admin/AdminPrestamoUX.fxml");
    }

}
