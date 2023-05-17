/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
/*import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ComboBoxOption;*/
import pe.edu.upeu.app.modelo.FacultadTO;

/**
 *
 * @author old
 */
public interface FacultadDaol {

    public interface FacultadDao {

        int create(FacultadTO d);

        int update(FacultadTO d);

        int delete(String id) throws Exception;

        List<FacultadTO> listCmb(String filter);

        List<FacultadTO> getAll();

        FacultadTO buscarNombre(String nombre);
    }
}
