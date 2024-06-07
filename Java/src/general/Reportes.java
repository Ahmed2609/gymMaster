package general;

import Mysql.Conexion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase Reportes maneja la generación y visualización de reportes en formato PDF utilizando JasperReports.
 */
public class Reportes {

    private final Conexion conexion;

    /**
     * Constructor de la clase Reportes.
     */
    public Reportes() {
        this.conexion = new Conexion();
    }

    /**
     * Exporta un informe a un archivo PDF.
     *
     * @param ruta  Ruta del informe Jasper.
     * @param archi Nombre del archivo PDF a generar.
     */
    public void reportesPDF(String ruta, String archi) {
        try {
            JasperPrint informe = JasperFillManager.fillReport(ruta, null, conexion.getConexion());
            JasperExportManager.exportReportToPdfFile(informe, archi);
            JOptionPane.showMessageDialog(null, "Informe exportado con éxito a " + archi);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar documento: " + e.getMessage());
        }
    }

    /**
     * Muestra un informe del cliente específico en un visor de JasperReports.
     *
     * @param cedula Cédula del cliente.
     */
    public void reporteCliente(String cedula) {
        generarYMostrarReporte("Reportes\\reporte_nuevo_cliente.jasper", "Reporte cliente", "num_cedula", cedula);
    }

    /**
     * Muestra un informe de clientes filtrado por sexo en un visor de JasperReports.
     *
     * @param sexo Sexo de los clientes a filtrar.
     */
    public void reporteSexoClientes(String sexo) {
        generarYMostrarReporte("Reportes\\Clientes.jasper", "Reporte cliente por sexo", "input_sexo", sexo);
    }

    /**
     * Muestra un informe de clientes filtrado por edad en un visor de JasperReports.
     *
     * @param edad Edad de los clientes a filtrar.
     */
    public void reporteEdadClientes(String edad) {
        generarYMostrarReporte("Reportes\\cliente_edad.jasper", "Reporte cliente por edad", "input_edad", edad);
    }

    /**
     * Muestra un informe de ficha de seguimiento de un cliente específico en un visor de JasperReports.
     *
     * @param cedula Cédula del cliente.
     */
    public void reporteFichaCliente(String cedula) {
        generarYMostrarReporte("Reportes\\ficha_seguimiento.jasper", "Reporte ficha de seguimiento", "num_cedula", cedula);
    }

    /**
     * Muestra un informe de pagos de un cliente específico en un visor de JasperReports.
     *
     * @param cedula Cédula del cliente.
     */
    public void reportePagoCliente(String cedula) {
        generarYMostrarReporte("Reportes\\pagos_cliente.jasper", "Reporte pagos del cliente", "num_cedula", cedula);
    }

    /**
     * Método privado que genera y muestra un informe en un visor de JasperReports.
     *
     * @param ruta        Ruta del informe Jasper.
     * @param titulo      Título de la ventana del visor.
     * @param parametro   Nombre del parámetro a pasar al informe.
     * @param valor       Valor del parámetro.
     */
    private void generarYMostrarReporte(String ruta, String titulo, String parametro, String valor) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put(parametro, valor);
            JasperPrint informe = JasperFillManager.fillReport(ruta, parametros, conexion.getConexion());
            JasperViewer ventanaVisor = new JasperViewer(informe, false);
            ventanaVisor.setTitle(titulo);
            ventanaVisor.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el informe: " + e.getMessage());
        }
    }
}
