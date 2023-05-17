/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

import lombok.Data;

/**
 *
 * @author HP
 */
@Data
public class AreaPeriodoResultTO {
    
    public int idAreaPeriodoResult,idAreaPeriodo,idAreaExamen;
    public double porcentaje;
    //FOREING KEY
    public String nombreIdAreaPeriodo, nombreIdAreaExamen;


}
