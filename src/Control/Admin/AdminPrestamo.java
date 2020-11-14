/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import DAO.LaboratorioDAO;
import DAO.UsuarioDAO;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
import Entidad.Usuario;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AdminPrestamo implements Initializable {

    private Usuario estudiante = null;
    private Laboratorio lab = AdminMenuController.currentLab;
    private ObservableList<MacroCategoria> dataMCats;

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<?> BusquedaTable;

    @FXML
    private TreeTableColumn<?, ?> TIDL;

    @FXML
    private TreeTableColumn<?, ?> TElementoL;

    @FXML
    private TreeTableColumn<?, ?> TAnadirL;

    @FXML
    private JFXTreeTableView<?> PrestamoTable;

    @FXML
    private TreeTableColumn<?, ?> TIDR;

    @FXML
    private TreeTableColumn<?, ?> TNombreR;

    @FXML
    private TreeTableColumn<?, ?> TQuitarR;

    @FXML
    private JFXTextArea Observaciones;

    @FXML
    private JFXComboBox<MacroCategoria> macroList;

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
        getMCats();
    }

    @FXML
    void BackToMenu(ActionEvent event) {

    }

    @FXML
    void Prestamo(ActionEvent event) {

    }

    @FXML
    void goToReservas(ActionEvent event) {

    }

    @FXML
    void searchDocument(ActionEvent event) {
        if (isNumeric(DocumentN.getText())) {
            System.out.println(Integer.parseInt(DocumentN.getText()));
            estudiante = new UsuarioDAO().getUser(Integer.parseInt(DocumentN.getText()));
            updateUser();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("El documento es inválido");
            alert.setContentText(null);
            alert.showAndWait();
        }

    }

    private void updateUser() {
        Name.setText(estudiante.getNombre());
        LastName.setText(estudiante.getApellido());
        Document.setText(String.valueOf(estudiante.getDocumento()));
    }

    private void getMCats() {
        dataMCats = FXCollections.observableList(new LaboratorioDAO().getMCats(lab.getID()));

        macroList.setItems(dataMCats);
        macroList.setConverter(new StringConverter<MacroCategoria>() {
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

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

}
