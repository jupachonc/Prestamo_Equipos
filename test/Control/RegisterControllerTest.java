/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Entidad.Usuario;
import DAO.UsuarioDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

/**
 *
 * @author edgar
 */
public class RegisterControllerTest {
    
    
    public RegisterControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void RegisterTest() {
        
        ValidarRegistro validar = new ValidarRegistro();
        
        //PRIMERO HAY QUE REALIZAR EL TEST UNICAMENTE DEL CODIGO COMENTADO.
        //LUEGO COMENTARLO COMO ESTÁ, Y AHÍ SI HACER EL TESTEO DE TODO
        
    /*  String nombre = ("Andres");
        String apellido = ("Castañeda");
        String strDocumento = ("1000000000");
        String strEmail = ("acasta@unal.edu.co");
        String contrasena = ("123456");
        String reContrasena = ("123456");
        assertEquals(validar.verificarRegistro(nombre,apellido,strDocumento,strEmail,contrasena,reContrasena),("Usuario registrado"));
        */
        String nombre1 = ("Andrea");
        String apellido1 = ("Castañeda");
        String strDocumento1 = ("1000000001");
        String strEmail1 = ("acasta@unal.edu.co");
        String contrasena1 = ("123456");
        String reContrasena1 = ("12345J");
        assertEquals(validar.verificarRegistro(nombre1,apellido1,strDocumento1,strEmail1,contrasena1,reContrasena1),("Las contraseñas no conciden"));
       
        String nombre2 = ("Andre");
        String apellido2 = ("Castañeda");
        String strDocumento2 = ("1000000002");
        String strEmail2 = ("acasta@unal.edu.co");
        String contrasena2 = ("123");
        String reContrasena2 = ("123");
        assertEquals(validar.verificarRegistro(nombre2,apellido2,strDocumento2,strEmail2,contrasena2,reContrasena2),("Longitud contrasena incorrecta"));
       
        String nombre3 = ("Andre");
        String apellido3 = ("Castañeda");
        String strDocumento3 = ("1000000003");
        String strEmail3 = ("acasta-unal.edu.co");
        String contrasena3 = ("123456");
        String reContrasena3 = ("123456");
        assertEquals(validar.verificarRegistro(nombre3,apellido3,strDocumento3,strEmail3,contrasena3,reContrasena3),("E-mail inválido"));
       
        String nombre4 = ("Andre");
        String apellido4 = ("Castañeda");
        String strDocumento4 = ("1.000.00.00.03");
        String strEmail4 = ("acasta@unal.edu.co");
        String contrasena4 = ("123456");
        String reContrasena4 = ("123456");
        assertEquals(validar.verificarRegistro(nombre4,apellido4,strDocumento4,strEmail4,contrasena4,reContrasena4),("Documento inválido"));
       
        String nombre5 = ("Andre");
        String apellido5 = ("Cas");
        String strDocumento5 = ("1000000003");
        String strEmail5 = ("acasta@unal.edu.co");
        String contrasena5 = ("123456");
        String reContrasena5 = ("123456");
        assertEquals(validar.verificarRegistro(nombre5,apellido5,strDocumento5,strEmail5,contrasena5,reContrasena5),("Longitud apellido incorrecta"));
       
        String nombre6 = ("San");
        String apellido6 = ("Castañeda");
        String strDocumento6 = ("1000000003");
        String strEmail6 = ("acasta@unal.edu.co");
        String contrasena6 = ("123456");
        String reContrasena6 = ("123456");
        assertEquals(validar.verificarRegistro(nombre6,apellido6,strDocumento5,strEmail6,contrasena6,reContrasena6),("Longitud nombre incorrecta"));
       
        String nombre7 = ("Andres");
        String apellido7 = ("Castañeda");
        String strDocumento7 = ("1000000000");
        String strEmail7 = ("acasta@unal.edu.co");
        String contrasena7 = ("123456");
        String reContrasena7 = ("123456");
        assertEquals(validar.verificarRegistro(nombre7,apellido7,strDocumento7,strEmail7,contrasena7,reContrasena7),("Error: Usuario ya existente"));
       
        
    }
}
