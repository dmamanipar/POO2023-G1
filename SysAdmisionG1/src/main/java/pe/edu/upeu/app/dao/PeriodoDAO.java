/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

/**
 *
 * @author User
 */
import java.sql.*;
import java.util.*;
import pe.edu.upeu.app.modelo.PeriodoTO;

public class PeriodoDAO {
    private final Connection conn;

    public PeriodoDAO(Connection conn) {
        this.conn = conn;
    }

    public void createPeriodo(PeriodoTO periodo) throws SQLException {
        String sql = "INSERT INTO periodo (nombre, estado) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, periodo.getNombre());
        stmt.setString(2, periodo.getEstado());
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating periodo failed, no rows affected.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                periodo.setIdPeriodo(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating periodo failed, no ID obtained.");
            }
        }
    }

    public PeriodoTO readPeriodo(int idPeriodo) throws SQLException {
        String sql = "SELECT * FROM periodo WHERE id_periodo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPeriodo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            PeriodoTO periodo = new PeriodoTO();
            periodo.setIdPeriodo(rs.getInt("id_periodo"));
            periodo.setNombre(rs.getString("nombre"));
            periodo.setEstado(rs.getString("estado"));
            return periodo;
        } else {
            return null;
        }
    }

    public List<PeriodoTO> readAllPeriodos() throws SQLException {
        String sql = "SELECT * FROM periodo";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<PeriodoTO> periodos = new ArrayList<>();
        while (rs.next()) {
            PeriodoTO periodo = new PeriodoTO();
            periodo.setIdPeriodo(rs.getInt("id_periodo"));
            periodo.setNombre(rs.getString("nombre"));
            periodo.setEstado(rs.getString("estado"));
            periodos.add(periodo);
        }
        return periodos;
    }

    public void updatePeriodo(PeriodoTO periodo) throws SQLException {
        String sql = "UPDATE periodo SET nombre = ?, estado = ? WHERE id_periodo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, periodo.getNombre());
        stmt.setString(2, periodo.getEstado());
        stmt.setInt(3, periodo.getIdPeriodo());
        stmt.executeUpdate();
    }

    public void deletePeriodo(int idPeriodo) throws SQLException {
        String sql = "DELETE FROM periodo WHERE id_periodo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPeriodo);
        stmt.executeUpdate();
    }
}

