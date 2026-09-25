package modelo;

import java.time.LocalDate;

public class Inscripcion {
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private TicketDeAcceso ticket;

    public Inscripcion(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.fecha = LocalDate.now();
        this.estado = "Pendiente";
    }


    public void confirmarInscripcion() {
        if (this.estado.equals("Pendiente")) {
            this.estado = "Confirmada";

            this.ticket = new TicketDeAcceso("TICKET-" + estudiante.getLegajo() + "-" + fecha.getDayOfYear());
        }
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public String getEstado() {
        return estado;
    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }


    public class TicketDeAcceso {
        private String idTicket;


        TicketDeAcceso(String idTicket) {
            this.idTicket = idTicket;
        }

        public void enviarTicket() {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Error enviando ticket.");
            }
            System.out.println(" [TICKET ENVIADO] " + estudiante.getNombre() + " | ID: " + idTicket);
        }
    }
}