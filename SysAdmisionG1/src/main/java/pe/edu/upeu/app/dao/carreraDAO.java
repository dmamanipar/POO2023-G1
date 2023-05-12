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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class carreraDAO {

    // Cambiar estos valores según tu configuración de base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/mi_basedatos";
    private static final String USER = "mi_usuario";
    private static final String PASSWORD = "mi_contraseña";

    // Consulta SQL para obtener todas las carreras
    private static final String SELECT_ALL_CARRERAS = "SELECT * FROM carrera";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_CARRERAS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_carrera = rs.getInt("id_carrera");
                int id_facultad = rs.getInt("id_facultad");
                int id_area_examen = rs.getInt("id_area_examen");
                String nombrecarrera = rs.getString("nombrecarrera");

                // Procesar los resultados aquí
                System.out.printf("%d %d %d %s\n", id_carrera, id_facultad, id_area_examen, nombrecarrera);
            }
        } catch (SQLException ex) {
            System.err.println("Error de SQL: " + ex.getMessage());
        }
    }
}