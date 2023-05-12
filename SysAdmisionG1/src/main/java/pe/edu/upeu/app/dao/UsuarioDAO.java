/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

/**
 *
 * @author User
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.upeu.app.modelo.Usuario;

public class UsuarioDAO {

    
    private final String SELECT_ALL = "SELECT * FROM usuario";
    private final String SELECT_BY_ID = "SELECT * FROM usuario WHERE id_usuario=?";
    private final String INSERT = "INSERT INTO usuario(id_usuario, user, clave, estado, perfil) VALUES(?,?,?,?,?)";

    
    private final Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUser(rs.getString("user"));
                usuario.setClave(rs.getString("clave"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setPerfil(rs.getString("perfil"));
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                } 
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }

        return usuarios;
    }

    
    public Usuario getUsuarioById(int id) {
        Usuario usuario = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUser(rs.getString("user"));
                usuario.setClave(rs.getString("clave"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setPerfil(rs.getString("perfil"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }

        return usuario;
    }

    
    public int insertUsuario(Usuario usuario) {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(INSERT);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getUser());
            stmt.setString(3, usuario.getClave());
            stmt.setString(4, usuario.getEstado());
            stmt.setString(5, usuario.getPerfil());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
