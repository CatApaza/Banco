package Hilos;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Hilo1 implements Runnable {
    private final String nombreCaja;
    private final double tiempoAtencion; // Tiempo en minutos
    private final double tiempoDescanso; // Tiempo en minutos
    private final JLabel etiquetaCaja;

    public Hilo1(String nombreCaja, double tiempoAtencion, double tiempoDescanso, JLabel etiquetaCaja) {
        this.nombreCaja = nombreCaja;
        this.tiempoAtencion = tiempoAtencion;
        this.tiempoDescanso = tiempoDescanso;
        this.etiquetaCaja = etiquetaCaja;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SwingUtilities.invokeLater(() -> etiquetaCaja.setText(nombreCaja + ": Atendiendo..."));
                TimeUnit.MINUTES.sleep((long) tiempoAtencion);
                SwingUtilities.invokeLater(() -> etiquetaCaja.setText(nombreCaja + ": Descansando..."));
                TimeUnit.MINUTES.sleep((long) tiempoDescanso);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
