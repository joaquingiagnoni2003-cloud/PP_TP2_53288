import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Sala;
import modelo.Inscripcion;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;
import modelo.actividades.Curso;
import certificacion.Certificable;
import excepciones.CupoExcedidoException;
import hilos.EnvioTicketsThread;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE EVENTOS UTN ===\n");


        Estudiante est1 = new Estudiante("LEG01", "María Gómez");

        Estudiante est2 = new Estudiante("LEG02", "Juan Pérez");

        Estudiante est3 = new Estudiante("LEG03", "Ana López");

        EventoUniversitario evento = new EventoUniversitario("EVT-1", "Semana de la Tecnología", 3000.0, false);
        evento.asignarSala(new Sala(101, "Auditorio Principal"));


        evento.crearActividad(1, "Charla de IA", 50, "Charla");

        evento.crearActividad(2, "Taller de Java", 1, "Taller");

        evento.crearActividad(3, "Curso de Base de Datos", 10, "Curso");

        evento.crearActividad(4, "Charla sobre Redes", 50, "Charla");

        System.out.println("--- EJERCICIO 1: EXCEPCIONES Y PERSISTENCIA ---");
        try {

            evento.getActividades().get(0).inscribir(est1);

            evento.getActividades().get(1).inscribir(est2);

            evento.getActividades().get(2).inscribir(est3);

            System.out.println("Intentando inscribir a María en el Taller (Cupo 1)...");
            evento.getActividades().get(1).inscribir(est1);

        } catch (CupoExcedidoException e) {
            System.out.println(" CASO FALLIDO CONTROLADO: " + e.getMessage());

        } finally {

            System.out.println("Ejecutando finally: Guardando evento en disco...");
            evento.persistirEvento();

            System.out.println("Leyendo evento desde el disco para confirmar...");
            EventoUniversitario recuperado = evento.recuperarEvento("EVT-1");
        }

        System.out.println("\n--- EJERCICIO 2: INTERFACES Y CERTIFICADOS ---");
        for (Actividad act : evento.getActividades()) {

            if (act instanceof Certificable) {
                Certificable actividadCertificable = (Certificable) act;

                for (Inscripcion insc : act.getInscripciones()) {
                    System.out.println(actividadCertificable.generarCertificado(insc.getEstudiante()));
                }
            }
        }

        System.out.println("\n--- EJERCICIO 3: GENERICS Y WILDCARDS ---");

        List listaCharlas = evento.filtrarActividadesPorTipo(Charla.class);

        List listaTalleres = evento.filtrarActividadesPorTipo(Taller.class);

        List listaCursos = evento.filtrarActividadesPorTipo(Curso.class);

        System.out.println(" Charlas creadas: " + listaCharlas.size());

        System.out.println(" Talleres creados: " + listaTalleres.size());

        System.out.println(" Cursos creados: " + listaCursos.size());


        System.out.println("Costo materiales Charlas: $" + evento.calcularCostoMateriales(listaCharlas));

        System.out.println("Costo materiales Talleres: $" + evento.calcularCostoMateriales(listaTalleres));

        System.out.println("Costo materiales Cursos: $" + evento.calcularCostoMateriales(listaCursos));

        System.out.println("\n--- EJERCICIO 4: HILOS Y CLASES ANIDADAS ---");

        evento.getActividades().get(0).getInscripciones().get(0).confirmarInscripcion();

        evento.getActividades().get(1).getInscripciones().get(0).confirmarInscripcion();

        System.out.println("Iniciando envío de tickets en segundo plano...");

        EnvioTicketsThread hiloTickets = new EnvioTicketsThread(evento);
        hiloTickets.start();

        System.out.println("\n--- RESUMEN FINAL (HILO PRINCIPAL) ---");

        evento.mostrarDatos();
        System.out.println("\n[HILO PRINCIPAL] Fin del main. Esperando a que el hilo secundario termine de enviar los tickets...");
    }
}