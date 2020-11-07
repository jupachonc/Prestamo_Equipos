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
        
     /* String nombre = ("Andres");
        String apellido = ("Castañeda");
        String strDocumento = ("1000000000");
        String strEmail = ("acasta@unal.edu.co"); 
        String contrasena = ("123456");
        String reContrasena = ("123456");
        assertEquals(validar.verificarRegistro(nombre,apellido,strDocumento,strEmail,contrasena,reContrasena),("Usuario registrado"));
        */
        String nombre1 = ("Andrea");
        String apellido1 = ("Castañeda");
        int Documento1 = 1000000001;
        String strEmail1 = ("acasta@unal.edu.co");
        String contrasena1 = ("123456");
        String reContrasena1 = ("12345J");
        assertEquals(validar.verificarRegistro(nombre1,apellido1,Documento1,strEmail1,contrasena1,reContrasena1, 0),("Las contraseñas no conciden"));
       
        String nombre2 = ("Andre");
        String apellido2 = ("Castañeda");
        int Documento2 = 1000000002;
        String strEmail2 = ("acasta@unal.edu.co");
        String contrasena2 = ("123");
        String reContrasena2 = ("123");
        assertEquals(validar.verificarRegistro(nombre2,apellido2,Documento2,strEmail2,contrasena2,reContrasena2, 0),("Longitud contrasena incorrecta"));
       
        String nombre3 = ("Andre");
        String apellido3 = ("Castañeda");
        int    Documento3 = 1000000003;
        String strEmail3 = ("acasta-unal.edu.co");
        String contrasena3 = ("123456");
        String reContrasena3 = ("123456");
        assertEquals(validar.verificarRegistro(nombre3,apellido3,Documento3,strEmail3,contrasena3,reContrasena3, 0),("E-mail inválido"));
       /*
        String nombre4 = ("Andre");
        String apellido4 = ("Castañeda");
        int    Documento4 = 10000000.03 ;
        String strEmail4 = ("acasta@unal.edu.co");
        String contrasena4 = ("123456");
        String reContrasena4 = ("123456");
        assertEquals(validar.verificarRegistro(nombre4,apellido4,Documento4,strEmail4,contrasena4,reContrasena4),("Documento inválido"));
       */
        String nombre5 = ("Andre");
        String apellido5 = ("Cas");
        int Documento5 = 1000000003;
        String strEmail5 = ("acasta@unal.edu.co");
        String contrasena5 = ("123456");
        String reContrasena5 = ("123456");
        assertEquals(validar.verificarRegistro(nombre5,apellido5,Documento5,strEmail5,contrasena5,reContrasena5, 0),("Longitud apellido incorrecta"));
       
        String nombre6 = ("San");
        String apellido6 = ("Castañeda");
        int Documento6 = 1000000003;
        String strEmail6 = ("acasta@unal.edu.co");
        String contrasena6 = ("123456");
        String reContrasena6 = ("123456");
        assertEquals(validar.verificarRegistro(nombre6,apellido6,Documento5,strEmail6,contrasena6,reContrasena6, 0),("Longitud nombre incorrecta"));
       
        String nombre7 = ("Andres");
        String apellido7 = ("Castañeda");
        int Documento7 = 1000000000;
        String strEmail7 = ("acasta@unal.edu.co");
        String contrasena7 = ("123456");
        String reContrasena7 = ("123456");
        assertEquals(validar.verificarRegistro(nombre7,apellido7,Documento7,strEmail7,contrasena7,reContrasena7, 0),("Error: Usuario ya existente"));
       
        
    }
}
