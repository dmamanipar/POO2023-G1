/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

import lombok.Data;

/**
 *
 * @author  old
 */
@Data
public class FacultadTO {
    public int idFacultad;
    public String nombreFacultad;
    
    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombrefacultad) {
        this.nombreFacultad = nombrefacultad;
    }

    @Override
    public String toString() {
        return nombreFacultad;
    }
    
    
}
