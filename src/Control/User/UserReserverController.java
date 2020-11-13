package Control.User;

import Control.LoginController;
import DAO.LaboratorioDAO;
import Entidad.Laboratorio;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class UserReserverController implements Initializable{
    private Usuario user = LoginController.getUsuario();
    private ObservableList<Laboratorio> dataLabs;
    
    
    @FXML
    private JFXComboBox<Laboratorio> labList;
    @FXML
    private JFXButton backToMenu1;
    @FXML
    private JFXButton reserveButton;
    @FXML
    private JFXButton reserveButton1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLabs();
    }
    
    private Laboratorio findID(int id) {
        for (Laboratorio lab : dataLabs) {
            if (lab.getID() == id) {
                return lab;
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
}
