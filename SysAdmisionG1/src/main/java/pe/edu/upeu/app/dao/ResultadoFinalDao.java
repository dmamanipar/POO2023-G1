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

import pe.edu.upeu.app.modelo.ResultadoFinalTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author HP
 */
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
        String sql = "INSERT INTO resultado_final ( id_postulante, id_periodo, id_carrera, dni, "
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
        System.out.println("actualizar d.getDni: " + d.getDni());
        int comit = 0;
        String sql = "UPDATE resultado_final SET "
                + "id_postulante=?, "
                + "id_periodo=?, "
                + "id_carrera=?, "
                + "punto_conocimiento=?, "
                + "punto_entrevista=?, "
                + "eval_psicologica=? "
                + "WHERE id_result_final=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);

            ps.setInt(++i, d.getIdPostulante());
            ps.setInt(++i, d.getIdPeriodo());
            ps.setInt(++i, d.getIdCarrera());
            ps.setDouble(++i, d.getPuntoConocimiento());
            ps.setDouble(++i, d.getPuntoEntrevista());
            ps.setInt(++i, d.getEvalPsicologica());
            ps.setInt(++i, d.getIdResultfinal());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<ResultadoFinalTO> listarTodo() {
        List<ResultadoFinalTO> listarEntidad = new ArrayList();
        String sql = "select * from resultado_final ";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ResultadoFinalTO cli = new ResultadoFinalTO();
                cli.setIdResultfinal(rs.getInt("id_result_final"));
                cli.setIdPostulante(rs.getInt("id_postulante"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));
                cli.setIdCarrera(rs.getInt("id_carrera"));
                cli.setDni(rs.getString("dni"));
                cli.setPuntoConocimiento(rs.getDouble("punto_conocimiento"));
                cli.setPuntoEntrevista(rs.getDouble("punto_entrevista"));
                cli.setEvalPsicologica(rs.getInt("eval_psicologica"));
                
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
        String sql = "DELETE FROM resultado_final WHERE dni = ?";
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
        ResultadoFinalDao re = new ResultadoFinalDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    ResultadoFinalTO tox = new ResultadoFinalTO();
                    //System.out.println("Ingrese el resultado final:");
                    //tox.setIdResultfinal(cs.nextInt());
                    System.out.println("Ingrese el Postulante:");
                    tox.setIdPostulante(cs.nextInt());
                    System.out.println("Ingrese el Perido(1=2023-1):");
                    tox.setIdPeriodo(cs.nextInt());
                    System.out.println("Ingrese Carrera(1=Sistemas, 2=Contabilidad):");
                    tox.setIdCarrera(cs.nextInt());
                    System.out.println("Ingrese el DNI:");
                    tox.setDni(cs.next());
                    System.out.println("Ingres el punto conocimiento:");
                    tox.setPuntoConocimiento(cs.nextDouble());
                    System.out.println("Ingres el punto entrevista:");
                    tox.setPuntoEntrevista(cs.nextDouble());
                    System.out.println("Ingres el punto psicologica:");
                    tox.setEvalPsicologica(cs.nextInt());
                                  re.create(tox);
                    re.listarResultado_final(re.listarTodo());
                }
                case "R" ->
                    re.listarResultado_final(re.listarTodo());
                case "U" -> {
                    ResultadoFinalTO tox;
                    System.out.println("Ingrese el id result final a Modificar:");
                    int dni = cs.nextInt();
                    tox = re.buscarEntidad(dni);
      
                    System.out.println("Ingres Nuevo punto conocimiento:");
                    tox.setPuntoConocimiento(cs.nextDouble());
                    System.out.println("Ingres Nuevo punto de entrevista:");
                    tox.setPuntoEntrevista(cs.nextDouble());
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

    public void listarResultado_final(List<ResultadoFinalTO> lista) {
        System.out.println("Id_result_final\t\tId_postulante\t\tId_periodo\t\tId_carrera\t\tDNI\t\tPunto_conocimiento\t\tPunto_entrevista\t\teval_psicologica");
        for (ResultadoFinalTO p : lista) {
            System.out.println(p.getIdResultfinal() +"\t\t\t" + p.getIdPostulante() +"\t\t\t" + p.getIdPeriodo() + "\t\t\t" + p.getIdCarrera() + "\t\t\t" + p.getDni() + "\t\t\t"
                    + p.getPuntoConocimiento() + " \t\t\t" + p.getPuntoEntrevista() + "\t\t\t\t" + p.getEvalPsicologica());
        }
    }

    @Override
    public ResultadoFinalTO buscarEntidad(int dni) {
        ResultadoFinalTO cli = new ResultadoFinalTO();
        String sql = "select * from resultado_final where id_result_final=?";
        
    try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setDni(rs.getString("dni"));
                cli.setPuntoConocimiento(rs.getDouble("punto_conocimiento"));
                cli.setPuntoEntrevista(rs.getDouble("punto_entrevista"));
                cli.setEvalPsicologica(rs.getInt("eval_psicologica"));
                cli.setIdCarrera(rs.getInt("id_carrera"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));
                cli.setIdPostulante(rs.getInt("id_postulante"));
                cli.setIdResultfinal(rs.getInt("id_result_final"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }

}
