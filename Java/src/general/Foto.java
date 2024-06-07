package general;

import Mysql.Conexion;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

/**
 * La clase Foto maneja la conversión de imágenes almacenadas como bytes en la base de datos
 * y la recuperación de estas imágenes desde la base de datos.
 */
public class Foto {
    
    private Conexion conexion;
    private Image data;

    /**
     * Constructor por defecto que inicializa la conexión a la base de datos.
     */
    public Foto() {
        conexion = new Conexion();
    }

    /**
     * Convierte una cadena de bytes en una imagen con extensión jpeg.
     *
     * @param bytes Arreglo de bytes que representa la imagen.
     * @return La imagen convertida.
     * @throws IOException Si ocurre un error durante la conversión.
     */
    private Image ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(bis);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            return reader.read(0, param);
        } else {
            throw new IOException("No se encontraron lectores de imágenes para el formato JPG.");
        }
    }

    /**
     * Recupera la imagen de la base de datos según el ID proporcionado.
     *
     * @param id El ID de la rutina cuyo archivo de imagen se desea recuperar.
     * @return La imagen recuperada o null si ocurre un error.
     */
    public Image recuperarfotos(String id) {
        try {
            String sql = "SELECT archivo FROM rutinas WHERE id_rutina = ?";
            PreparedStatement ps = conexion.getConexion().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                byte[] b = rs.getBytes("archivo");
                data = ConvertirImagen(b);
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return data;
    }
}
