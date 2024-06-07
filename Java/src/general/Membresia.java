package general;

import javax.swing.*;
import java.sql.*;

/**
 * La clase Membresia maneja las operaciones relacionadas con las membresías de los clientes.
 */
public class Membresia extends Cliente {

    // Variables o propiedades de la clase
    private int id_tipo_membresia;
    private String fecha_inicio;
    private String fecha_fin;
    private int asistencias;
    private int dias_total;

    // Constructor de la clase
    public Membresia() {
        id_tipo_membresia = 0;
        fecha_inicio = "";
        fecha_fin = "";
        asistencias = 0;
        dias_total = 0;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Métodos setter de membresia
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public void setIdTipoMembresia(int id_tipo) {
        this.id_tipo_membresia = id_tipo;
    }

    public void setFechaInicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFechaFin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public void setDiasTotal(int dias_total) {
        this.dias_total = dias_total;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Métodos getter de membresia
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public int getIdTipoMembresia() {
        return id_tipo_membresia;
    }

    public String getFechaInicio() {
        return fecha_inicio;
    }

    public String getFechaFin() {
        return fecha_fin;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public int getDiasTotal() {
        return dias_total;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Insertar membresía
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public boolean setMembresia(int cedula) {
        String sql = "INSERT INTO membresia(cedula, id_tipo, date_ini, date_end, numero_asistencias, dias_total) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            ps.setInt(2, this.getIdTipoMembresia());
            ps.setString(3, this.getFechaInicio());
            ps.setString(4, this.getFechaFin());
            ps.setInt(5, this.getAsistencias());
            ps.setInt(6, this.getDiasTotal());

            int n = ps.executeUpdate();
            return n > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Buscar membresía
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public String[] buscarMembresia(int id) {
        String[] registros = new String[3];
        String sql = "SELECT * FROM tipo WHERE id_tipo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                registros[0] = rs.getString(2);
                registros[1] = String.valueOf(rs.getFloat(3));

                JOptionPane.showMessageDialog(null, "Membresia Encontrada");
                return registros;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Membresia con el id " + id + " no existe");
        return registros;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Actualizar membresía
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public void updateMembresia(int id, double costo) {
        String sql = "UPDATE tipo SET costo = ? WHERE id_tipo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, costo);
            ps.setInt(2, id);

            int a = ps.executeUpdate();
            if (a > 0) {
                JOptionPane.showMessageDialog(null, "Precio Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "El precio no se pudo actualizar");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
