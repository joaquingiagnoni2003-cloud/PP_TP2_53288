package modelo.actividades;
import certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends Actividad implements Certificable{
    private boolean requiereNotebook;


    public Taller (int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo);

        this.requiereNotebook = requiereNotebook;

    }
    @Override
    public double calcularCostoMateriales () {
        if (this.requiereNotebook) {
            return 5000.0;

        } else {
            return 2000.0;

        }


    }
    @Override
    public String getTipo () {
        return "Taller";

    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return " CONSTANCIA: " + ENTIDAD_EMISORA + " certifica la asistencia de " +
                estudiante.getNombre() + " al " + getTipo() + " '" + getTitulo() + "'.";
    }
}


