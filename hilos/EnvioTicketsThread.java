package hilos;

import modelo.EventoUniversitario;
import modelo.actividades.Actividad;
import modelo.Inscripcion;
import java.util.List;

public class EnvioTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("\n[HILO-TICKETS] Iniciando envío en segundo plano...");


        List<Actividad> actividades = evento.getActividades();

        for (Actividad act : actividades) {
            for (Inscripcion insc : act.getInscripciones()) {

                if (insc.getEstado().equals("Confirmada") && insc.getTicket() != null) {
                    insc.getTicket().enviarTicket();
                }
            }
        }
        System.out.println("[HILO-TICKETS] Finalizó el envío de todos los tickets.");
    }
}