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
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.AreaPeriodoResultTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author EP-Ing_Sist.-CALIDAD
 */

public class AreaPeriodoResultDao implements AreaPeriodoResultDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(PostulanteDao.class.getName());

    @Override
    public int create(AreaPeriodoResultTO d) {
        int rsId = 0;
        String[] returns = {"id_area_periodo"};
        String sql = "INSERT INTO area_periodo_result(id_area_periodo,id_area_examen,  "
                + " porcentaje) "
                + " values(?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
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
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    @Override
    public int update(AreaPeriodoResultTO d) {
        System.out.println("actualizar d.getIdAreaPeriodo: " + d.getIdAreaPeriodo());
        int comit = 0;
        String sql = "UPDATE area_periodo_result SET "
                + "id_area_periodo=?, "
                + "id_area_examen=?, "
                + "porcentaje=?, ";
                //+ "WHERE id_area_periodo=?"
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
        String sql = "SELECT po.*, p.id_area_periodo as nombreidareaperiodo, c.nonmbreidareaexamen "
                + "FROM area_periodo_result po, area_periodo p, area_examen c "
                + "WHERE p.id_area_periodo = po.id_area_periodo and po.id_area_examen = c.id_area_examen"
                + " and po.porcentaje=?";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AreaPeriodoResultTO cli = new AreaPeriodoResultTO();
                cli.setIdAreaPeriodo(rs.getInt("id_area_periodo"));
                cli.setIdAreaExamen(rs.getInt("id_area_examen"));
                cli.setPorcentaje(rs.getDouble("porcentaje"));
                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));
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
        String sql = "DELETE FROM area_periodo_result WHERE id_area_periodo = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static void main(String[] args) {
        Scanner cs = new Scanner(System.in);
        AreaPeriodoResultDao po = new AreaPeriodoResultDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    AreaPeriodoResultTO tox = new AreaPeriodoResultTO();
                    System.out.println("Ingrese el id area periodo:");
                    tox.setIdAreaPeriodo(cs.nextInt());
                    System.out.println("Ingres id_area_examen:");
                    tox.setIdAreaExamen(cs.nextInt());
                    System.out.println("Ingres porcentaje:");
                    tox.setPorcentaje(cs.nextDouble());
                            
                    po.listarPostulantes(po.listarTodo());
                }
                case "R" ->
                    po.listarPostulantes(po.listarTodo());
                case "U" -> {
                    AreaPeriodoResultTO tox;
                    System.out.println("Ingrese el ID a Modificar:");
                    int idAreaPeriodo=cs.nextInt();
                    tox=po.buscarEntidad(idAreaPeriodo);
                    System.out.println("Ingres Nuevo Id area periodo:");
                    tox.setIdAreaPeriodo(cs.nextInt());
                    System.out.println("Ingres Nuevo Id area examen:");
                    tox.setPorcentaje(cs.nextInt());                    
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el ID AREA PERIODO del Registro que desea eliminar:");
                        po.delete(cs.nextInt());
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
        System.out.println("IdAreaPeriodo\t\tIdAreaExamen\t\t\tPorcentaje");
        for (AreaPeriodoResultTO p : lista) {
            System.out.println(p.getIdAreaPeriodo() + "\t" + p.getIdAreaExamen()+ "\t\t\t"
                    + p.getPorcentaje());
        }
    }

    @Override
    public List<AreaPeriodoResultTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AreaPeriodoResultTO buscarEntidad(int idAreaPeriodo) {
        AreaPeriodoResultTO cli = new AreaPeriodoResultTO();
        String sql = "SELECT po.*, p.id_area_periodo as nombreidareaperiodo, c.nombreidareaexamen "
                + "FROM area_periodo_result po, area_periodo p, area_examen c "
                + "WHERE p.id_area_periodo = po.id_area_periodo and po.id_area_examen = c.id_area_examen"
                + " and po.porcentaje=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idAreaPeriodo);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setIdAreaPeriodo(rs.getInt("id_area_periodo"));
                cli.setIdAreaExamen(rs.getInt("id_area_examen"));
                cli.setPorcentaje(rs.getDouble("porcentaje"));;
                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));               
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }

    @Override
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

    @Override
    public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}