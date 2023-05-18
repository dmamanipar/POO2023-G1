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
import pe.edu.upeu.app.modelo.PreguntasTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author acer
 */
public class PreguntasDao implements PreguntasDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(PreguntasDao.class.getName());

    //Crear
    @Override
    public int create(PreguntasTO d) {
        int rsId = 0;
        String[] returns = {"id_bp"};
        String sql = "INSERT INTO preguntas(id_bp, id_area_periodo, "
                + " resultado, numero) "
                + " values(?, ?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);

            ps.setInt(++i, d.getIdBp());
            ps.setInt(++i, d.getIdAreaPeriodo());
            ps.setString(++i, d.getResultado());
            ps.setInt(++i, d.getNumero());
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
        System.out.println("actualizar d.getIdPregunta: " + d.getIdPregunta());
        int comit = 0;
        String sql = "UPDATE preguntas SET "
                + "id_bp=?,"
                + "id_area_periodo=?, "
                + "resultado=?, "
                + "numero=? "
                + "WHERE id_pregunta=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            
            ps.setInt(++i, d.getIdBp());
            ps.setInt(++i, d.getIdAreaPeriodo());
            ps.setString(++i, d.getResultado());
            ps.setInt(++i, d.getNumero());
            ps.setInt(++i, d.getIdPregunta());
            
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<PreguntasTO> listarTodo() {
        List<PreguntasTO> listarPreguntas = new ArrayList();
        String sql = "SELECT * FROM preguntas";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PreguntasTO cli = new PreguntasTO();
                cli.setIdPregunta(rs.getInt("id_pregunta"));
                cli.setIdBp(rs.getInt("id_bp"));

                cli.setIdAreaPeriodo(rs.getInt("id_area_periodo"));
                cli.setResultado(rs.getString("resultado"));

                //cli.setIdNombreAreaPeriodo(rs.getString("nombrearea_periodo"));
                cli.setNumero(rs.getInt("numero"));

                listarPreguntas.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarPreguntas;
    }

    //Eliminar
    @Override
    public int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM preguntas WHERE id_pregunta = ? ";
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
                    System.out.println("Ingrese el bp:");
                    tox.setIdBp(cs.nextInt());
                    System.out.println("Ingrese el areaperiodo:");
                    tox.setIdAreaPeriodo(cs.nextInt());
                    System.out.println("Ingrese el resultado:");
                    tox.setResultado(cs.next());
                    System.out.println("Ingrese el numero:");
                    tox.setNumero(cs.nextInt());
                    po.create(tox);
                    po.listarPregunta(po.listarTodo());
                }
                case "R" ->
                    po.listarPregunta(po.listarTodo());
                case "U" -> {
                    PreguntasTO tox;
                    System.out.println("Ingrese el id pregiunta a Modificar:");
                    int IdPregunta=cs.nextInt();
                    tox = po.buscarEntidad(IdPregunta);
                    System.out.println("Ingrese id BP:");
                    tox.setIdBp(cs.nextInt());
                    System.out.println("Ingrese AREA periodo:");
                    tox.setIdAreaPeriodo(cs.nextInt());
                    System.out.println("Ingrese resultado:");
                    tox.setResultado(cs.next());
                    System.out.println("Ingrese numero:");
                    tox.setNumero(cs.nextInt());
                    
                    po.update(tox);
                    po.listarPregunta(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el id pregunta que desea eliminar:");
                        po.delete(cs.nextInt());
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
        System.out.println("idpregunta\t\t\tbp\t\t\tArePeriodo\t\tResultado\t\tNumero");
        for (PreguntasTO p : lista) {
            System.out.println(p.getIdPregunta()+ "\t\t\t" + p.getIdBp() + "\t\t\t\t\t" + p.getIdAreaPeriodo() + "\t\t\t" + p.getResultado() + "\t\t\t\t\t" + p.getNumero());
        }
    }

    @Override
    public PreguntasTO buscarEntidad(int id_pregunta) {
        PreguntasTO cli = new PreguntasTO();
        String sql = "SELECT * "
                + "FROM preguntas "
                + "WHERE id_pregunta=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id_pregunta);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setIdPregunta(rs.getInt("id_pregunta"));
                cli.setIdBp(rs.getInt("id_bp"));
                
                cli.setIdAreaPeriodo(rs.getInt("id_area_periodo"));
                cli.setResultado(rs.getString("resultado"));
                cli.setNumero(rs.getInt("numero"));

                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));               
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
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
