package DAO;

import Entidad.Categoria;
import Entidad.Reserva;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
            String sql = "SELECT * FROM reservas, estudiante WHERE EstudianteDocumento=Documento and EstadoReserva = 0 AND Documento =" + user.getDocumento() + ";";
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
}
