/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.edu.upeu.app.modelo.AreaExamenTO;

public interface AreaExamenDaoI {

    public int create(AreaExamenTO d);
    public int update(AreaExamenTO d);
    public int delete(String id) throws Exception;
    public List<AreaExamenTO> listarTodo();
    public AreaExamenTO buscarEntidad(String dni);
   
}
