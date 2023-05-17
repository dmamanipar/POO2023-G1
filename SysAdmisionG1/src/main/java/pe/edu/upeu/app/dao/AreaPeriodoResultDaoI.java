/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.AreaPeriodoResultTO;



/**
 *
 * @author HP
 */

public interface AreaPeriodoResultDaoI {
public int create(AreaPeriodoResultTO d);
public int update(AreaPeriodoResultTO d);
public int delete(int id) throws Exception;
public List<AreaPeriodoResultTO> listCmb(String filter);
public List<AreaPeriodoResultTO> listarTodo();
public AreaPeriodoResultTO buscarEntidad(int idAreaPeriodo);
public List<ModeloDataAutocomplet> listAutoComplet(String filter); 
public List<ComboBoxOption> listaModalidadExamen();
public List<ComboBoxOption> listarPeriodo();
public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter);
//public String buscarModalidadExamen(String id);

}
