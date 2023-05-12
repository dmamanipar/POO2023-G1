/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

import lombok.Data;

/**
 *
 * @author INTEL
 */
@Data
public class BancoPreguntasTO {
    public int idBancoPreguntas,idArea;
    public String pregunta;

    public int getIdBancoPreguntas() {
        return idBancoPreguntas;
    }

    public void setIdBancoPreguntas(int idBancoPreguntas) {
        this.idBancoPreguntas = idBancoPreguntas;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
 
}
