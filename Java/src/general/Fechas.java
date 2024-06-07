package general;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * La clase Fechas gestiona la obtención y formato de fechas actuales.
 */
public class Fechas {

    private int year;
    private int day;
    private int month;

    Calendar fecha = new GregorianCalendar();

    /**
     * Constructor por defecto de la clase Fechas. Inicializa los atributos de
     * día, mes y año a 0.
     */
    public Fechas() {
        int day = 0;
        int month = 0;
        int year = 0;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
    Metodos setter de fecha
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Establece el día actual.
     */
    public void setDay() {
        this.day = fecha.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Establece el mes actual.
     */
    public void setMonth() {
        this.month = fecha.get(Calendar.MONTH);
    }

    /**
     * Establece el año actual.
     */
    public void setYear() {
        this.year = fecha.get(Calendar.YEAR);
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
    Metodos getter de fecha
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Obtiene el día actual.
     *
     * @return El día actual.
     */
    public int getDay() {
        return day;
    }

    /**
     * Obtiene el mes actual.
     *
     * @return El mes actual.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Obtiene el año actual.
     *
     * @return El año actual.
     */
    public int getYear() {
        return year;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
    Metodos para obtener fecha en formato año/mes/día
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Obtiene la fecha actual en formato latino (día/mes/año).
     *
     * @return Una cadena que representa la fecha en formato latino.
     */
    public String fechaLatina() {
        String fecha2;
        this.day = fecha.get(Calendar.DAY_OF_MONTH);
        this.month = fecha.get(Calendar.MONTH) + 1;  // Los meses en Calendar empiezan en 0
        this.year = fecha.get(Calendar.YEAR);

        fecha2 = String.valueOf(day + "/" + month + "/" + year);
        return fecha2;
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------
    Metodos para obtener fecha en formato año/mes/día
    ------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Obtiene la fecha actual en formato europeo (año-mes-día).
     *
     * @return Una cadena que representa la fecha en formato europeo.
     */
    public String fechaEuropa() {
        String fecha2;

        this.day = fecha.get(Calendar.DAY_OF_MONTH);
        this.month = fecha.get(Calendar.MONTH) + 1;  // Los meses en Calendar empiezan en 0
        this.year = fecha.get(Calendar.YEAR);

        fecha2 = String.valueOf(year + "-" + month + "-" + day);
        return fecha2;
    }

    /*
    public static void main(String[] args){
        Fechas fecha = new Fechas();
    
        System.out.println(fecha.fechaLatina());
        System.out.println(fecha.fechaEuropa());
        
        fecha.setDay();
        System.out.println(fecha.getDay());
    }
     */
}
