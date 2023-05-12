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
import pe.edu.upeu.app.modelo.Resultado_finalTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author HP
 */
public class Resultado_finalDao implements Resultado_finalDaoI {

    ConnS instance=ConnS.getInstance();
    Connection connection=instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    ErrorLogger log=new ErrorLogger(Resultado_finalDao.class.getName());
    
    
   @Override
    public int create(Resultado_finalTO d) {
        int rsId = 0;
        String[] returns = {"dni"};
        String sql = "INSERT INTO postulante(id_result_final, id_postulante, id_periodo, id_carrera, dni, "
                + " punto_conocimiento, punto_entrevista, eval_psicologica) "
                + " values(?, ?, ?, ?, ?, ?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdResult_final());
            ps.setInt(++i, d.getIdPostulante());
            ps.setInt(++i, d.getIdPeriodo());
            ps.setInt(++i, d.getIdCarrera());
            ps.setString(++i, d.getDni());
            ps.setString(++i, d.getPunto_conocimiento());
            ps.setString(++i, d.getPunto_entrevista());
            ps.setInt(++i, d.getEval_psicologica());
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
    public int update(Resultado_finalTO d) {
        System.out.println("actualizar d.getDniruc: " + d.getDni());
        int comit = 0;
        String sql = "UPDATE postulante SET "
                + "punto_conocimiento=?, "
                + "punto_entrevista=?, "
                + "eval_psicologica=?, "
                + "id_result_final=?, "
                + "id_postulante=?, "
                + "id_periodo=?, "
                + "id_carrera=? "
                + "WHERE dni=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getPunto_conocimiento());
            ps.setString(++i, d.getPunto_entrevista());
            ps.setInt(++i, d.getIdResult_final());
            ps.setInt(++i, d.getIdPostulante());
            ps.setInt(++i, d.getIdPeriodo());
            ps.setInt(++i, d.getIdCarrera());
            ps.setInt(++i, d.getEval_psicologica());
            ps.setString(++i, d.getDni());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<Resultado_finalTO> listarTodo() {
        List<Resultado_finalTO> listarEntidad = new ArrayList();
        String sql = "SELECT re.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM resultado_final re, periodo p, carrera c "
                + "WHERE p.id_periodo = re.id_periodo and re.id_carrera = c.id_carrera";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Resultado_finalTO cli = new Resultado_finalTO();
                cli.setDni(rs.getString("dni"));
                cli.setPunto_conocimiento(rs.getString("punto_conocimiento"));
                cli.setPunto_entrevista(rs.getString("punto_entrevista"));
                cli.setEval_psicologica(rs.getString("eval_psicologica"));
                cli.setIdCarrera(rs.getInt("id_carrera"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));
                cli.setIdPostulante(rs.getInt("id_postulante"));
                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));
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
        String sql = "DELETE FROM resultado final WHERE dni = ?";
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
        Resultado_finalDao re = new Resultado_finalDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    Resultado_finalTO tox = new Resultado_finalTO();
                    System.out.println("Ingrese el DNI:");
                    tox.setDni(cs.next());
                    System.out.println("Ingres el punto conocimiento:");
                    tox.setPunto_conocimiento(cs.next());
                    System.out.println("Ingres el punto psicologica:");
                    tox.setPunto_entrevista(cs.next());
                    System.out.println("Ingres el punto psicologica:");
                    tox.setEval_psicologica(cs.next());
                    System.out.println("Ingrese Carrera(1=Sistemas, 2=Contabilidad):");
                    tox.setIdCarrera(cs.nextInt());
                    System.out.println("Ingrese el Perido(1=2023-1):");
                    tox.setIdPeriodo(cs.nextInt());
                    System.out.println("Ingrese el Postulante:");
                    tox.setIdPostulante(cs.nextInt());
                    re.create(tox);
                    re.listarResultado_final(Listre.listarTodo());
                }
                case "R" ->
                    re.listarResultado_final(re.listarTodo());
                case "U" -> {
                    Resultado_finalTO tox;
                    System.out.println("Ingrese el DNI a Modificar:");
                    String dni=cs.next();
                    tox=re.buscarEntidad(dni);
                    System.out.println("Ingres Nuevo punto conocimiento:");
                    tox.setPunto_conocimiento(cs.next());
                    System.out.println("Ingres Nuevo punto de entrevista:");
                    tox.setPunto_entrevista(cs.next());                    
                    re.update(tox);
                    re.listarResultado_final(re.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el DNI del Registro que desea eliminar:");
                        re.delete(cs.next());
                        re.listarResultado_final(re.listarTodo());
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

    public void listarResultado_final(List<Resultado_finalTO> lista) {
        System.out.println("DNI\t\t\tPuntos\t\t\tCarrera Post.");
        for (Resultado_finalTO p : lista) {
            System.out.println(p.getDni() + "\t"
                    + p.getPuntoConocimiento() + " " + p.getPuntoEntrevista() + "\t\t\t" + p.getCarrera());
        }
    }

    @Override
    public List<Resultado_finalTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Resultado_finalTO buscarEntidad(String dni) {
        Resultado_finalTO cli = new Resultado_finalTO();
        String sql = "SELECT re.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM postulante po, periodo p, carrera c "
                + "WHERE p.id_periodo = re.id_periodo and re.id_carrera = c.id_carrera"
                + " and re.dni=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setDni(rs.getString("dni"));
                cli.setPunto_conocimiento(rs.getString("punto_conocimiento"));
                cli.setPunto_entrevista(rs.getString("punto_entrevista"));
                cli.setEval_psicologica(rs.getString("eval_psicologica"));
                cli.setIdCarrera(rs.getInt("id_carrera"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));
                cli.setIdPostulante(rs.getInt("id_postulante"));
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

    @Override
    public List<Resultado_finalTO> ListarTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

}
