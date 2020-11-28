package Control.Admin.Reportes;

import Control.Admin.AdminMenuController;
import DAO.LaboratorioDAO;
import Entidad.Categoria;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.util.StringConverter;

public class CategoriaR implements Initializable {

    private Laboratorio lab = AdminMenuController.currentLab;
    private ObservableList<MacroCategoria> dataMCats;

    @FXML
    private JFXDatePicker fInicio;
    @FXML
    private JFXDatePicker fFin;
    @FXML
    private JFXButton btnRH;
    @FXML
    private JFXComboBox<MacroCategoria> macroLista;
    @FXML
    private JFXTreeTableView<Categoria> eTable;
    @FXML
    private TreeTableColumn<Categoria, Integer> idColumn;
    @FXML
    private TreeTableColumn<Categoria, String> nombreColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getMacro();
    }

    private void getMacro() {

        dataMCats = FXCollections.observableList(new LaboratorioDAO().getMCats(lab.getID()));

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

    private MacroCategoria findID(int id) {
        for (MacroCategoria MCat : dataMCats) {
            if (MCat.getID() == id) {
                return MCat;
            }

        }
        return null;
    }

    @FXML
    void onMacro(ActionEvent event) {

    }

    @FXML
    void onSaveDates(ActionEvent event) {

    }

    @FXML
    void onSaveHistory(ActionEvent event) {

    }

}
