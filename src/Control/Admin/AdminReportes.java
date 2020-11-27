/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin;

import com.jfoenix.controls.JFXButton;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class AdminReportes implements Initializable {

    @FXML
    private JFXButton volver;
    @FXML
    private AnchorPane mainPane;
    private static final Logger LOGGER = Logger.getLogger("Control.Admin.Reportes.Elemento");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void BackToMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Frontera/Admin/AdminMenuUX.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
            Stage stage1 = (Stage) volver.getScene().getWindow();
            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void elementoReport(ActionEvent event) {
        try {
            AnchorPane newpane = FXMLLoader.load(getClass().getResource("/Frontera/Admin/Reportes/ElementoR.fxml"));
            mainPane.getChildren().clear();
            mainPane.getChildren().add(newpane);
        } catch (IOException ex) {
            Logger.getLogger(AdminReportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void catReport(ActionEvent event) {
        try {
            AnchorPane newpane = FXMLLoader.load(getClass().getResource("/Frontera/Admin/Reportes/LabR.fxml"));
            mainPane.getChildren().clear();
            mainPane.getChildren().add(newpane);
        } catch (IOException ex) {
            Logger.getLogger(AdminReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void save(Workbook workbook, String filename) {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Libro de Excel", "*.xlsx"));
        fileChooser.setInitialFileName(filename);
        File file = fileChooser.showSaveDialog(stage);

        try {
            // Creamos el flujo de salida de datos,
            // apuntando al archivo donde queremos 
            // almacenar el libro de Excel
            FileOutputStream salida = new FileOutputStream(file);

            // Almacenamos el libro de 
            // Excel via ese 
            // flujo de datos
            workbook.write(salida);

            // Cerramos el libro para concluir operaciones
            workbook.close();

            LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}", file.getAbsolutePath());

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de archivos");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error de entrada/salida");
        }

        System.out.println(file.getAbsolutePath());

    }

}
