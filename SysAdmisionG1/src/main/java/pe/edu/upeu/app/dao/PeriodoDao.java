/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

/**
 *
 * @author Data_Science
 */
import pe.edu.upeu.app.modelo.PeriodoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import pe.edu.upeu.app.conexion.ConnS;
import pe.edu.upeu.app.modelo.ComboBoxOption;
import pe.edu.upeu.app.modelo.PostulanteTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author Data_Science
 */
public abstract class PeriodoDao implements PeriodoDaoI {

    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(PostulanteDao.class.getName());

    @Override
    public int create(PeriodoTO d) {
        int rsId = 0;
        String[] returns = {"dni"};
        String sql = "INSERT INTO postulante(id_carrera, id_periodo, dni, "
                + " nombre, estado) ";

        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);

            ps.setInt(++i, d.getIdPeriodo());
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getEstado());
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
    public int update(PostulanteTO d) {
        System.out.println("actualizar d.getNombre: " + d.getNombre());
        int comit = 0;
        String sql = "UPDATE postulante SET "
                + "nombre=?, "
                + "estado=?, "
                + "WHERE nombre=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(++i, d.getIdPeriodo());
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getEstado());

            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<PostulanteTO> listarTodo() {
        List<PostulanteTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.nombre as nombreperiodo, c.nombrecarrera "
                + "FROM postulante po, periodo p, carrera c "
                + "WHERE p.id_periodo = po.id_periodo and po.id_carrera = c.id_carrera";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PostulanteTO cli = new PostulanteTO();
                cli.setNombre(rs.getString("nombre"));
                cli.setEstado(rs.getString("estado"));
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
        String sql = "DELETE FROM postulante WHERE nombre = ?";
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
        PostulanteDao po = new PostulanteDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    PostulanteTO tox = new PostulanteTO();
                    System.out.println("Ingres Nombre:");
                    tox.setNombre(cs.next());
                    System.out.println("Ingrese Estado (Activo, Desactivo):");
                    tox.setEstado(cs.next());
                    System.out.println("Ingrese el Perido(1=2023-1):");
                    tox.setIdPeriodo(cs.nextInt());
                    po.create(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "R" ->
                    po.listarPostulantes(po.listarTodo());
                case "U" -> {
                    PostulanteTO tox;
                    System.out.println("Ingrese el Nombre a Modificar:");
                    String nombre=cs.next();
                    tox=po.buscarEntidad(nombre);
                    System.out.println("Ingres Nuevo Nombre:");
                    tox.setNombre(cs.next());
                   
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el Nombre del Registro que desea eliminar:");
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

    public void listarPostulantes(List<PostulanteTO> lista) {
        System.out.println("DNI\t\tNombre\t\t\tApellidos\t\t\tCarrera Post.");
        for (PostulanteTO p : lista) {
            System.out.println(p.getNombre() + "\t\t\t"
                    + p.getEstado());
        }
    }
 

    @Override
    public PostulanteTO buscarEntidad(String dni) {
        PostulanteTO cli = new PostulanteTO();
        String sql = "SELECT po.*, p.nombre as nombreperiodo,  "
                + "FROM postulante po, periodo p "
                + "WHERE p.id_periodo";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {

                cli.setNombre(rs.getString("nombre"));

                cli.setEstado(rs.getString("estado"));
              
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }
}


