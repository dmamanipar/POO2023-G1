/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author EP-Ing_Sist.-CALIDAD
 */
public class PostulanteDao implements PostulanteDaoI{
    ConnS instance=ConnS.getInstance();
    Connection connection=instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    ErrorLogger log=new ErrorLogger(PostulanteDao.class.getName());    
    @Override
    public List<PostulanteTO> listarTodo() {
        String sql=" select po.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "from postulante po, periodo p, carrera c "
                + "where po.id_periodo=p.id_periodo and po.id_carrera=c.id_carrera;";
        
        try {
            ps=connection.prepareStatement(sql);
            rs=ps.executeQuery();
            
            while (rs.next()) {
                PostulanteTO pos=new PostulanteTO();
                pos.setDni(rs.getString("dni"));
                pos.setNombre(rs.getString("nombre"));                                
                System.out.println("dni:"+pos.getDni() +" nombre:"+pos.getNombre());                
            }
            
        } catch (Exception e) {
        }
        
        return null;
    }    
    
    public static void main(String[] args) {
        PostulanteDao po=new PostulanteDao();
        po.listarTodo();
    }
}
