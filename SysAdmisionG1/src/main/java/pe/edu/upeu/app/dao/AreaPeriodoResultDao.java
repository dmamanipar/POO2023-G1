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
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.modelo.AreaPeriodoResultTO;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author HP idAreaPeriodoResult,idAreaPeriodo,idAreaExamen; public double
 * Porcentaje; public String NombreAreaPeriodoResult,NombreAreaExamen;
 */

public class AreaPeriodoResultDao implements AreaPeriodoResultDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    //ResultSet rj;

    ErrorLogger log = new ErrorLogger(PostulanteDao.class.getName());
    @Override
    public int create(AreaPeriodoResultTO d) {
        int rsId = 0;
        String[] returns = {"idAreaPeriodo"};

        String sql = "INSERT INTO area_periodo_result ( id_area_periodo, id_area_examen,"
                + "porcentaje) "
                + " values(?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            //ps.setInt(++i, d.getIdAreaPeriodoResult());
            ps.setInt(++i, d.getIdAreaPeriodo());
            ps.setInt(++i, d.getIdAreaExamen());
            ps.setDouble(++i, d.getPorcentaje());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
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
    public int update(AreaPeriodoResultTO d) {
        System.out.println("actualizar d.getPorcentageruc: " + d.getIdAreaPeriodo());
        int comit = 0;
        String sql = "UPDATE area_perido_result SET "
                + "id_area_periodo=?, "
                + "id_area_examen=?, "
                + "porcentage=?, ";
  
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(++i, d.getIdAreaPeriodo());
            ps.setInt(++i, d.getIdAreaExamen());
            ps.setDouble(++i, d.getPorcentaje()); 
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<AreaPeriodoResultTO> listarTodo() {
        List<AreaPeriodoResultTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.porcentage"
                + "FROM areaperiodoresult po"
                + "WHERE p.id_area_periodo = po.id_area_periodo and po.id_area_examen = c.id_area_examen";
        try {   
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AreaPeriodoResultTO cli = new AreaPeriodoResultTO();
                cli.setIdAreaPeriodo(rs.getInt("periodo"));
                cli.setIdAreaExamen(rs.getInt("examen"));
                cli.setPorcentaje(rs.getDouble("apellido_pat"));
 
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }

    @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM area_periodo_result WHERE id_area_periodo = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }
    
    public static void main(String[] args) {
        Scanner cs = new Scanner(System.in);
        PostulanteDao po = new PostulanteDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    AreaPeriodoResultTO tox = new AreaPeriodoResultTO();
                    System.out.println("Ingrese el Id area periodo:");
                    tox.setIdAreaPeriodo(cs.nextInt());
                    System.out.println("Ingres id area examen");
                    tox.setIdAreaExamen(cs.nextInt());
                    System.out.println("Ingrese el Porcentaje");
                    tox.setPorcentaje(cs.nextDouble());
                    po.create(tox);
                    po.listarAreaPeriodoResult(po.listarTodo());
                }
                case "R" ->
                    po.listarAreaPeriodoResult(po.listarTodo());
                case "U" -> {
                    AreaPeriodoResultTO tox;
                    System.out.println("Ingrese el Id Area Periodo a Modificar:");
                    String IdAreaPeriodo = cs.next();
                    tox=po.buscarEntidad(IdAreaPeriodo);
                    System.out.println("Ingres Nuevo id area periodo:");
                    tox.setIdAreaExamen(cs.nextInt());
                    System.out.println("Ingres Nuevo id area examen:");
                    tox.setPorcentaje(cs.nextDouble());                    
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }

                case "D" -> {
                    try {
                        System.out.println("Ingrese el Id area del Registro que desea eliminar:");
                        po.delete(cs.next());
                        po.listarPostulantes(po.listarTodo());
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

    public void listarPostulantes(List<AreaPeriodoResultTO> lista) {
        System.out.println("DNI\t\tNombre\t\t\tApellidos\t\t\tCarrera Post.");
        for (AreaPeriodoResultTO p : lista) {
            System.out.println(p.getIdAreaPeriodo() + "\t" + p.getIdAreaExamen()+ "\t\t\t"
                    + p.getPorcentaje()) ;
        }
    }

    @Override
    public List<AreaPeriodoResultTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public AreaPeriodoResultTO buscarEntidad(int IdAreaPeriodo) {
        AreaPeriodoResultTO cli = new AreaPeriodoResultTO();
        String sql = "SELECT po.*, p.ideriodo"
                + "FROM areaperiodoresult po"
                + "WHERE p.Id_area_periodo = po.Id_area_periodo and po.id_area_examen = c.id_area_examen";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, IdAreaPeriodo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cli.setIdAreaPeriodo(rs.getInt("IdAreaPeriodo"));
                cli.setIdAreaExamen(rs.getInt("IdAreaExamen"));
                cli.setPorcentaje(rs.getDouble("Porcentaje"));
       
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

    @Override
    public List<ComboBoxOption> listaModalidadExamen() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ComboBoxOption> listarPeriodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AreaPeriodoResultTO buscarEntidad(Integer idAreaPeriodo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoComplet(Integer filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoCompletCarrera(Integer filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


  
}
