/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;


import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;
/*import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;*/
import pe.edu.upeu.app.modelo.FacultadTO;
/**
 *
 * @author old
 */
public interface FacultadDaoI {
    
    public int create(FacultadTO d);
public int update(FacultadTO d);
public int delete(int id) throws Exception;
public List<FacultadTO> listCmb(String filter);
public List<FacultadTO> listarTodo();
public FacultadTO buscarEntidad(int id);
public List<ModeloDataAutocomplet> listAutoComplet(String filter); 
public List<ComboBoxOption> listaModalidadExamen();
public List<ComboBoxOption> listarPeriodo();
public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter);

    
}
