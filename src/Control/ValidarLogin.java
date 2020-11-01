/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.UsuarioDAO;
import Entidad.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarLogin {
    
   private UsuarioDAO dao = new UsuarioDAO();
    public ValidarLogin(){}
    
    public String verificarLogin(Usuario usuario){
        if(!verificarLongitudNombre(usuario.getNombre())){
            return("Longitud de nombre incorrecta");
        }
        if(!verificarLongitudPassword(usuario.getContrasena())){
            return("Longitud de contraseña incorrecta");
        }
        /*if(dao.leer(usuario)){
            return("Bienvenido");
        }*/
        return("Datos incorrectos");  
    }
   /* public boolean acceso (Usuario acc){
        if (dao.leer(acc)){
            return false;
        }return true;
    }*/
    
    
    
    public boolean verificarLongitudNombre(String nombre){
        return(nombre.length() > 1 && nombre.length() <= 255);
    }
    
    public boolean verificarLongitudPassword(String password){
        return(password.length() >= 6 && password.length() < 255);
    }
}
