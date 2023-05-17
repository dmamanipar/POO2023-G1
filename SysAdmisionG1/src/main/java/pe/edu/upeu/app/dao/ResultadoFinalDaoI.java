/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.edu.upeu.app.modelo.ResultadoFinalTO;

/**
 *
 * @author HP
 */
public interface ResultadoFinalDaoI {

    public int create(ResultadoFinalTO d);

    public int update(ResultadoFinalTO d);

    public int delete(String id) throws Exception;

    public List<ResultadoFinalTO> listarTodo();
    
    public ResultadoFinalTO buscarEntidad(int dni);
}
