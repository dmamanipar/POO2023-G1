/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.BancopreguntasTO;

/**
 *
 * @author Jose MP
 */
public interface Banco_PreguntasDaol {
        

public int create(BancopreguntasTO d);
public int update(BancopreguntasTO d);
public int delete(String id) throws Exception;
public List<BancopreguntasTO> listCmb(String filter);
public List<BancopreguntasTO> listarTodo();
public List<ModeloDataAutocomplet> listAutoComplet(String filter); 
public List<ComboBoxOption> listaModalidadExamen();
public List<ComboBoxOption> listarPeriodo();
public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter);
public String buscarModalidadExamen(String id);
}

