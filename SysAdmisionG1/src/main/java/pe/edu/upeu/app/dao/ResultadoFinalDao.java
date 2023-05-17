package pe.edu.upeu.app.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.ResultadoFinalTO;
import pe.edu.upeu.app.util.ErrorLogger;

import java.util.logging.Level;

public class ResultadoFinalDao implements ResultadoFinalDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(ResultadoFinalDao.class.getName());

    @Override
    public int create(ResultadoFinalTO d) {
        int rsId = 0;
        String[] returns = {"dni"};
        String sql = "INSERT INTO resultado_final(id_postulante, id_periodo, id_carrera, dni, "
                + " punto_conocimiento, punto_entrevista, eval_psicologica) "
                + " values(?, ?, ?, ?, ?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdPostulante());
            ps.setInt(++i, d.getIdPeriodo());
            ps.setInt(++i, d.getIdCarrera());
            ps.setString(++i, d.getDni());
            ps.setDouble(++i, d.getPuntoConocimiento());
            ps.setDouble(++i, d.getPuntoEntrevista());
            ps.setInt(++i, d.getEvalPsicologica());
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
    public int update(ResultadoFinalTO d) {
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
            ps.setDouble(++i, d.getPuntoConocimiento());
            ps.setDouble(++i, d.getPuntoEntrevista());
            ps.setInt(++i, d.getIdPostulante());
            ps.setInt(++i, d.getIdPeriodo());
            ps.setInt(++i, d.getIdCarrera());
            ps.setInt(++i, d.getEvalPsicologica());
            ps.setString(++i, d.getDni());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<ResultadoFinalTO> listarTodo() {
        List<ResultadoFinalTO> listarEntidad = new ArrayList();
        String sql = "SELECT re.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM resultado_final re, periodo p, carrera c "
                + "WHERE p.id_periodo = re.id_periodo and re.id_carrera = c.id_carrera";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ResultadoFinalTO cli = new ResultadoFinalTO();
                cli.setDni(rs.getString("dni"));
                cli.setPuntoConocimiento(rs.getInt("punto_conocimiento"));
                cli.setPuntoEntrevista(rs.getInt("punto_entrevista"));
                cli.setEvalPsicologica(rs.getInt("eval_psicologica"));
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
    public int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM resultado final WHERE dni = ?";
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
        ResultadoFinalDao re = new ResultadoFinalDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    ResultadoFinalTO tox = new ResultadoFinalTO();
                    System.out.println("Ingrese el DNI:");
                    tox.setDni(cs.next());
                    System.out.println("Ingres el punto conocimiento:");
                    tox.setPuntoConocimiento(cs.nextDouble());
                    System.out.println("Ingres el punto psicologica:");
                    tox.setPuntoEntrevista(cs.nextDouble());
                    System.out.println("Ingres el punto psicologica:");
                    tox.setEvalPsicologica(cs.nextInt());
                    System.out.println("Ingrese Carrera(1=Sistemas, 2=Contabilidad):");
                    tox.setIdCarrera(cs.nextInt());
                    System.out.println("Ingrese el Perido(1=2023-1):");
                    tox.setIdPeriodo(cs.nextInt());
                    System.out.println("Ingrese el Postulante:");
                    tox.setIdPostulante(cs.nextInt());
                    re.create(tox);
                    re.listarResultadoFinal(re.listarTodo());
                }
                case "R" ->
                    re.listarResultadoFinal(re.listarTodo());
                case "U" -> {
                    ResultadoFinalTO tox;
                    System.out.println("Ingrese el DNI a Modificar:");
                    int dni = cs.nextInt();
                    tox = re.buscarEntidad(dni);
                    System.out.println("Ingres Nuevo punto conocimiento:");
                    tox.setPuntoConocimiento(cs.nextDouble());
                    System.out.println("Ingres Nuevo punto de entrevista:");
                    tox.setPuntoEntrevista(cs.nextDouble());
                    re.update(tox);
                    re.listarResultadoFinal(re.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el DNI del Registro que desea eliminar:");
                        re.delete(cs.nextInt());
                        re.listarResultadoFinal(re.listarTodo());
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

    public void listarResultadoFinal(List<ResultadoFinalTO> lista) {
        System.out.println("DNI\t\t\tPuntos\t\t\tCarrera Post.");
        for (ResultadoFinalTO p : lista) {
            System.out.println(p.getDni() + "\t"
                    + p.getPuntoConocimiento() + " " + p.getPuntoEntrevista() + "\t\t\t" + p.getEvalPsicologica());
        }
    }

    @Override
    public List<ResultadoFinalTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultadoFinalTO buscarEntidad(int id) {
        ResultadoFinalTO cli = new ResultadoFinalTO();
        String sql = "SELECT re.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM postulante po, periodo p, carrera c "
                + "WHERE p.id_periodo = re.id_periodo and re.id_carrera = c.id_carrera"
                + " and re.dni=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setDni(rs.getString("dni"));
                cli.setPuntoConocimiento(rs.getInt("punto_conocimiento"));
                cli.setPuntoEntrevista(rs.getInt("punto_entrevista"));
                cli.setEvalPsicologica(rs.getInt("eval_psicologica"));
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
}