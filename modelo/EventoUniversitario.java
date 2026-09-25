package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import modelo.actividades.Curso;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private Sala sala;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;


    private List <Actividad> actividades;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(int idActividad, String titulo, int cupo, String tipo) {
        if (tipo.equals("Charla")) {
            Charla nuevaCharla = new Charla(idActividad, titulo, cupo, "Orador a confirmar");
            this.actividades.add(nuevaCharla);

        } else if (tipo.equals("Taller")) {
            Taller nuevoTaller = new Taller(idActividad, titulo, cupo, true);
            this.actividades.add(nuevoTaller);

        } else if (tipo.equals("Curso")) {

            Curso nuevoCurso = new Curso(idActividad, titulo, cupo, 1);
            this.actividades.add(nuevoCurso);

        } else {
            System.out.println("Error: El tipo debe ser Charla, Taller o Curso.");
        }
    }


    public double calcularCostoEstimado() {
        if (this.gratuito) {
            return 0.0;
        }

        double totalAcumulado = this.costoBase;
        for (Actividad act : this.actividades) {
            totalAcumulado += act.calcularCostoMateriales();
        }
        return totalAcumulado * 1.21;
    }

    public void mostrarDatos() {
        System.out.println("Evento [ID: " + id + " | Titulo: " + titulo +
                " | costoBase: $" + costoBase + " | gratuito: " + gratuito +
                " | costo Final Estimado $" + this.calcularCostoEstimado() + "]");

        if (this.sala != null) {
            System.out.println(" Sala asignada: " + this.sala.getNombre());
        }

        if (!this.actividades.isEmpty()) {
            System.out.println("Actividades:");
            for (Actividad act : this.actividades) {
                act.mostrarIdentificacion();
                act.mostrarInscripciones();
            }
        }
    }

    public boolean persistirEvento() {
        String nombreArchivo = this.id + ".dat";

        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);

            ObjectOutputStream salida = new ObjectOutputStream(fileOut);

            salida.writeObject(this);
            salida.close();
            fileOut.close();

            System.out.println(" Evento guardado exitosamente en: " + nombreArchivo);
            return true;

        } catch (FileNotFoundException e) {
            System.out.println(" Error: No se pudo crear el archivo de destino.");
            return false;

        } catch (IOException e) {
            System.out.println(" Error al serializar el evento: " + e.getMessage());
            return false;
        }
    }

    public EventoUniversitario recuperarEvento(String idBuscado) {
        String nombreArchivo = idBuscado + ".dat";

        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);

            ObjectInputStream entrada = new ObjectInputStream(fileIn);

            EventoUniversitario eventoRecuperado = (EventoUniversitario) entrada.readObject();
            entrada.close();
            fileIn.close();

            System.out.println(" Evento " + idBuscado + " recuperado exitosamente.");
            return eventoRecuperado;

        } catch (FileNotFoundException e) {
            System.out.println(" Archivo no encontrado.");
            return null;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Error al deserializar el evento.");
            return null;
        }
    }

    public static int getCantidadEventos() { return cantidadEventos; }
    public String getTitulo() { return titulo; }


    public List <Actividad> getActividades() {
        return actividades;
    }

        public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> actividadesFiltradas = new ArrayList<>();

        for (Actividad act : this.actividades) {
            if (tipo.isInstance(act)) {
                actividadesFiltradas.add(tipo.cast(act));
            }
        }
        return actividadesFiltradas;
    }


    public double calcularCostoMateriales(List<? extends Actividad> actividades) {
        double costoTotal = 0.0;


        for (Actividad act : actividades) {
            costoTotal += act.calcularCostoMateriales();
        }
        return costoTotal;

    }
}
