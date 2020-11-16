/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Control.Admin.AdminCategoriasController;
import Entidad.Categoria;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nguzman
 */

public class CategoriasDAO {
    private Connection con;
    

    public void create(Categoria c){
        try {
            con = DBConnection.getConnection();
            String sql="INSERT INTO " + 
                "categoria (Nombre, Descripción, MacroCategoriaID, CantidadMax) " + 
                "VALUES ('" + c.getNombre()+ "', '" + c.getDescripción()+ "', '" + c.getMacroCategoriaID()+ "','" + c.getCantidadMax()+ "')";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMacroCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean delete(Categoria c){
        try {
            con = DBConnection.getConnection();
            String sql="DELETE FROM categoria WHERE ID = '" + c.getID()+"'";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public void update(Categoria c){
        try {
            con = DBConnection.getConnection();
            String sql="UPDATE categoria SET " + 
                "Nombre =  '" + c.getNombre()+ "', Descripción = '" + c.getDescripción()+ "', CantidadMax = '" + c.getCantidadMax()+ "' WHERE ID = '" + c.getID()+"'";
            System.out.println(sql);
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
