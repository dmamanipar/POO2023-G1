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
public class ResultadoFinalTO {
    public int idResultfinal, idPostulante, idPeriodo, idCarrera, evalPsicologica;
    public String dni;
    double puntoConocimiento, puntoEntrevista;
}
