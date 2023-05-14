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
import pe.edu.upeu.app.modelo.AreasTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author EP-Ing_Sist.-CALIDAD
 */
public class AreasDao implements AreasDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(AreasDao.class.getName());

    @Override
    public int create(AreasTO d) {
        int rsId = 0;
        String[] returns = {"idarea"};
        String sql = "INSERT INTO areas(id_area, nombrearea, siglas, )"
                + " values(?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdArea());
            ps.setString(++i, d.getNombreArea());
            ps.setString(++i, d.getSiglas());
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
    public int update(AreasTO d) {
        System.out.println("actualizar d.IdAreas: " + d.getidareas());
        int comit = 0;
       String sql = "UPDATE areas SET "
                + "id_area=?, "
                + "nombrearea=?, "
                + "siglas=?, ";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getNombreArea());
            ps.setString(++i, d.getSiglas());
            ps.setInt(++i, d.getIdArea());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<AreasTO> listarTodo() {
        List<AreasTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, n.id_area as nombrearea, s.siglas "
                + "FROM areas po, nombrearea n, siglas s "
                + "WHERE p.id_area = po.id_area";
        //Falta mostrar el contenido de la tabla
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AreasTO cli = new AreasTO();
                cli.setIdArea(rs.getInt("id_area"));
                cli.setNombreArea(rs.getString("nombrearea"));
                cli.setSiglas(rs.getString("Siglas"));
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
        String sql = "DELETE FROM areas WHERE idareas = ?";
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
        AreasDao po = new AreasDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    AreasTO tox = new AreasTO();
                    System.out.println("Ingrese Area:");
                    tox.setIdArea(cs.nextInt());
                    System.out.println("Ingres Nombre Area:");
                    tox.setNombreArea(cs.next());
                    System.out.println("Ingres sigla:");
                    tox.setSiglas(cs.next());
                    po.create(tox);
                    po.listarAreas(po.listarTodo());
                }
                case "R" ->
                    po.listarAreas(po.listarTodo());
                case "U" -> {
                    AreasTO tox;
                    System.out.println("Ingrese el nombre del area:");
                    String siglas=cs.next();
                    tox=po.buscarEntidad(siglas);
                    System.out.println("Ingres Nuevo Nombre:");
                    tox.setNombreArea(cs.next());
                    System.out.println("Ingres Nueva Sigla:");
                    tox.setSiglas(cs.next());                    
                    po.update(tox);
                    po.listarAreas(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el DNI del Registro que desea eliminar:");
                        po.delete(cs.next());
                        po.listarAreas(po.listarTodo());
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

    public void listarAreas(List<AreasTO> lista) {
        System.out.println("IdArea\t\tNombreArea\t\t\tSiglas.");
        for (AreasTO p : lista) {
            System.out.println(p.getIdArea() + "\t" + p.getNombreArea() + "\t\t\t"
                    + p.getSiglas());
        }
    }

    @Override
    public List<AreasTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public AreasTO buscarEntidad(String nombrearea) {
        AreasTO cli = new AreasTO();
        String sql = "SELECT po.*, n.nombrearea, s.siglas "
                + "FROM areas po, nombrearea n, siglas s "
                + "WHERE p.id_area = po.id_area";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, nombrearea);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setIdArea(rs.getInt("id_area"));
                cli.setNombreArea(rs.getString("nombrearea"));
                cli.setSiglas(rs.getString("siglas"));             
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }

}
