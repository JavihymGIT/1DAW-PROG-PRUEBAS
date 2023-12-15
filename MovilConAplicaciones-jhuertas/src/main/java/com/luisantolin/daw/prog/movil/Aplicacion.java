package com.luisantolin.daw.prog.movil;

public class Aplicacion {
    final private String nombre;
    final private int    usoDeRam;
    final private int    usoDeHd;
    private boolean      running;

    public Aplicacion( String nombre, int usoDeRam, int usoDeHd ) {
        this.nombre = nombre;
        this.usoDeHd = usoDeHd;
        this.usoDeRam = usoDeRam;
        this.running = false;
    }

    public void run() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getHd() {
        return usoDeHd;
    }

    public int getRam() {
        return usoDeRam;
    }

    public boolean isRunning() {
        return running;
    }
}
