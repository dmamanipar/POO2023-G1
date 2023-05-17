
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Data_Science
 */
public class FacultadDao {

    private Connection conn;

    public FacultadDao() {
        try {
            conn = DriverManager.getConnection("a", "usuario", "contraseña");
        } catch (SQLException e) {
            System.out.println("Error al conectarse : " + e.getMessage());
        }
    }

    public List<FacultadDao> obtenerFacultades() {
        List<FacultadDao> facultades = new ArrayList<>();
        String sql = "SELECT  FROM facultad";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                FacultadDao facultad = new FacultadDao();
                facultad.setId(rs.getInt("id_facultad"));
                facultad.setNombre(rs.getString("nombrefacultad"));
                facultades.add(facultad);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las facultades: " + e.getMessage());
        }
        return facultades;
    }

    public FacultadDao obtenerFacultadPorId(int id) {
        FacultadDao facultad = null;
        String sql = "SELECT  FROM facultad WHERE id_facultad = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                facultad = new FacultadDao();
                facultad.setId(rs.getInt("id_facultad"));
                facultad.setNombre(rs.getString("nombrefacultad"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la facultad: " + e.getMessage());
        }
        return facultad;
    }

    public void crearFacultad(FacultadDao facultad) {
        String sql = "INSERT INTO facultad (nombrefacultad) VALUES (?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, facultad.getNombre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al crear la facultad: " + e.getMessage());
        }
    }

    private String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setNombre(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
