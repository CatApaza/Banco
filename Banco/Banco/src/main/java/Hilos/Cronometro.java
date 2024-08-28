package Hilos;

import Interfaz.Banco;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Cronometro extends SwingWorker<Void, Void> {
    private final JLabel labelCaja;
    private final int tiempoAtencion;
    private final int tiempoDescanso;
    private int tiempoRestante;
    private boolean enDescanso;
    private final Banco banco;

    public Cronometro(JLabel labelCaja, int tiempoAtencion, int tiempoDescanso, Banco banco) {
        this.labelCaja = labelCaja;
        this.tiempoAtencion = tiempoAtencion;
        this.tiempoDescanso = tiempoDescanso;
        this.tiempoRestante = tiempoAtencion; // Inicia con el tiempo de atención
        this.enDescanso = false; // Inicialmente no está en descanso
        this.banco = banco;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            // Actualiza la interfaz gráfica con el estado y tiempo restante
            SwingUtilities.invokeLater(() -> {
                String estado = enDescanso ? "Descansando" : "Atendiendo";
                labelCaja.setText(String.format("%s\nTiempo Restante: %d s", estado, tiempoRestante));
            });

            // Actualiza el tiempo restante para los clientes
            banco.actualizarTiempoClientes();

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

    public double getTiempoRestante() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
