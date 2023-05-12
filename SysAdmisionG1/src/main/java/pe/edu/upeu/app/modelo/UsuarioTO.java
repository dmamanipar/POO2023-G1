/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

import lombok.Data;

/**
 *
 * @author Jesus
 */
@Data
public class UsuarioTO {
    
    public int idUsuario;
    public String user, clave, estado, perfil;
    
}
