
package Control;

import DAO.UsuarioDAO;
import Entidad.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarRegistro {
    
    private final UsuarioDAO dao = new UsuarioDAO();
    Usuario usuario = new Usuario();
    
    public String verificarRegistro(String nombre, String apellido, int documento, String email, String contrasena, String reContrasena) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        Pattern VALID_DOCUMENT_REGEX = Pattern.compile("^[0-9]{7,10}$");
        Matcher matcher2 = VALID_DOCUMENT_REGEX.matcher(String.valueOf(documento));
        
        if(!verificarLongitud(nombre,64)) {
            return ("Longitud nombre incorrecta");
        }    
        else if(!verificarLongitud(apellido,64)) {
            return ("Longitud apellido incorrecta");
        }
        else if(!matcher2.find()) {
            return ("Documento inválido");
        }
        else if(!matcher.find()) {
            return ("E-mail inválido");
        }
        
        else if(!verificarLongitud(contrasena,30)) {
            return ("Longitud contrasena incorrecta");
        }
        else if(!verificarContrasenas(contrasena,reContrasena)) {
            return("Las contraseñas no conciden");
        }
        else{
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDocumento(documento);
            usuario.setEmail(email);
            usuario.setContrasena(contrasena);
            System.out.println(usuario.getNombre());
            System.out.println(usuario.getContrasena());
            if(dao.existente(usuario)){
                return("Error: Usuario ya existente");
            }
            dao.crear(usuario);
            return ("Usuario registrado");
            

        }
    }
    
    public boolean verificarLongitud (String x, int largo){
        return (x.length() > 3 && x.length() <=largo);
    }
    public boolean verificarContrasenas (String contrasena, String reContrasena){
        return contrasena.equals(reContrasena);
    }

}
