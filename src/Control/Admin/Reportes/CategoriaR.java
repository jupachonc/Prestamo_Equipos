package Control.Admin.Reportes;

import Control.Admin.AdminMenuController;
import Control.Admin.AdminReportes;
import DAO.LaboratorioDAO;
import DAO.ReportesDAO;
import Entidad.Categoria;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CategoriaR implements Initializable {

    private Laboratorio lab = AdminMenuController.currentLab;
    private ObservableList<MacroCategoria> dataMCats;
    private ObservableList<Categoria> dataCats;

    @FXML
    private JFXDatePicker fInicio;
    @FXML
    private JFXDatePicker fFin;
    @FXML
    private JFXButton btnRH;
    @FXML
    private JFXComboBox<MacroCategoria> macroLista;
    @FXML
    private JFXTreeTableView<Categoria> cTable;
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
        MacroCategoria Mcat = macroLista.getSelectionModel().getSelectedItem();
        dataCats = FXCollections.observableList(new LaboratorioDAO().getCats(Mcat));

        idColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("ID")
        );
        nombreColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory<>("Nombre")
        );

        TreeItem<Categoria> root = new RecursiveTreeItem<>(dataCats, RecursiveTreeObject::getChildren);

        cTable.setRoot(root);
        cTable.setShowRoot(false);

    }

    private boolean doReport(Categoria cat, LocalDateTime in, LocalDateTime fi) throws SQLException {
        try {
            int filaidx = 0;

            //Creación Libro y hoja
            Workbook workbook = new XSSFWorkbook();
            Sheet pagina = workbook.createSheet("Reporte");

            //Titulo
            pagina.addMergedRegion(new CellRangeAddress(filaidx, filaidx, 0, 9));
            Row titleF = pagina.createRow(filaidx);
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 20);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setFont(titleFont);
            Cell title = titleF.createCell(0);
            title.setCellStyle(titleStyle);
            title.setCellValue("Préstamos Categoría " + cat.getID() + "-" + cat.getNombre());
            filaidx++;

            //Subtitulo
            pagina.addMergedRegion(new CellRangeAddress(filaidx, filaidx, 0, 9));
            Row fila = pagina.createRow(filaidx);
            CellStyle subtitleStyle = workbook.createCellStyle();
            Font subtitleFont = workbook.createFont();
            subtitleFont.setFontHeightInPoints((short) 14);
            subtitleStyle.setAlignment(HorizontalAlignment.CENTER);
            subtitleStyle.setFont(subtitleFont);
            Cell subtitle = fila.createCell(0);
            subtitle.setCellStyle(subtitleStyle);
            if (in == null && fi == null) {
                subtitle.setCellValue(ReportesDAO.useHoursC(cat) + " horas de uso total - "
                        + ReportesDAO.useTimesC(cat) + " Préstamos");
            } else {
                subtitle.setCellValue(ReportesDAO.useHoursC(cat, in, fi) + " horas de uso desde "
                        + in.toLocalDate() + " hasta " + fi.toLocalDate() + " - "
                        + ReportesDAO.useTimesC(cat, in, fi) + " Préstamos");
            }
            filaidx++;
            
            //Encabezados
            String[] titulos = {"ID", "ID Elemento", "Nombre Elemento", "IDEstudiante",
                "Nombre Estudiante", "E-Mail", "Administrador", "Tiempo Inicio",
                "Tiempo Entrega", "Tiempo Uso (Min)"};

            Font newFont = workbook.createFont();
            newFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFont(newFont);

            fila = pagina.createRow(filaidx);

            for (int i = 0; i < titulos.length; i++) {
                // Creamos una celda en esa fila, en la posicion 
                // indicada por el contador del ciclo
                Cell celda = fila.createCell(i);

                // Indicamos el estilo que deseamos 
                // usar en la celda, en este caso el unico 
                // que hemos creado
                celda.setCellStyle(style);
                celda.setCellValue(titulos[i]);
            }

            //Data
            ResultSet rs;
            if (in == null && fi == null) {
                rs = ReportesDAO.getPrestamosC(cat);
            } else {
                rs = ReportesDAO.getPrestamosC(cat, in, fi);
            }

            while (rs.next()) {
                filaidx++;
                fila = pagina.createRow(filaidx);
                Cell celda;
                int ci = 0;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getInt("ID"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getInt("IDElemento"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("NombreElemento"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getInt("IDEstudiante"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("NombreEstudiante"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("Email"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("Administrador"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("TiempoDeInicio"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("TiempoDeEntrega"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getInt("TiempoUso"));

            }

            ReportesDAO.close();
            
            //Autosize Columns
            for (int i = 0; i < titulos.length; i++) {
                pagina.autoSizeColumn(i);
            }

            //Save file
            AdminReportes.save(workbook, "Reporte_Cat_" + cat.getID() + "_" + LocalDate.now());
            return true;

        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }

    }

    @FXML
    void onSaveDates(ActionEvent event) throws SQLException {
                if (cTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No ha seleccionado una categoría");
            alert.setContentText("Para generar un reporte primero seleccione una categoría");
            alert.showAndWait();
            return;
        } else if (fInicio.getValue() == null || fFin.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No ha seleccionado un intervalo válido");
            alert.setContentText("Para generar un reporte primero debe seleccionar un intervalo válido");
            alert.showAndWait();
            return;
        }
        LocalDateTime initialDate = fInicio.getValue().atTime(0, 0);
        LocalDateTime endDate = fFin.getValue().atTime(23, 59);
        if (endDate.isBefore(initialDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("La fecha final no puede ser antes de la inicial");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }

        Categoria cat = cTable.getSelectionModel().getSelectedItem().getValue();
        
        doReport(cat, initialDate,endDate);

    }

    @FXML
    void onSaveHistory(ActionEvent event) throws SQLException {
        if (cTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Información");
            alert.setHeaderText("No ha seleccionado una categoría");
            alert.setContentText("Para generar un reporte primero seleccione una categoría");
            alert.showAndWait();
            return;
        }

        Categoria cat = cTable.getSelectionModel().getSelectedItem().getValue();
        System.out.println("Do report");
        doReport(cat, null, null);
    }

}
