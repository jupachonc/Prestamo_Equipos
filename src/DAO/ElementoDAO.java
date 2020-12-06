package DAO;

import static DAO.UsuarioDAO.DB_URL;
import Entidad.Elemento;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
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

static final String DB_URL = "jdbc:mysql://loginapp.c6zrixw9dnvf.us-east-1.rds.amazonaws.com:3306/LoginApp?useSSL=false";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "admin";
    static final String DB_PASSWD = "H6TWCpw3RbKVhKWf4ZUa";
    
public void create(Elemento el, int cat){

try {
            con = DBConnection.getConnection();
            String sql="INSERT INTO " + 
                "elemento (ID , Nombre, Descripción, EstadoElemento, CategoriaID) " + 
                "VALUES ('" + el.getID()+ "', '" + el.getNombre()+ "', '" + el.getDescripción()+ "', '" + el.getEstadoElemento()+" ','"+cat+"' )";
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
    
    public boolean existente(int ID) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql = "SELECT * FROM elemento WHERE ID = " + ID  + "\";";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            } else {
                sql = "SELECT * FROM administrador WHERE Documento = " + ID + "\";";
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
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

public void update(Elemento el){
        try {
            con = DBConnection.getConnection();
            String sql="UPDATE elemento SET " + 
                "Nombre =  '" + el.getNombre()+ "', Descripción = '" + el.getDescripción()+ "', EstadoElemento = '" + el.getEstadoElemento()+ "' WHERE ID = '" + el.getID()+"'";
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
                
                i++;
                
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
