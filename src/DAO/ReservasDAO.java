package DAO;

import Entidad.Categoria;
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
            System.out.println(cats.get(0).getID());
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
}
