package DAO;

import Entidad.Elemento;
import Entidad.Laboratorio;
import Entidad.Prestamo;
import Entidad.Reserva;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class PrestamoDAO {
    
    Connection connection;
    Statement statement;

    public int doPrestamo(ArrayList<Elemento> elements, Usuario usr, Usuario admin, String obs, int reserve, int labID) {
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql;
            if (reserve == 0) {
                sql = "INSERT INTO prestamo (IDEstudiante, EstadoPrestamo, Comentarios, TiempoDeInicio, TiempoDeEntrega, ReservasID, AdministradorDocumento, LaboratorioID) "
                        + "VALUES (" + usr.getDocumento() + ", 0, "
                        + "\"Observaciones Préstamo:\n" + obs + "\",\"" + new Timestamp(new Date().getTime()) + "\", null, null"
                        + "," + admin.getDocumento() + ", " + labID + ");";

            } else {
                sql = "INSERT INTO prestamo (IDEstudiante, EstadoPrestamo, Comentarios, TiempoDeInicio, TiempoDeEntrega, ReservasID, AdministradorDocumento, LaboratorioID) "
                        + "VALUES (" + usr.getDocumento() + ", 0, "
                        + "\"Observaciones Préstamo:\n" + obs + "\",\"" + new Timestamp(new Date().getTime()) + "\", null,"
                        + reserve + "," + admin.getDocumento() + ", " + labID + ");";
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
            String sql = "SELECT * FROM prestamo "
                       + "WHERE EstadoPrestamo = 0 AND IDEstudiante = " + est.getDocumento() + " AND DATE(TiempoDeInicio) = curdate();";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                reservas.add( new Reserva(resultSet.getInt("ID"), resultSet.getInt("EstadoPrestamo"), resultSet.getTimestamp("TiempoDeInicio")) );
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
    
    public ArrayList<Prestamo> getHistory(Usuario est, Laboratorio lab, String fechaInicio, String fechaFinal) {
         
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
           
            String sql;
            
            /*
            sql = "SELECT * FROM reservas WHERE DATE(TiempoDeReserva) = curdate() AND EstudianteDocumento = " + est.getDocumento() + " AND EstadoReserva = 0;";
            
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                prestamos.add(new Prestamo(resultSet.getInt("ID"), 0, -1, resultSet.getString("TiempodeReserva"), "", "Reserva"));
            }
            */
            
            if(lab == null){
                sql = "SELECT * FROM prestamo, laboratorio WHERE IDEstudiante = " + est.getDocumento()
                       + " AND TiempoDeInicio >= \"" + fechaInicio + "\" AND TiempoDeInicio <= \"" + fechaFinal
                       + "\" AND LaboratorioID = laboratorio.ID;";
            }
            else{
                sql = "SELECT * FROM prestamo, laboratorio WHERE IDEstudiante = " + est.getDocumento()
                       + " AND TiempoDeInicio >= \"" + fechaInicio + "\" AND TiempoDeInicio <= \"" + fechaFinal
                       + "\" AND LaboratorioID = " + lab.getID() + " AND LaboratorioID = laboratorio.ID;";
            }
            
            
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                prestamos.add(new Prestamo(resultSet.getInt("ID"), resultSet.getInt("AdministradorDocumento"), resultSet.getInt("ReservasID"),
                                           resultSet.getInt("EstadoPrestamo"), resultSet.getString("TiempodeInicio"), resultSet.getString("Comentarios"),
                                           resultSet.getString("Nombre")));
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
        return prestamos;

    }
    
    public ArrayList<Elemento> getHistorySpecific(int ID) {
         
        ArrayList<Elemento> prestamos = new ArrayList<>();
        ResultSet resultSet;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            
            String sql;
            sql = "SELECT * FROM prestamo_elemento, elemento, categoria WHERE IDElemento = elemento.ID "
                + "AND CategoriaID = categoria.ID AND IDPrestamo = " + ID + ";";
            
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                Elemento iterator = new Elemento(resultSet.getInt("elemento.ID"), resultSet.getString("elemento.Nombre"),
                                                 resultSet.getString("elemento.Descripción"), resultSet.getInt("EstadoElemento"));
                iterator.setCatName(resultSet.getString("categoria.Nombre"));
                prestamos.add(iterator);
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
        return prestamos;

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
            
            
            String sql = "UPDATE prestamo SET EstadoPrestamo = " + state + ", Comentarios = \"" + coment  + "\", TiempoDeEntrega = \"" + new Timestamp(new Date().getTime())
                       + "\" WHERE ID =" + ID + ";";
            statement.executeUpdate(sql);
            
            sql = "UPDATE prestamo_elemento, elemento SET EstadoElemento = " + state + " WHERE IDElemento = ID AND IDPrestamo = " + ID + ";";
            statement.executeUpdate(sql);
            
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
