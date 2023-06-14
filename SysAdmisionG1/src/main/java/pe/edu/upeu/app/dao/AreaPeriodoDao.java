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
import pe.edu.upeu.app.modelo.AreaPeriodoTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author ACER
 */
public class AreaPeriodoDao implements AreaPeriodoDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(AreaPeriodoDao.class.getName());

    @Override
    public int create(AreaPeriodoTO d) {
        int rsId = 0;
        String[] returns = {"id_area"};
        String sql = "INSERT INTO area_periodo(id_area, id_periodo) "
                + " values(?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdArea());
            ps.setInt(++i, d.getIdPeriodo());

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
    public int update(AreaPeriodoTO d) {
        System.out.println("actualizar d.getIdAreaPeriodo: " + d.getIdAreaPeriodo());
        int comit = 0;
        String sql = "UPDATE area_periodo SET "
                + "id_area=?, "
                + "id_periodo=? "
                + "WHERE id_area_periodo=?";
        int i = 0;
        try {

            ps = connection.prepareStatement(sql);

            ps.setInt(++i, d.getIdArea());
            ps.setInt(++i, d.getIdPeriodo());
            ps.setInt(++i, d.getIdAreaPeriodo());

            comit = ps.executeUpdate();
            System.err.println("hh:" + sql);
            System.err.println("hh:" + d.getIdAreaPeriodo());
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM area_periodo WHERE id_area_periodo = ?";
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
        AreaPeriodoDao po = new AreaPeriodoDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    AreaPeriodoTO tx = new AreaPeriodoTO();
                    System.out.println("Ingrese el area:");
                    tx.setIdArea(cs.nextInt());
                    System.out.println("Ingrese el periodo, 1 = 2023-i, 2 = 2023-ii :");
                    tx.setIdPeriodo(cs.nextInt());

                    po.create(tx);
                    po.listarAreaPeriodo(po.listarTodo());
                }
                case "R" -> {
                    po.listarAreaPeriodo(po.listarTodo());

                }
                case "U" -> {
                    AreaPeriodoTO tox;
                    System.out.println("Ingrese el idAreaperiodo a Modificar:");
                    int IdAreaPeriodo = cs.nextInt();
                    tox = po.buscarEntidad(IdAreaPeriodo);

                    System.out.println("Ingres Nuevo Area:");
                    tox.setIdArea(cs.nextInt());

                    System.out.println("Ingres Nuevo Periodo:");
                    tox.setIdPeriodo(cs.nextInt());

                    po.update(tox);
                    po.listarAreaPeriodo(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el id area periodo del Registro que desea eliminar:");
                        po.delete(cs.nextInt());
                        po.listarAreaPeriodo(po.listarTodo());
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

    @Override
    public List<AreaPeriodoTO> listarTodo() {
        List<AreaPeriodoTO> listarEntidad = new ArrayList();
        String sql = "SELECT ar.*,a.nombrearea, p.nombre FROM area_periodo ar, areas a , periodo p "
                + "WHERE ar.id_area = a.id_area and ar.id_periodo = p.id_periodo;";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AreaPeriodoTO cli = new AreaPeriodoTO();
                cli.setIdAreaPeriodo(rs.getInt("id_area_periodo"));
                cli.setIdArea(rs.getInt("id_area"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));
                cli.setNombreArea(rs.getString("nombrearea"));
                cli.setNombre(rs.getString("nombre"));

                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
    }

    public void listarAreaPeriodo(List<AreaPeriodoTO> lista) {
        System.out.println("iDAreaPeriodo\t\tArea\t\tPeriodo\t\tNombreArea\t\tNombrePeriodo");
        for (AreaPeriodoTO a : lista) {
            System.out.println(+a.getIdAreaPeriodo() + "\t\t\t" + a.getIdArea() + "\t\t" + a.getIdPeriodo()
                    + "\t\t\t" + a.getNombreArea() + "\t\t\t" + a.getNombre());

        }
    }

    @Override
    public AreaPeriodoTO buscarEntidad(int IdAreaPerido) {
        AreaPeriodoTO cli = new AreaPeriodoTO();
        String sql = "SELECT * "
                + "FROM area_periodo "
                + "WHERE id_area_periodo=?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, IdAreaPerido);
            rs = ps.executeQuery();
            if (rs.next()) {

                cli.setIdAreaPeriodo(rs.getInt("id_area_periodo"));
                cli.setIdArea(rs.getInt("id_area"));
                cli.setIdPeriodo(rs.getInt("id_periodo"));

                //cli.setNombreModalidad(buscarModalidadExamen(rs.getString("modalidad")));               
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }

}