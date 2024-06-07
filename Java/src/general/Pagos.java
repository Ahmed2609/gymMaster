package general;

import javax.swing.JOptionPane;
import java.sql.*;

/**
 * La clase Pagos maneja las operaciones relacionadas con los pagos de membresías de los clientes.
 */
public class Pagos extends Membresia {

    // Variables o propiedades de la clase
    private int id_membresia;
    private float costo;
    private float total;

    // Constructor de la clase
    public Pagos() {
        id_membresia = 0;
        costo = 0;
        total = 0;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Métodos setter de pagos
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public void setIdMembresia(int id_membresia) {
        this.id_membresia = id_membresia;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Métodos getter de pagos
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public int getIdMembresia() {
        return id_membresia;
    }

    public float getCosto() {
        return costo;
    }

    public float getTotal() {
        return total;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
        Insertar pago
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public boolean setPago(int cedula) {
        String sql = "INSERT INTO pagos(cedula, id_membresia, costo, total) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            ps.setInt(2, this.getIdMembresia());
            ps.setFloat(3, this.getCosto());
            ps.setFloat(4, this.getTotal());

            int n = ps.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Pago insertado con éxito");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "El pago no se insertó");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------------------------------
        Buscar pagos
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public String[] buscarPagos(int cedula) {
        String[] registros = new String[7];
        String sqlCliente = "SELECT * FROM clientes WHERE cedula = ?";
        String sqlPago = "CALL sp_pagos(?)";

        try (PreparedStatement psCliente = conexion.prepareStatement(sqlCliente);
             PreparedStatement psPago = conexion.prepareStatement(sqlPago)) {
             
            psCliente.setInt(1, cedula);
            ResultSet rsCliente = psCliente.executeQuery();

            if (rsCliente.next()) {
                psPago.setInt(1, cedula);
                ResultSet rsPago = psPago.executeQuery();

                if (rsPago.next()) {
                    registros[0] = String.valueOf(rsPago.getInt("cedula"));
                    registros[1] = rsPago.getString("nombres");
                    registros[2] = rsPago.getString("apellidos");
                    registros[3] = rsPago.getString("tipo");
                    registros[4] = rsPago.getString("date_ini");
                    registros[5] = rsPago.getString("date_end");
                    registros[6] = rsPago.getString("total");

                    JOptionPane.showMessageDialog(null, "Pago encontrado");
                    return registros;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Cliente no existe");
        return registros;
    }

    /*----------------------------------------------------------------------------------------------------------------------------------------
        Eliminar pago
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    public void eliminarPago(int cedula) {
        String sqlCliente = "SELECT * FROM clientes WHERE cedula = ?";
        String sqlPago = "CALL sp_pagos(?)";
        String sqlEliminarMembresia = "DELETE FROM membresia WHERE id_membresia = ?";

        try (PreparedStatement psCliente = conexion.prepareStatement(sqlCliente);
             PreparedStatement psPago = conexion.prepareStatement(sqlPago);
             PreparedStatement psEliminarMembresia = conexion.prepareStatement(sqlEliminarMembresia)) {
             
            psCliente.setInt(1, cedula);
            ResultSet rsCliente = psCliente.executeQuery();

            if (rsCliente.next()) {
                psPago.setInt(1, cedula);
                ResultSet rsPago = psPago.executeQuery();

                if (rsPago.next()) {
                    int id_membresia = rsPago.getInt("id_membresia");
                    int id_pago = rsPago.getInt("id_pago");

                    psEliminarMembresia.setInt(1, id_membresia);
                    psEliminarMembresia.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Pago eliminado con éxito");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cliente no existe");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
