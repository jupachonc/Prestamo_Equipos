package DAO;

import Entidad.Categoria;
import Entidad.Elemento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReportesDAO {

    static Connection connection;
    static PreparedStatement statement;

    public static float useHoursE(Elemento elm) {
        float hours = -1;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT ROUND(SUM(TIMESTAMPDIFF(MINUTE , TiempoDeInicio, TiempoDeEntrega))/60, 1 )Horas"
                    + " FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo"
                    + " inner join elemento e on pe.IDElemento = e.ID"
                    + " where e.ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, elm.getID());
            ResultSet resultset = statement.executeQuery();
            resultset.beforeFirst();
            resultset.next();
            hours = resultset.getFloat("Horas");

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            close();
        }
        return hours;
    }

    public static float useHoursE(Elemento elm, LocalDateTime init, LocalDateTime fin) {
        float hours = -1;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT ROUND(SUM(TIMESTAMPDIFF(MINUTE , TiempoDeInicio, TiempoDeEntrega))/60, 1 )Horas"
                    + " FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo"
                    + " inner join elemento e on pe.IDElemento = e.ID"
                    + " where e.ID = ? AND TiempoDeInicio > ? AND TiempoDeEntrega < ?;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, elm.getID());
            statement.setTimestamp(2, Timestamp.valueOf(init));
            statement.setTimestamp(3, Timestamp.valueOf(fin));
            ResultSet resultset = statement.executeQuery();
            resultset.beforeFirst();
            resultset.next();
            hours = resultset.getFloat("Horas");

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            close();
        }
        return hours;
    }

    public static int useTimesE(Elemento elm) {
        int count = -1;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT COUNT(IDPrestamo) COUNT FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo\n"
                    + " inner join elemento e on pe.IDElemento = e.ID"
                    + " where e.ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, elm.getID());
            ResultSet resultset = statement.executeQuery();
            resultset.beforeFirst();
            resultset.next();
            count = resultset.getInt("COUNT");

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            close();
        }
        return count;
    }

    public static int useTimesE(Elemento elm, LocalDateTime init, LocalDateTime fin) {
        int count = -1;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT COUNT(IDPrestamo) COUNT FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo\n"
                    + " inner join elemento e on pe.IDElemento = e.ID"
                    + " where e.ID = ? AND TiempoDeInicio > ? AND TiempoDeEntrega < ?;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, elm.getID());
            statement.setTimestamp(2, Timestamp.valueOf(init));
            statement.setTimestamp(3, Timestamp.valueOf(fin));
            ResultSet resultset = statement.executeQuery();
            resultset.beforeFirst();
            resultset.next();
            count = resultset.getInt("COUNT");

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            close();
        }
        return count;
    }

    public static ResultSet getPrestamosE(Elemento elm) {
        ResultSet resultset = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT prestamo.ID ID, IDEstudiante, concat_ws(\" \",e2.Nombre, e2.Apellido ) NombreEstudiante, e2.Email Email,\n"
                    + "concat_ws(\" \", a.Nombre, a.Apellido) Administrador, TiempoDeInicio, TiempoDeEntrega,\n"
                    + "       TIMESTAMPDIFF(MINUTE , TiempoDeInicio, TiempoDeEntrega) TiempoUso\n"
                    + "FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo\n"
                    + "    inner join elemento e on pe.IDElemento = e.ID\n"
                    + "    inner join estudiante e2 on prestamo.IDEstudiante = e2.Documento\n"
                    + "    inner join administrador a on prestamo.AdministradorDocumento = a.Documento\n"
                    + "    where e.ID = ?;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, elm.getID());
            resultset = statement.executeQuery();
            resultset.beforeFirst();

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {

        }
        return resultset;
    }

    public static ResultSet getPrestamosE(Elemento elm, LocalDateTime init, LocalDateTime fin) {
        ResultSet resultset = null;
        PreparedStatement pstatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT prestamo.ID ID, IDEstudiante, concat_ws(\" \",e2.Nombre, e2.Apellido ) NombreEstudiante, e2.Email Email,\n"
                    + "concat_ws(\" \", a.Nombre, a.Apellido) Administrador, TiempoDeInicio, TiempoDeEntrega,\n"
                    + "       TIMESTAMPDIFF(MINUTE , TiempoDeInicio, TiempoDeEntrega) TiempoUso\n"
                    + "FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo\n"
                    + "    inner join elemento e on pe.IDElemento = e.ID\n"
                    + "    inner join estudiante e2 on prestamo.IDEstudiante = e2.Documento\n"
                    + "    inner join administrador a on prestamo.AdministradorDocumento = a.Documento\n"
                    + "    where e.ID = ? AND TiempoDeInicio > ? AND TiempoDeEntrega < ?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, elm.getID());
            pstatement.setTimestamp(2, Timestamp.valueOf(init));
            pstatement.setTimestamp(3, Timestamp.valueOf(fin));
            resultset = pstatement.executeQuery();
            resultset.beforeFirst();

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {

        }
        return resultset;
    }

    public static ResultSet getPrestamosC(Categoria cat) {
        ResultSet resultset = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT prestamo.ID ID, e.ID IDElemento, e.Nombre NombreElemento, IDEstudiante, concat_ws(\" \",e2.Nombre, e2.Apellido ) NombreEstudiante, e2.Email Email,\n"
                    + "concat_ws(\" \", a.Nombre, a.Apellido) Administrador, TiempoDeInicio, TiempoDeEntrega,\n"
                    + "       TIMESTAMPDIFF(MINUTE , TiempoDeInicio, TiempoDeEntrega) TiempoUso FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo\n"
                    + "inner join elemento e on pe.IDElemento = e.ID inner join categoria c on e.CategoriaID = c.ID\n"
                    + "inner join estudiante e2 on prestamo.IDEstudiante = e2.Documento\n"
                    + "inner join administrador a on prestamo.AdministradorDocumento = a.Documento\n"
                    + "WHERE CategoriaID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, cat.getID());
            resultset = statement.executeQuery();
            resultset.beforeFirst();

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {

        }
        return resultset;
    }

    public static ResultSet getPrestamosC(Categoria cat, LocalDateTime init, LocalDateTime fin) {
        ResultSet resultset = null;
        PreparedStatement pstatement = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT prestamo.ID ID, e.ID IDElemento, e.Nombre NombreElemento, IDEstudiante, concat_ws(\" \",e2.Nombre, e2.Apellido ) NombreEstudiante, e2.Email Email,\n"
                    + "concat_ws(\" \", a.Nombre, a.Apellido) Administrador, TiempoDeInicio, TiempoDeEntrega,\n"
                    + "       TIMESTAMPDIFF(MINUTE , TiempoDeInicio, TiempoDeEntrega) TiempoUso FROM prestamo inner join prestamo_elemento pe on prestamo.ID = pe.IDPrestamo\n"
                    + "inner join elemento e on pe.IDElemento = e.ID inner join categoria c on e.CategoriaID = c.ID\n"
                    + "inner join estudiante e2 on prestamo.IDEstudiante = e2.Documento\n"
                    + "inner join administrador a on prestamo.AdministradorDocumento = a.Documento"
                    + "    where e.ID = ? AND TiempoDeInicio > ? AND TiempoDeEntrega < ?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, cat.getID());
            pstatement.setTimestamp(2, Timestamp.valueOf(init));
            pstatement.setTimestamp(3, Timestamp.valueOf(fin));
            resultset = pstatement.executeQuery();
            resultset.beforeFirst();

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {

        }
        return resultset;
    }

    public static void close() {
        try {
            System.out.println("cerrando statement...");
            statement.close();
            System.out.println("cerrando conexión...");
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        }

    }

}
