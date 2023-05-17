/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

/**
 *
 * @author Data_Science
 */
import java.util.List;
import pe.edu.upeu.app.modelo.PeriodoTO;
import pe.edu.upeu.app.modelo.PostulanteTO;

public interface PeriodoDaoI {

    public int create(PeriodoTO d);

    public int update(PeriodoTO d);

    public int delete(String id) throws Exception;

public List<PostulanteTO> listarTodo();

}
