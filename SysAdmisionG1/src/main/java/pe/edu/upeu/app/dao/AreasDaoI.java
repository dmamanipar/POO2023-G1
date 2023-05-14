/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.AreasTO;

/**
 *
 * @author guita
 */
public interface AreasDaoI {
    
public int create(AreasTO d);
public int update(AreasTO d);
public int delete(String id) throws Exception;
public List<AreasTO> listCmb(String filter);
public List<AreasTO> listarTodo();
}