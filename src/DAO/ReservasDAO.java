package DAO;

import Entidad.Categoria;
import Entidad.Reserva;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;

public class ReservasDAO {
    Connection connection;
    Statement statement;
    
    public ArrayList<Categoria> getReserve(int id) {
        ArrayList<Categoria> cats = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM reservas, categoria_reservas, categoria "
                       + "WHERE ReservasID = reservas.ID AND CategoriaID = categoria.ID AND ReservasID = " + id + ";";

            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                cats.add(new Categoria(resultSet.getInt("categoria.ID"), resultSet.getInt("CantidadMAX"), 0,
                                       resultSet.getString("Nombre"), resultSet.getString("Descripción")));
            }
        }
        catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        }
        finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            }
            catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        
        return cats;

    }
    
    public ArrayList<Reserva> getReservasUser(Usuario user){
        ArrayList<Reserva> reservas = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM reservas, estudiante WHERE EstudianteDocumento=Documento and EstadoReserva = 0 AND Documento =" + user.getDocumento() + " AND DATE(TiempoDeReserva) = curdate();";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                reservas.add(new Reserva(resultSet.getInt("ID"),resultSet.getInt("EstadoReserva"), 
                        resultSet.getTimestamp("TiempoDeReserva")));

            }
        }
        catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        }
        finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            }
            catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        
        return reservas;
    }
    
    public boolean checkReservasUser(Usuario user){
        ArrayList<Reserva> reservas = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM reservas, categoria_reservas WHERE EstadoReserva = 0 AND EstudianteDocumento = " +  user.getDocumento()+ " AND ID = ReservasID;";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            
            while (resultSet.next()) {
                reservas.add(new Reserva(resultSet.getInt("ID"),resultSet.getInt("EstadoReserva"), 
                        resultSet.getTimestamp("TiempoDeReserva")));

            }
            
            Date date = new Date();
            Timestamp nw = new Timestamp(date.getTime());
            date.setHours(0);
            date.setMinutes(0);
            Timestamp ts = new Timestamp(date.getTime());
            
            for(Reserva rv : reservas) {
                if (rv.getTiempoReserva().after(ts) && rv.getTiempoReserva().before(nw)) {
                    return false;
                }
            }
            
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        }
        finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            }
            catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        
        return false;
    }
    
    public boolean makeReserve(ObservableList<Categoria> cats, int LabID, int userID) {
        ResultSet resultSet = null;
        int id;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            
            String sql = "INSERT INTO reservas (EstadoReserva, TiempoDeReserva, EstudianteDocumento) "
                        + "VALUES ( 0,\"" + new Timestamp(new Date().getTime()) + "\"," + userID + ");";
            statement.executeUpdate(sql);
            
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() as ID");
            resultSet.next();
            id = resultSet.getInt("ID");

            
            for(Categoria e : cats) {
                String sql_ = "INSERT INTO categoria_reservas (CategoriaID, ReservasID, Cantidad, LaboratorioID)"
                            + "VALUES ("+ e.getID() + "," + id + "," + e.getCantidadMax() + "," + LabID + ");";
                statement.executeUpdate(sql_);
            }
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
            return false;
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
    }
}
