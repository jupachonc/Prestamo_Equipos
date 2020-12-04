package DAO;

import Entidad.Elemento;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANDRES CAMILO
 */
public class ElementoDAO {
    
Connection con;
Statement statement;
    
public void create(Elemento el, int cat){

try {
            con = DBConnection.getConnection();
            String sql="INSERT INTO " + 
                "elemento (ID , Nombre, Descripción, EstadoElemento, CategoriaID) " + 
                "VALUES ('" + el.getID()+ "', '" + el.getNombre()+ "', '" + el.getDescripción()+ "', '" + el.getEstado()+" ','"+cat+"' )";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }




}
   
    public boolean delete(Elemento el){
        try {
            con = DBConnection.getConnection();
            String sql="DELETE FROM elemento WHERE ID = '" + el.getID()+"'";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
public void update(Elemento el){
        try {
            con = DBConnection.getConnection();
            String sql="UPDATE elemento SET " + 
                "Nombre =  '" + el.getNombre()+ "', Descripción = '" + el.getDescripción()+ "', EstadoElemento = '" + el.getEstado()+ "' WHERE ID = '" + el.getID()+"'";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public ArrayList<Elemento> getElements(int id) {
        ArrayList<Elemento> labs = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            con = DBConnection.getConnection();
            statement = con.createStatement();
            String sql = "SELECT * FROM elemento Where CategoriaID = "+id+";";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            int i =0;
            while (resultSet.next()) {
                labs.add(new Elemento(resultSet.getInt("ID"), resultSet.getString("Nombre"),
                        resultSet.getString("Descripción"), resultSet.getInt("EstadoElemento")));
                System.out.println("La mierda esta avanzando ");
                i++;
                System.out.println(i + "puta mierda");
            }

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        return labs;
    }
    
}
