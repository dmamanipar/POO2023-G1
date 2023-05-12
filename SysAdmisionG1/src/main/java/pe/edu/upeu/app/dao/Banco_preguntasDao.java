/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.modelo.BancopreguntasTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author Jose MP
 */
public class Banco_preguntasDao {
    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(BancopreguntasTO.class.getName());
    
    public int update(BancopreguntasTO d) {
        System.out.println("actualizar d.getdni: " + d.getBp());
        int comit = 0;
        String sql = "UPDATE resultado SET "
                + "resultado=?, ";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getResultado());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }
    
    
 public List<PostulanteTO> listarTodo() {
        List<PostulanteTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM postulante po, periodo p, carrera c "
                + "WHERE p.id_periodo = po.id_periodo and po.id_carrera = c.id_carrera";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PostulanteTO cli = new PostulanteTO();
                cli.setDni(rs.getString("dni"));
                cli.setNombre(rs.getString("nombre"));
                cli.setApellidoPat(rs.getString("apellido_pat"));
                cli.setApellidoMat(rs.getString("apellido_mat"));
                cli.setModalidad(rs.getString("modalidad"));
                cli.setEstado(rs.getString("estado"));
                cli.setIdCarrera(rs.getInt("id_carrera"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));
                cli.setNombrePeriodo(rs.getString("nombreperiodo"));
                cli.setNombreCarrera(rs.getString("nombrecarrera"));
                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
