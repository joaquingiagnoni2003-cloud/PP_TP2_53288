package modelo.actividades;

import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;
import modelo.Inscripcion;
import excepciones.CupoExcedidoException;
import java.io.Serializable;

public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO = 1;

    private List<Inscripcion> inscripciones = new ArrayList<>();

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (this.inscripciones.size() >= this.cupoMaximo) {
            throw new CupoExcedidoException(
                    "Error: No hay más cupos disponibles para la actividad " + this.titulo
            );
        }

        Inscripcion nuevaInscripcion = new Inscripcion(estudiante);
        this.inscripciones.add(nuevaInscripcion);
        return nuevaInscripcion;
    }

    public void mostrarInscripciones() {
        System.out.println("Inscriptos en '" + this.titulo + "':");

        for (Inscripcion i : this.inscripciones) {
            System.out.println("  - " + i.getEstudiante().getNombre());
        }
    }

    public final void mostrarIdentificacion() {
        System.out.println(
                "ID: " + this.id +
                        " | Actividad: " + this.titulo +
                        " | Tipo: " + this.getTipo()
        );
    }

    public abstract double calcularCostoMateriales();

    public abstract String getTipo();
}