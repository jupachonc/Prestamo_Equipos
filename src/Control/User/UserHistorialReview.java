package Control.User;

import Control.LoginController;
import DAO.PrestamoDAO;
import Entidad.Elemento;
import Entidad.Laboratorio;
import Entidad.Prestamo;
import Entidad.Usuario;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

public class UserHistorialReview implements Initializable {
    private Usuario user = LoginController.getUsuario();
    private Laboratorio currLab;
    private Prestamo pres;
    private ObservableList<Elemento> dataPres;
    
    @FXML
    private JFXButton backToMenu1;
    @FXML
    private JFXTreeTableView<Elemento> elementosTable;
    @FXML
    private TreeTableColumn<Elemento, String> nombreColumn;   
    @FXML
    private TreeTableColumn<Elemento, String> descColumn;
    @FXML
    private TreeTableColumn<Elemento, String> catColumn;
    @FXML
    private Label labLabel, nombreLabel, fechaLabel, reservaLabel;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currLab = UserHistoryController.currLab;
        pres = UserHistoryController.pres;
        updateSideData();
        getReserveTable();
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
    void BackToMenu(ActionEvent event) {
        ToPath("/Frontera/User/UserHistoryUX.fxml");
    }
    
    private void updateSideData(){        
        labLabel.setText(currLab.getNombre());
        nombreLabel.setText("" + pres.getAdminID());
        fechaLabel.setText(pres.getTiempoReserva());
        
        if(pres.getReserveID() != 0){
            reservaLabel.setText("Sí");
        }
        else{
            reservaLabel.setText("No");
        }
    }

    private void getReserveTable() {
        dataPres = FXCollections.observableList(new PrestamoDAO().getHistorySpecific(pres.getID()));

        nombreColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("nombre")
        );
        descColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("descripción")
        );
        catColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("catName")
        );

        TreeItem<Elemento> root = new RecursiveTreeItem<>(dataPres, RecursiveTreeObject::getChildren);
        
        elementosTable.setRoot(root);
        elementosTable.setShowRoot(false);

    }
    
}
