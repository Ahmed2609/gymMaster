package general;

import Mysql.Conexion;

import javax.swing.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * La clase Rutina maneja la inserción, actualización y eliminación de rutinas en la base de datos.
 */
public class Rutina {

    private int id_rutina;
    private String nivel_rutina;

    // Conexión a la base de datos
    private final Conexion cn;
    private final Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;

    /**
     * Constructor de la clase Rutina.
     */
    public Rutina() {
        id_rutina = 0;
        nivel_rutina = "";
        cn = new Conexion();
        conexion = cn.getConexion();
    }

    /**
     * Establece el ID de la rutina.
     *
     * @param rutina ID de la rutina.
     */
    public void setIdRutina(int rutina) {
        this.id_rutina = rutina;
    }

    /**
     * Establece el ID de la rutina basándose en el nivel de la rutina.
     *
     * @param nivel Nivel de la rutina.
     */
    public void setIdRutina(String nivel) {
        try {
            sql = "SELECT id_rutina FROM rutinas WHERE nivel = ?";
            ps = conexion.prepareStatement(sql);
            ps.setString(1, nivel);
            rs = ps.executeQuery();

            if (rs.next()) {
                this.id_rutina = rs.getInt("id_rutina");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Establece el nivel de la rutina.
     *
     * @param nivel Nivel de la rutina.
     */
    public void setNivelRutina(String nivel) {
        this.nivel_rutina = nivel;
    }

    /**
     * Obtiene el ID de la rutina.
     *
     * @return ID de la rutina.
     */
    public int getIdRutina() {
        return this.id_rutina;
    }

    /**
     * Obtiene el nivel de la rutina.
     *
     * @return Nivel de la rutina.
     */
    public String getNivelRutina() {
        return this.nivel_rutina;
    }

    /**
     * Inserta una nueva rutina en la base de datos.
     *
     * @param nivel    Nivel de la rutina.
     * @param img      Archivo de imagen de la rutina.
     * @param longitud Longitud del archivo de imagen.
     */
    public void insertarRutina(String nivel, FileInputStream img, int longitud) {
        sql = "INSERT INTO rutinas (nivel, archivo) VALUES (?, ?)";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, nivel);
            ps.setBlob(2, img, longitud);

            int a = ps.executeUpdate();
            if (a > 0) {
                JOptionPane.showMessageDialog(null, "Rutina insertada con éxito");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Elimina una rutina de la base de datos.
     */
    public void eliminarRutina() {
        sql = "DELETE FROM rutinas WHERE id_rutina = ?";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, id_rutina);

            int a = ps.executeUpdate();
            if (a > 0) {
                JOptionPane.showMessageDialog(null, "Rutina eliminada con éxito");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Actualiza los datos de una rutina en la base de datos.
     *
     * @param nivel  Nuevo nivel de la rutina.
     * @param img    Nuevo archivo de imagen de la rutina.
     * @param largo  Longitud del nuevo archivo de imagen.
     */
    public void actualizarRutina(String nivel, FileInputStream img, int largo) {
        sql = "UPDATE rutinas SET nivel = ?, archivo = ? WHERE id_rutina = ?";

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, nivel);
            ps.setBlob(2, img, largo);
            ps.setInt(3, id_rutina);

            int a = ps.executeUpdate();
            if (a > 0) {
                JOptionPane.showMessageDialog(null, "Rutina actualizada con éxito");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
