/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.SU;

import Control.ValidarRegistro;
import Entidad.Laboratorio;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MLabsTest {

    public MLabsTest() {
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

    @Test
    public void testCreateLab() {

        ValidarRegistro validar = new ValidarRegistro();

        Laboratorio lab0 = new Laboratorio("Laboratorio Prueba 1", "8888888", "Edificio 403");
        assertEquals(validar.verificarLab(lab0), ("Laboratorio creado"));

        Laboratorio lab1 = new Laboratorio("La", "8888888", "Edificio 403");
        assertEquals(validar.verificarLab(lab1), ("Longitud nombre incorrecta"));

        Laboratorio lab2 = new Laboratorio("Laboratorio Prueba 1", "8", "Edificio 403");
        assertEquals(validar.verificarLab(lab2), ("Longitud teléfono incorrecta"));

        Laboratorio lab3 = new Laboratorio("Laboratorio Prueba 1", "8888888", "4");
        assertEquals(validar.verificarLab(lab3), ("Longitud ubicación incorrecta"));

    }
}
