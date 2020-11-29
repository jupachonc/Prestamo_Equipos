/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Admin.Reportes;

import Control.Admin.AdminMenuController;
import Control.Admin.AdminReportes;
import DAO.ReportesDAO;
import Entidad.Categoria;
import Entidad.Laboratorio;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class LabR implements Initializable {

    private Laboratorio lab = AdminMenuController.currentLab;

    @FXML
    private JFXDatePicker fInicio;
    @FXML
    private JFXDatePicker fFin;
    @FXML
    private JFXButton btnRH;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onCurrentInventary(ActionEvent event) {
    }

    private boolean doReportL(Laboratorio lab, LocalDateTime in, LocalDateTime fi) throws SQLException {
        try {
            int filaidx = 0;

            //Creación Libro y hoja
            Workbook workbook = new XSSFWorkbook();
            Sheet pagina = workbook.createSheet("Reporte");

            //Titulo
            pagina.addMergedRegion(new CellRangeAddress(filaidx, filaidx, 0, 11));
            Row titleF = pagina.createRow(filaidx);
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 20);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setFont(titleFont);
            Cell title = titleF.createCell(0);
            title.setCellStyle(titleStyle);
            title.setCellValue("Préstamos Laboratorio " + lab.getID() + "-" + lab.getNombre());
            filaidx++;

            //Subtitulo
            pagina.addMergedRegion(new CellRangeAddress(filaidx, filaidx, 0, 11));
            Row fila = pagina.createRow(filaidx);
            CellStyle subtitleStyle = workbook.createCellStyle();
            Font subtitleFont = workbook.createFont();
            subtitleFont.setFontHeightInPoints((short) 14);
            subtitleStyle.setAlignment(HorizontalAlignment.CENTER);
            subtitleStyle.setFont(subtitleFont);
            Cell subtitle = fila.createCell(0);
            subtitle.setCellStyle(subtitleStyle);
            if (in == null && fi == null) {
                subtitle.setCellValue(ReportesDAO.useHoursL(lab) + " horas de uso de elementos en total - "
                        + ReportesDAO.useTimesL(lab) + " Préstamos");
            } else {
                subtitle.setCellValue(ReportesDAO.useHoursL(lab, in, fi) + " horas de uso de elementos desde "
                        + in.toLocalDate() + " hasta " + fi.toLocalDate() + " - "
                        + ReportesDAO.useTimesL(lab, in, fi) + " Préstamos");
            }
            filaidx++;

            //Encabezados
            String[] titulos = {"ID", "ID Elemento", "Nombre Elemento", "Categoría", 
                "Macrocategoría","IDEstudiante", "Nombre Estudiante", "E-Mail",
                "Administrador", "Tiempo Inicio", "Tiempo Entrega", "Tiempo Uso (Min)"};

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
                rs = ReportesDAO.getPrestamosL(lab);
            } else {
                rs = ReportesDAO.getPrestamosL(lab, in, fi);
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
                celda.setCellValue(rs.getString("Categoria"));
                ci++;
                celda = fila.createCell(ci);
                celda.setCellValue(rs.getString("Macrocategoria"));
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
            AdminReportes.save(workbook, "Reporte_Lab_" + lab.getID() + "_" + LocalDate.now());
            return true;

        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }

    }

    @FXML
    private void onSaveDates(ActionEvent event) throws SQLException {
        if (fInicio.getValue() == null || fFin.getValue() == null) {
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
        
        doReportL(lab, initialDate, endDate);
    }

    @FXML
    private void onSaveHistory(ActionEvent event) throws SQLException {
        doReportL(lab, null, null);
    }

}
