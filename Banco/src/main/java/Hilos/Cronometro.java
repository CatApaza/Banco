package Hilos;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Cronometro extends SwingWorker<Void, Void> {
    private final JLabel labelCaja;
    private final int tiempoAtencion; // Tiempo en segundos
    private final int tiempoDescanso; // Tiempo en segundos
    private int tiempoRestante;
    private boolean enDescanso;

    public Cronometro(JLabel labelCaja, int tiempoAtencion, int tiempoDescanso) {
        this.labelCaja = labelCaja;
        this.tiempoAtencion = tiempoAtencion;
        this.tiempoDescanso = tiempoDescanso;
        this.tiempoRestante = tiempoAtencion; // Inicia con el tiempo de atención
        this.enDescanso = false; // Inicialmente no está en descanso
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            // Actualiza la interfaz gráfica con el estado y tiempo restante
            SwingUtilities.invokeLater(() -> {
                String estado = enDescanso ? "Descansando" : "Atendiendo";
                labelCaja.setText(String.format("%s\nTiempo Restante: %d s", estado, tiempoRestante));
            });

            // Espera 1 segundo antes de la siguiente actualización
            TimeUnit.SECONDS.sleep(1);

            // Actualiza el tiempo restante
            tiempoRestante -= 1;

            if (tiempoRestante <= 0) {
                if (enDescanso) {
                    // Si está en descanso, reinicia el tiempo de atención
                    tiempoRestante = tiempoAtencion;
                    enDescanso = false;
                } else {
                    // Si no está en descanso, pasa al estado de descanso
                    tiempoRestante = tiempoDescanso;
                    enDescanso = true;
                }
            }
        }
    }
}
