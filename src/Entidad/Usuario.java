/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

/**
 *
 * @author nguzman
 */
public class Usuario {
    private String nombre;
    private String apellido;
    private String documento;
    private String email;
    private String contrasena;
    private int type;
    
    public Usuario(){
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public String getDocumento(){
        return documento;
    }
    public void setDocumento(String documento){
        this.documento = documento;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getContrasena(){
        return contrasena;
    }
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Usuario(String nombre, String apellido, String documento, String email, String contrasena, int type) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.email = email;
        this.contrasena = contrasena;
        this.type = type;
    }
    
    
    
}
