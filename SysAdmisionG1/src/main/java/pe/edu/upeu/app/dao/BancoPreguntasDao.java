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
import pe.edu.upeu.app.modelo.BancoPreguntasTO;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author INTEL
 */
public class BancoPreguntasDao implements BancoPreguntasDaoI{
    
    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(BancoPreguntasDao.class.getName());

    @Override
    public int create(BancoPreguntasTO d) {
         int rsId = 0;
        String[] returns = {"id_area"};
        String sql = "INSERT INTO banco_preguntas(id_area, pregunta) "
                + " values(?, ?)";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            //ps.setInt(++i, d.getIdBancoPreguntas());
            ps.setInt(++i, d.getIdArea());
            ps.setString(++i, d.getPregunta());

            rsId = ps.executeUpdate();// 0 no o 1 Csi commit
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
    public int update(BancoPreguntasTO d) {
System.out.println("actualizar d.getIdArea: " + d.getIdArea());
        int comit = 0;
        String sql = "UPDATE banco_preguntas SET "
                + "pregunta=?, "
                + "id_area=?"
                + "WHERE id_area=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(++i, d.getIdArea());
            //ps.setInt(++i, d.getIdBancoPreguntas());
            ps.setString(++i, d.getPregunta());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(String id) throws Exception {
  int comit = 0;
        String sql = "DELETE FROM banco_preguntas WHERE id_area = ?";
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
        BancoPreguntasDao po = new BancoPreguntasDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    BancoPreguntasTO tox = new BancoPreguntasTO();
                    System.out.println("Ingrese el ID de Area:");
                    tox.setIdArea(cs.nextInt());
                    System.out.println("Ingrese la Nueva Pregunta:");
                    tox.setPregunta(cs.next());
                    po.create(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "R" ->
                    po.listarPostulantes(po.listarTodo());
                case "U" -> {
                    BancoPreguntasTO tox;
                    System.out.println("Ingrese el ID de Area a Modificar:");
                    String id_area =cs.next();
                    tox=po.buscarEntidad(id_area);
                    System.out.println("Ingrese Nueva Pregunta:");
                    tox.setPregunta(cs.next());
                                       
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el Id Area del Registro que desea eliminar:");
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
      public void listarPostulantes(List<BancoPreguntasTO> lista) {
        System.out.println("IdBP\tUIdArea\t\t\tPregunta\t\t\t.");
        for (BancoPreguntasTO a : lista) {
            System.out.println(a.getIdBancoPreguntas()+ "\t" + a.getIdArea()+ "\t\t\t"
                    + a.getPregunta());
        }
    }

    @Override
    public List<BancoPreguntasTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BancoPreguntasTO> listarTodo() {
        List<BancoPreguntasTO> listarEntidad = new ArrayList();
        String sql = "SELECT * FROM banco_preguntas";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BancoPreguntasTO cli = new BancoPreguntasTO();
                cli.setIdBancoPreguntas(rs.getInt("id_bp"));
                cli.setIdArea(rs.getInt("id_area"));
                cli.setPregunta(rs.getString("pregunta"));

                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }
 
    @Override
    public BancoPreguntasTO buscarEntidad(String id_area) {
BancoPreguntasTO cli = new BancoPreguntasTO();
        String sql = "SELECT *FROM banco_preguntas WHERE id_area=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id_area);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setIdBancoPreguntas(rs.getInt("id_bp"));
                cli.setIdArea(rs.getInt("id_area"));
                cli.setPregunta(rs.getString("pregunta"));
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

    @Override
    public String buscarModalidadExamen(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
