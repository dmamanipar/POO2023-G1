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
import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.modelo.PreguntasTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author acer
 */
public class PreguntasDao implements PreguntasDaoI{
     ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(PreguntasDao.class.getName());
    
    //Crear
     @Override
    public int create(PreguntasTO d) {
        int rsId = 0;
        String[] returns = {"dni"};
        String sql = "INSERT INTO preguntas(id_Bp, id_area_periodo "
                + " resultado, numero) "
                + " values(?, ?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdPreguntas());
            ps.setInt(++i, d.getIdBp());
            ps.setString(++i, d.getDni());
            ps.setString(++i, d.getIdAreaPeriodo());
            ps.setString(++i, d.getResultado());
            ps.setString(++i, d.getNumero());
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
    
    //Actualizar
    
     @Override
    public int update(PreguntasTO d) {
        System.out.println("actualizar d.getdni: " + d.getdni());
        int comit = 0;
        String sql = "UPDATE resultado de SET "
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
    
    
     @Override
    public List<PreguntasTO> listarTodo() {
        List<PreguntasTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.pregunta as nombrearea_periodo, b.bp "
                + "FROM pregunta po, area_periodo ap, bp b "
                + "WHERE p.id_areaperiodo = po.id_pregunta and po.id_bp = b.id_bp";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PreguntasTO cli = new PreguntasTO();
                cli.setDni(rs.getString("dni"));
                cli.setIdPregunta(rs.getInt("id_pregunta"));
                cli.setIdBp(rs.getInt("id_bp"));
                cli.setNombreAreaPeriodo(rs.getString("nombrearea_periodo"));
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }
    
    //Eliminar
     @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM pregunta WHERE dni = ?";
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
    
    //CRUD 
    public static void main(String[] args) {
        Scanner cs = new Scanner(System.in);
        PreguntasDao po = new PreguntasDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    PreguntasTO tox = new PreguntasTO();
                    System.out.println("Ingrese el DNI:");
                    tox.setDni(cs.next());
                    System.out.println("Ingrese el Perido(1=2023-1):");
                    tox.setIdPeriodo(cs.nextInt());
                    System.out.println("Ingrese Modalidad(E=Examen, PI=Primeros Puestos):");
                }
                case "R" ->
                    po.listarPregunta(po.listarTodo());
                case "U" -> {
                    PostulanteTO tox;
                    System.out.println("Ingrese el dni a Modificar:");
                    String dni=cs.next();
                    tox=po.buscarEntidad(dni);
                    System.out.println("Ingrese Area Periodo:");
                    tox.setAreaPeriodo(cs.next());
                    System.out.println("Ingrese Id pregunta:");
                    tox.setPregunta(cs.next());                    
                    po.update(tox);
                    po.listarPregunta(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el dni del Registro que desea eliminar:");
                        po.delete(cs.next());
                        po.listarPregunta(po.listarTodo());
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

    public void listarPregunta(List<PreguntasTO> lista) {
        System.out.println("BP\t\tArePeriodo\t\t\tResultado");
        for (PreguntasTO p : lista) {
            System.out.println(p.getBp() + "\t" + p.getResultado() + "\t\t\t");
        }
    }

    public PostulanteTO buscarEntidad(String dni) {
        PostulanteTO cli = new PostulanteTO();
        String sql = "SELECT po.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM postulante po, periodo p, carrera c "
                + "WHERE p.id_periodo = po.id_periodo and po.id_carrera = c.id_carrera"
                + " and po.dni=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setDni(rs.getString("dni"));
                cli.setModalidad(rs.getString("modalidad"));
                cli.setIdCarrera(rs.getInt("id_carrera"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));

                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));               
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }


    public void update(PostulanteTO tox) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     @Override
    public List<PreguntasTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     @Override
    public List<ModeloDataAutocomplet> listAutoComplet(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
     @Override
    public List<ComboBoxOption> listaModalidadExamen() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @return
     */
    @Override
    public List<ComboBoxOption> listarPeriodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoCompletCarrera(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String buscarModalidadExamen(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
