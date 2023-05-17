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
import pe.edu.upeu.app.modelo.FacultadTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author olp
 */
public class FacultadDao implements FacultadDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(FacultadDao.class.getName());

    @Override
    public int create(FacultadTO d) {
        int rsId = 0;
        String[] returns = {"id_facultad"};
        String sql = "INSERT INTO facultad(nombrefacultad) values(?) ";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);         
            
            ps.setString(++i, d.getNombreFacultad());
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
    public int update(FacultadTO d) {
        System.out.println("actualizar d.getIdFacultad: " + d.getIdFacultad());
        int comit = 0;
        String sql = "UPDATE facultad SET "
                + "nombrefacultad=? "
                
                + "WHERE id_facultad=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getNombreFacultad());
            /*ps.setInt(++i, d.getIdFacultad());*/
   
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<FacultadTO> listarTodo() {
        List<FacultadTO> listarEntidad = new ArrayList();
        String sql = "SELECT * "
                + "FROM facultad ";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FacultadTO cli = new FacultadTO();
                cli.setIdFacultad(rs.getInt("id_facultad"));
                cli.setNombreFacultad(rs.getString("nombrefacultad"));
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
        String sql = "DELETE FROM facultad WHERE id_facultad = ?";
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
        FacultadDao fa = new FacultadDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "MENU OPCIONES \nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    FacultadTO tox = new FacultadTO();
                   // System.out.println("Ingrese el ID de la facultad:");
                   // tox.setIdFacultad(cs.nextInt()); 
                    System.out.println("Ingrese el nombre de la facultad:");
                    tox.setNombreFacultad(cs.next());
                    fa.create(tox);
                    fa.listarFacultad(fa.listarTodo());
                }
                case "R" ->
                    fa.listarFacultad(fa.listarTodo());
                case "U" -> {
                    FacultadTO tox;
                    System.out.println("Ingrese el ID a Modificar:");
                    int id =cs.nextInt();
                    tox = fa.buscarEntidad(id);
                    System.out.println("Ingres Nuevo Nombre:");
                    tox.setNombreFacultad(cs.next());

                    fa.update(tox);
                    fa.listarFacultad(fa.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el ID del Registro que desea eliminar:");
                        fa.delete(cs.nextInt());
                        fa.listarFacultad(fa.listarTodo());
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

    public void listarFacultad(List<FacultadTO> lista) {
        System.out.println("ID\t\tNombre Facultad\t\t\t");
        for (FacultadTO p : lista) {
            System.out.println(p.getIdFacultad() + "\t\t" + p.getNombreFacultad() + "\t");
        }
    }

    @Override
    public List<FacultadTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FacultadTO buscarEntidad(int id) {
        FacultadTO cli = new FacultadTO();
        String sql = "SELECT * "
                + "FROM facultad "
                + "WHERE id_facultad=?";
        try {
            
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setNombreFacultad(rs.getString("nombrefacultad"));
                cli.setIdFacultad(rs.getInt("id_facultad"));
                
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

}
