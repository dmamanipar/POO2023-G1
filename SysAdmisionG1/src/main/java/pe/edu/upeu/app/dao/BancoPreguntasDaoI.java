/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.BancoPreguntasTO;
import pe.edu.upeu.app.modelo.ComboBoxOption;


/**
 *
 * @author INTEL
 */
public interface BancoPreguntasDaoI {
    public int create(BancoPreguntasTO d);
public int update(BancoPreguntasTO d);
public int delete(int id) throws Exception;
public List<BancoPreguntasTO> listCmb(String filter);
public List<BancoPreguntasTO> listarTodo();
public BancoPreguntasTO buscarEntidad(int id_bp);
public List<ModeloDataAutocomplet> listAutoComplet(String filter); 
public List<ComboBoxOption> listaModalidadExamen();
public List<ComboBoxOption> listarPeriodo();
public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter);
public String buscarModalidadExamen(String id);
}

