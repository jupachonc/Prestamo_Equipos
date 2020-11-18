package DAO;

import Entidad.Categoria;
import Entidad.Elemento;
import Entidad.Reserva;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class PrestamoDAO {

    Connection connection;
    Statement statement;

    public int doPrestamo(ArrayList<Elemento> elements, Usuario usr, Usuario admin, String obs, int reserve) {
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql;
            if (reserve == 0) {
                sql = "INSERT INTO prestamo (IDEstudiante, EstadoPrestamo, Comentarios, TiempoDeInicio, TiempoDeEntrega, ReservasID, AdministradorDocumento) "
                        + "VALUES (" + usr.getDocumento() + ", 0, "
                        + "\"Obsernaciones Préstamo:\n" + obs + "\",\"" + new Timestamp(new Date().getTime()) + "\", null, null"
                        + "," + admin.getDocumento() + ");";

            } else {
                sql = "INSERT INTO prestamo (IDEstudiante, EstadoPrestamo, Comentarios, TiempoDeInicio, TiempoDeEntrega, ReservasID, AdministradorDocumento) "
                        + "VALUES (" + usr.getDocumento() + ", 0, "
                        + "\"Obsernaciones Préstamo:\n" + obs + "\",\"" + new Timestamp(new Date().getTime()) + "\", null,"
                        + reserve + "," + admin.getDocumento() + ");";
            }
            statement.executeUpdate(sql);
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() as ID");
            resultSet.next();
            id = resultSet.getInt("ID");

            for (Elemento e : elements) {
                String sql_ = "INSERT INTO prestamo_elemento (IDPrestamo, IDElemento) VALUES (" + id + "," + e.getID() + ");";
                statement.executeUpdate(sql_);
                String sql2 = "UPDATE elemento SET EstadoElemento = 2 WHERE ID =" + e.getID();
                statement.executeUpdate(sql2);
            }

            if (reserve != 0) {
                String sql3 = "UPDATE reservas SET EstadoReserva = 1 WHERE ID =" + reserve;
                statement.executeQuery(sql3);
            }

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        return id;

    }
    
    public ArrayList<Reserva> getPrestamos(Usuario est) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM estudiante, prestamo "
                       + "WHERE IDEstudiante = Documento and EstadoPrestamo = 0 AND Documento = " + est.getDocumento() + " AND DATE(TiempoDeInicio) = curdate();";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                reservas.add( new Reserva(resultSet.getInt("ID"), resultSet.getInt("EstadoReserva"), resultSet.getTimestamp("TiempoDeReserva")) );
            }
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        return reservas;

    }
    
    public boolean makeDevolution(int ID, int state, String comms){
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("SELECT Comentarios FROM prestamo WHERE ID = " + ID + ";");
            resultSet.next();
            String coment = resultSet.getString("Comentarios");
            coment = coment + " " + comms;
            
            
            String sql = "UPDATE prestamo SET EstadoPrestamo = " + state + ", Comentarios = " + coment  + ", TiempoDeEntrega = " + new Timestamp(new Date().getTime())
                       + " WHERE ID =" + ID;
            statement.executeQuery(sql);
            return true;
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        return false;
    }
}
