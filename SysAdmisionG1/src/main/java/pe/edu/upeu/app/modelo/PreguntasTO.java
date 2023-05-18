/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

import lombok.Data;

/**
 *
 * @author acer
 */
@Data
public class PreguntasTO {
    public int idPregunta, idBp, idAreaPeriodo, numero;
    public String resultado;
    //Adicionales por cada forany key
    public String nombreAreaPeriodo, nombrePregunta;

    

}
