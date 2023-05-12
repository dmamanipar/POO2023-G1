/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

/**
 *
 * @author User
 */
public class Usuario {
    
    private int id;
    private String user;
    private String clave;
    private String estado;
    private String perfil;
    
    public Usuario() {}
    
    public Usuario(int id, String user, String clave, String estado, String perfil) {
        this.id = id;
        this.user = user;
        this.clave = clave;
        this.estado = estado;
        this.perfil = perfil;
    }
    
    // Métodos getters y setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getPerfil() {
        return perfil;
    }
    
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
}