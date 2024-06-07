package general;

import Mysql.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * La clase Cliente gestiona la información de los clientes y permite realizar
 * operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en una base de datos
 * MySQL.
 */
public class Cliente {

    private int cedula;
    private String nombres;
    private String apellidos;
    private String dir;
    private String fecha_nac;
    private String sexo;
    private String telefono;
    private String celular;

    // Variables para los métodos
    Conexion cn = new Conexion();
    Connection conexion = cn.getConexion();
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    /**
     * Constructor por defecto de la clase Cliente. Inicializa los atributos con
     * valores predeterminados.
     */
    public Cliente() {
        super();
        cedula = 0;
        nombres = "";
        apellidos = "";
        dir = "";
        fecha_nac = "";
        sexo = "";
        telefono = "";
        celular = "";
    }

    /**
     * Establece la cédula del cliente.
     *
     * @param ci La cédula del cliente.
     */
    public void setCi(int ci) {
        this.cedula = ci;
    }

    /**
     * Establece los nombres del cliente.
     *
     * @param nombres Los nombres del cliente.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Establece los apellidos del cliente.
     *
     * @param apellidos Los apellidos del cliente.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param dir La dirección del cliente.
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * Establece la fecha de nacimiento del cliente.
     *
     * @param fecha_nac La fecha de nacimiento del cliente.
     */
    public void setFechaNacimiento(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    /**
     * Establece el sexo del cliente.
     *
     * @param sexo El sexo del cliente.
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Establece el teléfono del cliente.
     *
     * @param telefono El teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Establece el celular del cliente.
     *
     * @param celular El celular del cliente.
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Obtiene la cédula del cliente.
     *
     * @return La cédula del cliente.
     */
    public int getCi() {
        return cedula;
    }

    /**
     * Obtiene los nombres del cliente.
     *
     * @return Los nombres del cliente.
     */
    public String getNombres() {
        return this.nombres;
    }

    /**
     * Obtiene los apellidos del cliente.
     *
     * @return Los apellidos del cliente.
     */
    public String getApellidos() {
        return this.apellidos;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return La dirección del cliente.
     */
    public String getDir() {
        return this.dir;
    }

    /**
     * Obtiene la fecha de nacimiento del cliente.
     *
     * @return La fecha de nacimiento del cliente.
     */
    public String getFechaNacimiento() {
        return this.fecha_nac;
    }

    /**
     * Obtiene el sexo del cliente.
     *
     * @return El sexo del cliente.
     */
    public String getSexo() {
        return this.sexo;
    }

    /**
     * Obtiene el teléfono del cliente.
     *
     * @return El teléfono del cliente.
     */
    public String getTelefono() {
        return this.telefono;
    }

    /**
     * Obtiene el celular del cliente.
     *
     * @return El celular del cliente.
     */
    public String getCelular() {
        return this.celular;
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @return true si el cliente se insertó correctamente, false en caso
     * contrario.
     */
    public boolean SetCliente() {
        try {
            sql = "insert into clientes(cedula, nombres, apellidos, direccion, fecha_nacimiento, sexo, telefono, celular)"
                    + "values(?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(sql);
            ps.setInt(1, this.getCi());
            ps.setString(2, this.getNombres());
            ps.setString(3, this.getApellidos());
            ps.setString(4, this.getDir());
            ps.setString(5, this.getFechaNacimiento());
            ps.setString(6, this.getSexo());
            ps.setString(7, this.getTelefono());
            ps.setString(8, this.getCelular());

            int n = ps.executeUpdate();

            if (n > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

    /**
     * Busca un cliente en la base de datos por su cédula.
     *
     * @param cedula La cédula del cliente a buscar.
     * @return Un array de strings con los datos del cliente encontrado, o un
     * array vacío si no se encuentra.
     */
    public String[] buscarClientes(int cedula) {
        String[] registros = new String[9];

        try {
            sql = "select * from clientes where cedula = " + cedula;
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                registros[0] = String.valueOf(rs.getInt("cedula"));
                registros[1] = rs.getString("nombres");
                registros[2] = rs.getString("apellidos");
                registros[3] = rs.getString("direccion");
                registros[4] = rs.getString("fecha_nacimiento");
                registros[5] = rs.getString("sexo");
                registros[6] = rs.getString("telefono");
                registros[7] = rs.getString("celular");

                JOptionPane.showMessageDialog(null, "Cliente encontrado");
                return registros;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Cliente no existe");
        return registros;
    }

    /**
     * Actualiza la información de un cliente en la base de datos.
     *
     * @param cedula La cédula del cliente a actualizar.
     * @return true si el cliente se actualizó correctamente, false en caso
     * contrario.
     */
    public boolean updateCliente(int cedula) {
        try {
            sql = "update clientes set nombres = ?, apellidos = ?, direccion = ?,"
                    + "fecha_nacimiento = ?, sexo = ?, telefono = ?, celular = ? where cedula = " + cedula;

            ps = conexion.prepareStatement(sql);

            ps.setString(1, this.getNombres());
            ps.setString(2, this.getApellidos());
            ps.setString(3, this.getDir());
            ps.setString(4, this.getFechaNacimiento());
            ps.setString(5, this.getSexo());
            ps.setString(6, this.getTelefono());
            ps.setString(7, this.getCelular());

            int n = ps.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Datos actualizados con éxito");
                return true;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Los datos no se actualizaron");
        return false;
    }

    /**
     * Elimina un cliente de la base de datos por su cédula.
     *
     * @param cedula La cédula del cliente a eliminar.
     * @return true si el cliente se eliminó correctamente, false en caso
     * contrario.
     */
    public boolean eliminarCliente(int cedula) {
        try {
            sql = "delete from clientes where cedula = " + cedula;
            ps = conexion.prepareStatement(sql);

            int n = ps.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado");
                return true;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        JOptionPane.showMessageDialog(null, "No se eliminó el cliente");
        return false;
    }

}
