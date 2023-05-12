/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.util.ErrorLogger;
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
//import pe.edu.upeu.app.modelo.ComboBoxOption;
//import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.modelo.AreasTO;
import pe.edu.upeu.app.modelo.ComboBoxOption;
//import pe.edu.upeu.app.modelo.PostulanteTO;
//import pe.edu.upeu.app.modelo.PreguntasTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author EP-Ing_Sist.-CALIDAD
 */
public abstract class AreasDao implements AreasDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(AreasDao.class.getName());

    public int create(AreasTO d) {
        int rsId = 0;
        String[] returns = {"id_area"};
        String sql = "INSERT INTO areas(id_area, nombrearea, siglas)"
            + " values(?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setString(++i, d.getIdAreas());
            ps.setString(++i, d.getNombreArea());
            ps.setString(++i, d.getSiglas()); 
            rsId = ps.executeUpdate();// 0 no o 1 si commit
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

    public int update(AreasTO d) {
        System.out.println("actualizar d.getDniruc: " + d.getIdAreas());
        int comit = 0;
        String sql = "UPDATE Areas SET "
            + "nombrearea=?, "
            + "siglas=? "
            + "WHERE id_area=?";
                
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getIdAreas());
            ps.setString(++i, d.getNombreArea());
            ps.setString(++i, d.getSiglas());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }
    public List<AreasTO> listarTodo() {
        List<AreasTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.nombrearea "
            + "FROM areas po "
            + "INNER JOIN p ON p.id_area = po.id_area "
            + "WHERE po.nombrearea = ?";
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AreasTO cli = new AreasTO();
                cli.setIdAreas(rs.getString("Areas"));
                cli.setNombreAreas(rs.getString("nombreareas"));
                cli.setSiglas(rs.getString("Siglas"));
                //cli.setApellidoMat(rs.getString("apellido_mat"));
                //cli.setModalidad(rs.getString("modalidad"));
                //cli.setEstado(rs.getString("estado"));
                //cli.setIdCarrera(rs.getInt("id_carrera"));
                //cli.setIdPeriodo(rs.getInt("id_periodo"));
                //cli.setNombrePeriodo(rs.getString("nombreperiodo"));
                //cli.setNombreCarrera(rs.getString("nombrecarrera"));
                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }

    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM areas WHERE IdArea = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            ErrorLogger.log(Level.SEVERE, "delete", ex);
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

 public static void main(String[] args) {
        Scanner cs = new Scanner(System.in);
       AreasDao po = new AreasDao() {};
        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    AreasTO tox = new AreasTO();
                    System.out.println("Ingrese Area:");
                    tox.setNombreAreas(cs.next());
                    System.out.println("Ingres Siglas:");
                    tox.setSiglas(cs.next());
                    //System.out.println("Ingres A. Paterno:");
                    //tox.setApellidoPat(cs.next());
                    //System.out.println("Ingres A. Materno:");
                    //tox.setApellidoMat(cs.next());
                    //System.out.println("Ingrese Modalidad(E=Examen, PI=Primeros Puestos):");
                    //tox.setModalidad(cs.next());
                    //System.out.println("Ingrese Estado (Activo, Desactivo):");
                    //tox.setEstado(cs.next());
                    //System.out.println("Ingrese el Perido(1=2023-1):");
                    //tox.setIdPeriodo(cs.nextInt());
                    //System.out.println("Ingrese Carrera(1=Sistemas, 2=Contabilidad):");
                    //tox.setIdCarrera(cs.nextInt());
                    po.create(tox);
                    po.listarAreas(po.listarTodo());
                }
                case "R" ->
                    po.listarAreas(po.listarTodo());
                case "U" -> {
                    AreasTO tox;
                    System.out.println("Ingrese el DNI a Modificar:");
                    String NombreAreas=cs.next();
                    tox=po.buscarEntidad(NombreAreas);
                    System.out.println("Ingres Nuevo Nombre:");
                    //tox.setNombre(cs.next());
                    System.out.println("Ingres Nuevo A. Paterno:");
                    //tox.setApellidoPat(cs.next());                    
                    po.update(tox);
                    po.listarAreas(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el DNI del Registro que desea eliminar:");
                        po.delete(cs.next());
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
 
    public void listarAreas(List<AreasTO> lista) {
        System.out.println("DNI\t\tNombre\t\t\tApellidos\t\t\tCarrera Post.");
        for (AreasTO p : lista) {
            System.out.println(p.getNombreAreas() + "\t" + p.getSiglas() + "\t\t\t"
                    + p.getNombreAreas() + " " + p.getSiglas());
        }
    }

   
    public List<AreasTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public AreasTO buscarEntidad(String dni) {
        AreasTO cli = new AreasTO();
        String sql = "SELECT po.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM postulante po, periodo p, carrera c "
                + "WHERE p.id_periodo = po.id_periodo and po.id_carrera = c.id_carrera"
                + " and po.dni=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setNombreAreas(rs.getString("dni"));
                cli.setSiglas(rs.getString("nombre"));
                //cli.setApellidoPat(rs.getString("apellido_pat"));
                //cli.setApellidoMat(rs.getString("apellido_mat"));
                //cli.setModalidad(rs.getString("modalidad"));
                //cli.setEstado(rs.getString("estado"));
                //cli.setIdCarrera(rs.getInt("id_carrera"));
                //cli.setIdPeriodo(rs.getInt("id_periodo"));
                //cli.setNombrePeriodo(rs.getString("nombreperiodo"));
                //cli.setNombreCarrera(rs.getString("nombrecarrera"));
                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));               
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }

    
    public List<ModeloDataAutocomplet> listAutoComplet(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    public List<ComboBoxOption> listaModalidadExamen() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public List<ComboBoxOption> listarPeriodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
    public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    public String buscarModalidadExamen(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void listarAreas(List<AreasTO> listarTodo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    }

}