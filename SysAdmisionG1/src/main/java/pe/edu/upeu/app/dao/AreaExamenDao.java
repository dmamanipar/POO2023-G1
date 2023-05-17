/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.modelo.AreaExamenTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author EP-Ing_Sist.-CALIDAD
 */
public abstract class AreaExamenDao implements AreaExamenDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(AreaExamenDao.class.getName());

    
    @Override
    public int create(AreaExamenTO d) {
        int rsId = 0;
        String[] returns = {"id_area_examen"};
        String sql = "INSERT INTO area_examen(nombreae)"
            + " values(?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
           // ps.setInt(++i, d.getIdAreaExamen());
            ps.setString(++i, d.getNombreae());
           
            rsId = ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    @Override
    public int update(AreaExamenTO d) {
        System.out.println("actualizar d.getDniruc: " + d.getNombreae());
        int comit = 0;
        String sql = "UPDATE Areas SET "
            + "nombreae=?, "
            + "WHERE Nombreae=?";
                
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getNombreae());
           
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }
    
    @Override
    public List<AreaExamenTO> listarTodo() {
        List<AreaExamenTO> listarEntidad = new ArrayList();
        String sql = "SELECT * from area_examen";
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AreaExamenTO cli = new AreaExamenTO();
                cli.setNombreae(rs.getString("nombreae"));
                cli.setIdAreaExamen(rs.getInt("id_area_examen"));
                
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }

    @Override
    public int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM areas WHERE IdArea = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "delete", ex);
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

 public static void main(String[] args) {
        Scanner cs = new Scanner(System.in);
       AreaExamenDao po = new AreaExamenDao() {};
        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    AreaExamenTO tox = new AreaExamenTO();
                    System.out.println("Ingrese Nombre:");
                    tox.setNombreae(cs.next());
                    //System.out.println("Ingres Area:");
                    //tox.setIdAreaExamen(cs.nextInt());
                    
                    po.create(tox);
                    po.listarAreas(po.listarTodo());
                }
                case "R" ->
                    po.listarAreas(po.listarTodo());
                case "U" -> {
                    AreaExamenTO tox;
                    System.out.println("Ingrese el Nombre a Modificar:");
                    int areaExamen=cs.nextInt();
                    tox=po.buscarEntidad(areaExamen);
                    System.out.println("IngreseNuevo Nombre:");
                    tox.setNombreae(cs.next());                   
                    po.update(tox);
                    po.listarAreas(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el Nombre del Registro que desea eliminar:");               
                        po.delete(cs.nextInt());
                        po.listarAreas(po.listarTodo());
                    } catch (Exception e) {
                        System.err.println("Error al Eliminar");
                    }
                }
                default ->
                    System.out.println("Opcion no existe intente otra vez!");
            }
            System.out.println("Que desea hacer:\n" + mensajeOpciones);
            opcion = cs.next();
        } while (!opcion.toUpperCase().equals("X"));

        System.out.println("F1:" + (i++));
    }
 
    public void listarAreas(List<AreaExamenTO> lista) {
        System.out.println("AreaExamen\t\tNombreae.");
        for (AreaExamenTO p : lista) {
            System.out.println(p.getNombreae());
        }
    }

    @Override
    public AreaExamenTO buscarEntidad(int idAreaExamen) {
        AreaExamenTO cli = new AreaExamenTO();
        String sql = "SELECT po.*, p.nombreae"
                + "FROM AreaExamen, nombreae n, carrera c "
                + "WHERE p.id_area_examen = po.id_area_examen "
                + " and po.Nombreae=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idAreaExamen);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setNombreae(rs.getString("id_area_examen"));
             
                         
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }

}