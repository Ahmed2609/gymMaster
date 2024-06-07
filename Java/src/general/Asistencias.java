package general;

import Mysql.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * La clase Asistencias gestiona las asistencias de los clientes a través de
 * interacciones con una base de datos MySQL.
 *
 * Esta clase permite insertar nuevas asistencias y actualizar el estado de las
 * membresías de los clientes.
 */
public class Asistencias {

    //Hacemos la conexion 
    public Conexion cn = new Conexion(); // instanciamos nuestra clase conexion
    public Connection conexion = cn.getConexion();
    private ResultSet rs;
    private PreparedStatement ps;
    private PreparedStatement ps2;
    private String sql;

    //variables del objeto tipo asistencias
    private String date;

    /**
     * Constructor de la clase Asistencias. Inicializa la variable date a una
     * cadena vacía.
     */
    public Asistencias() {
        date = "";
    }

    /**
     * Establece la fecha de la asistencia.
     *
     * @param date La fecha de la asistencia.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Obtiene la fecha de la asistencia.
     *
     * @return La fecha de la asistencia.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Inserta una asistencia para el cliente con la cédula proporcionada.
     * Actualiza el número de asistencias y los días restantes en la membresía
     * del cliente.
     *
     * @param cedula La cédula del cliente.
     * @return true si la asistencia se insertó correctamente, false en caso
     * contrario.
     */
    public boolean insertAsistencia(int cedula) {
        String tipo;
        int diasTotal;
        int diasAsistencias;

        try {
            sql = "select tipo, dias_total, numero_asistencias\n"
                    + "from membresia, tipo\n"
                    + "where tipo.id_tipo = membresia.id_tipo\n"
                    + "and cedula = " + cedula + " ";

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                tipo = rs.getString(1);
                diasTotal = rs.getInt(2);
                diasAsistencias = rs.getInt(3);

                if (tipo.equals("Especial")) {
                    sql = "select cedula from clientes where cedula = " + cedula;
                    ps = conexion.prepareStatement(sql);
                    rs = ps.executeQuery();

                    // para verificar si el cliente existe antes de insertar la asistencia
                    if (rs.next() && diasTotal > 0) {
                        sql = "insert into asistencia(cedula, date) values(?, ?)";
                        String sql2 = "update membresia set numero_asistencias = ?, dias_total = ? where cedula = " + cedula;

                        ps = conexion.prepareStatement(sql);
                        ps2 = conexion.prepareStatement(sql2);

                        ps.setInt(1, cedula);
                        ps.setString(2, getDate());

                        ps2.setInt(1, (diasAsistencias + 1));
                        ps2.setInt(2, (diasTotal - 1));

                        ps.executeUpdate();
                        ps2.executeUpdate();

                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Tus días se agotaron");
                        return false;
                    }
                } else {
                    sql = "select cedula from clientes where cedula = " + cedula;
                    ps = conexion.prepareStatement(sql);
                    rs = ps.executeQuery();

                    // para verificar si el cliente existe antes de insertar la asistencia
                    if (rs.next()) {
                        sql = "insert into asistencia(cedula, date) values(?, ?)";
                        String sql2 = "update membresia set numero_asistencias = " + (diasAsistencias + 1) + " where cedula = " + cedula;

                        ps = conexion.prepareStatement(sql);
                        ps2 = conexion.prepareStatement(sql2);

                        ps.setInt(1, cedula);
                        ps.setString(2, getDate());
                        ps.executeUpdate();
                        ps2.executeUpdate();

                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
}
