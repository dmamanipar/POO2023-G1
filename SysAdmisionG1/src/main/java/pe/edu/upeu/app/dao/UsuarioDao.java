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
import pe.edu.upeu.app.modelo.UsuarioTO;
import pe.edu.upeu.app.util.ErrorLogger;
/**
 *
 * @author Jesus
 */
public class UsuarioDao implements UsuarioDaoI {
    ConnS instance = ConnS.getInstance();
    Connection connection = instance.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    ErrorLogger log = new ErrorLogger(UsuarioDao.class.getName());

    @Override
    public int create(UsuarioTO d) {
        int rsId = 0;
        String[] returns = {"user"};
        String sql = "INSERT INTO usuario(user, clave, estado,perfil "
                + " values(?, ?, ?, ?);";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setString(++i, d.getUser());
            ps.setString(++i, d.getClave());
            ps.setString(++i, d.getEstado());
            ps.setString(++i, d.getPerfil());
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
    public int update(UsuarioTO d) {
        System.out.println("actualizar d.getUserruc: " + d.getUser());
        int comit = 0;
        String sql = "UPDATE postulante SET "
                + "user=?, "
                + "clave=?, "
                + "estado=?, "
                + "perfil=?, ";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getUser());
            ps.setString(++i, d.getClave());
            ps.setString(++i, d.getEstado());
            ps.setString(++i, d.getPerfil());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public List<UsuarioTO> listarTodo() {
        List<UsuarioTO> listarEntidad = new ArrayList();
        String sql = "SELECT po.*, p.user as user "
                + "FROM usuario u "
                + "WHERE p.id_usuario";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO cli = new UsuarioTO();
                cli.setUser(rs.getString("user"));
                cli.setClave(rs.getString("clave"));
                cli.setEstado(rs.getString("estado"));
                cli.setPerfil(rs.getString("perfil"));
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
        String sql = "DELETE FROM usuario WHERE user = ?";
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
        UsuarioDao po = new UsuarioDao();

        int i = 0;
        String opcion = "R";
        String mensajeOpciones = "Menu de Opciones\nR=Reportar\nC=Crear\nU=Update\nD=Delete\nX=Salir";
        do {
            switch (opcion) {
                case "C" -> {
                    UsuarioTO tox = new UsuarioTO();
                    System.out.println("Ingrese el User:");
                    tox.setUser(cs.next());
                    System.out.println("Ingres Clave:");
                    tox.setClave(cs.next());
                    System.out.println("Ingrese Estado:");
                    tox.setEstado(cs.next());
                    System.out.println("Ingrese Perfil:");
                    tox.setPerfil(cs.next());
                    po.create(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "R" ->
                    po.listarPostulantes(po.listarTodo());
                case "U" -> {
                    UsuarioTO tox;
                    System.out.println("Ingrese el Usuario a Modificar:");
                    String user=cs.next();
                    tox=po.buscarEntidad(user);
                    System.out.println("Ingres Nuevo Nombre:");
                    tox.setClave(cs.next());
                    System.out.println("Ingres Nuevo A. Paterno:");
                    tox.setPerfil(cs.next());                    
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el USER del Registro que desea eliminar:");
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

    public void listarPostulantes(List<UsuarioTO> lista) {
        System.out.println("USUARIO\t\tCLAVE\t\t\tESTADO\t\t\tPERFIL");
        for (UsuarioTO p : lista) {
            System.out.println(p.getUser() + "\t" + p.getClave() + "\t\t\t"
                    + p.getEstado() + "\t\t\t" + p.getPerfil());
        }
    }

    @Override
    public List<UsuarioTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UsuarioTO buscarEntidad(String user) {
        UsuarioTO cli = new UsuarioTO();
        String sql = "SELECT po.*, p.user as user "
                + "FROM usuario u "
                + "WHERE p.id_usuario";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, user);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setUser(rs.getString("user"));
                cli.setClave(rs.getString("clave"));
                cli.setEstado(rs.getString("estado"));
                cli.setPerfil(rs.getString("perfil"));
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


