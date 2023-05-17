/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.PreguntasTO;

/**
 *
 * @author acer
 */
public interface PreguntasDaoI {

    public int create(PreguntasTO d);

    public int update(PreguntasTO d);

    public int delete(int id) throws Exception;
    
    public PreguntasTO buscarEntidad(int id_pregunta);

    public List<PreguntasTO> listarTodo();

    public List<PreguntasTO> listCmb(String filter);

    public List<ModeloDataAutocomplet> listAutoComplet(String filter);

    public List<ComboBoxOption> listaModalidadExamen();

    public List<ComboBoxOption> listarPeriodo();

    public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter);

    public String buscarModalidadExamen(String id);
}
