package DAO;

import Entidad.MacroCategoria;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOMacroCategorias {
    private Connection con;

    public void create(MacroCategoria mc){
        try {
            con = DBConnection.getConnection();
            String sql="INSERT INTO " + 
                "macrocategoria (Nombre, Descripción, LaboratorioID) " + 
                "VALUES ('" + mc.getNombre()+ "', '" + mc.getDescripción()+ "', '" + mc.getLaboratorioID()+ "')";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean delete(MacroCategoria mc){
        try {
            con = DBConnection.getConnection();
            String sql="DELETE FROM macrocategoria WHERE ID = '" + mc.getID()+"'";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public void update(MacroCategoria mc){
        try {
            con = DBConnection.getConnection();
            String sql="UPDATE macrocategoria SET " + 
                "Nombre =  '" + mc.getNombre()+ "', Descripción = '" + mc.getDescripción()+ "', LaboratorioID = '" + mc.getLaboratorioID()+ "' WHERE ID = '" + mc.getID()+"'";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
