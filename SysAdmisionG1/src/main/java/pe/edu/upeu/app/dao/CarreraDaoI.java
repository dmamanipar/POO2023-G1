/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.edu.upeu.app.modelo.CarreraTO;

/**
 *
 * @author Data_Science
 */
public interface CarreraDaoI {

    public int create(CarreraTO d);

    public int update(CarreraTO d);

    public int delete(int id) throws Exception;

    public List<CarreraTO> listarTodo();

    public CarreraDao buscarEntidad(int id);
}
