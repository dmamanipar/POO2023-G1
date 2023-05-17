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
import pe.edu.upeu.app.modelo.CarreraTO;
import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author Data_Science
 */
public class CarreraDao implements CarreraDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(PostulanteDao.class.getName());

    @Override
    public int update(CarreraTO d) {
        System.out.println("actualizar d.getDniruc: " + d.getIdCarrera());
        int comit = 0;
        String sql = "UPDATE carrera SET "
                + "id_facultad=?, "
                + "id_area_examen=?, "
                + "nombre_carrera=?"
                + " where id_carrera=? ";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(++i, d.getIdFacultad());
            ps.setInt(++i, d.getIdAreaExamen());
            ps.setInt(++i, d.getIdCarrera());

            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int create(CarreraTO d) {
        int rsId = 0;
        String[] returns = {"dni"};
        String sql = "INSERT INTO CarreraTO(id_facultad, id_area_examen, nombre_carrera) "
                + " values(?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdFacultad());
            ps.setInt(++i, d.getIdAreaExamen());
            ps.setString(++i, d.getNombrecarrera());

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
    public List<CarreraTO> listarTodo() {
        List<CarreraTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM carrera po, periodo p, carrera c "
                + "WHERE p.id_periodo = po.id_periodo and po.id_carrera = c.id_carrera";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CarreraTO cli = new CarreraTO();
                cli.setIdFacultad(rs.getInt("id facultadd"));
                cli.setIdAreaExamen(rs.getInt("area examen"));
                cli.setNombrecarrera(rs.getString("nombre carrera"));

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
        String sql = "DELETE FROM postulante WHERE dni = ?";
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

    @Override
    public CarreraDao buscarEntidad(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
                    PostulanteTO tox = new PostulanteTO();
                    System.out.println("id facultad:");
                    tox.setDni(cs.next());
                    System.out.println("id area examen:");
                    tox.setNombre(cs.next());
                    System.out.println("nombre carrera:");
                    tox.setApellidoPat(cs.next());
                    
                    po.create(tox);
                    po.listarCarrerato(po.listarTodo());
                }
                case "R" ->
                    po.listarPostulantes(po.listarTodo());
                case "U" -> {
                    PostulanteTO tox;
                    System.out.println("Ingrese el DNI a Modificar:");
                    String dni=cs.next();
                    tox=po.buscarEntidad(dni);
                    System.out.println("Ingres Nuevo Nombre:");
                    tox.setNombre(cs.next());
                    System.out.println("Ingres Nuevo A. Paterno:");
                    tox.setApellidoPat(cs.next());                    
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el DNI del Registro que desea eliminar:");
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

}
