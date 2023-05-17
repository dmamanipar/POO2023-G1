/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.edu.upeu.app.modelo.AreaPeriodoTO;




/**
 *
 * @author ACER
 */
public interface AreaPeriodoDaoI {
    public int create(AreaPeriodoTO d);
    public int update(AreaPeriodoTO d);
    public int delete(String id) throws Exception;
    public List<AreaPeriodoTO> listarTodo();
    public AreaPeriodoTO buscarEntidad(int IdArea);

}
