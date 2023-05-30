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
        String sql = "INSERT INTO usuario(user, clave, estado, perfil) "
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
        String sql = "UPDATE usuario SET "
                + "user=?, "
                + "clave=?, "
                + "estado=?, "
                + "perfil=? where id_usuario=? ";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getUser());
            ps.setString(++i, d.getClave());
            ps.setString(++i, d.getEstado());
            ps.setString(++i, d.getPerfil());
            ps.setInt(++i, d.getIdUsuario());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
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

    @Override
    public List<UsuarioTO> listarTodo() {
        List<UsuarioTO> listarEntidad = new ArrayList();
        String sql = "SELECT * "
                + "FROM usuario  ";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO cli = new UsuarioTO();
                cli.setUser(rs.getString("user"));
                cli.setClave(rs.getString("clave"));
                cli.setEstado(rs.getString("estado"));
                cli.setPerfil(rs.getString("perfil"));
                cli.setIdUsuario(rs.getInt("id_usuario"));
                listarEntidad.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarEntidad;
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
                    System.out.println("Ingrese el ID USUARIO a Modificar:");
                    int user = cs.nextInt();
                    tox = po.buscarEntidad(user);
                    System.out.println("Ingres Nuevo clave:");
                    tox.setClave(cs.next());
                    System.out.println("Ingres Nuevo Perfilo:");
                    tox.setPerfil(cs.next());
                    po.update(tox);
                    po.listarPostulantes(po.listarTodo());
                }
                case "D" -> {
                    try {
                        System.out.println("Ingrese el ID USUARIO del Registro que desea eliminar:");
                        po.delete(cs.nextInt());
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
        System.out.println("Id\t\tUSUARIO\t\tCLAVE\t\t\tESTADO\t\t\tPERFIL\t\tID_USUARIO");
        for (UsuarioTO p : lista) {
            System.out.println(p.getIdUsuario()+"\t\t"+p.getUser() + "\t\t" + p.getClave() + "\t\t\t"
                    + p.getEstado() + "\t\t\t" + p.getPerfil() + "\t\t\t" + p.getIdUsuario());
        }
    }

    @Override
    public List<UsuarioTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UsuarioTO buscarEntidad(int iduser) {
        UsuarioTO cli = new UsuarioTO();
        String sql = "SELECT  * "
                + "FROM usuario "
                + "WHERE id_usuario=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, iduser);
            rs = ps.executeQuery();
            if (rs.next()) {
                cli.setUser(rs.getString("user"));
                cli.setClave(rs.getString("clave"));
                cli.setEstado(rs.getString("estado"));
                cli.setPerfil(rs.getString("perfil"));
                cli.setIdUsuario(rs.getInt("id_usuario"));
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
