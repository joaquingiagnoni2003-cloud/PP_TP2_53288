package modelo;

import java.io.Serializable;

public class Sala implements Serializable{
    private int Id;
    private String nombre;

        public Sala(int Id, String nombre) {
            this.Id = Id;
            this.nombre = nombre;


        }
    public int getId () {
            return Id;

    }

    public String getNombre() {
            return nombre;

    }
}
