package Perceptrones;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BancoGUI {

    private static final int NUM_PERSONAS = 10;
    private static final int TIEMPO_ATENCION_HOMBRE = 2000; // 2 segundos
    private static final int TIEMPO_ATENCION_MUJER = 1500; // 1.5 segundos
    private static final int TIEMPO_ATENCION_ADULTO_MAYOR = 4000; // 4 segundos
    private static final int TIEMPO_ATENCION_DISCAPACITADO = 1000; // 1 segundo
    private static final int TIEMPO_ATENCION_EMBARAZADA = 3000; // 3 segundos
    private static final int TIEMPO_DESCANSO_CAJERO1 = 500; // 0.5 segundos
    private static final int TIEMPO_DESCANSO_CAJERO2 = 800; // 0.8 segundos
    private static final int TIEMPO_ATENCION_CAJERO1 = 3000; // 3 segundos
    private static final int TIEMPO_ATENCION_CAJERO2 = 2500; // 2.5 segundos

    private static AtomicInteger personasAtendidas = new AtomicInteger(0);
    private Perceptron perceptron;
    private boolean resumenMostrado = false; // Bandera para controlar el mensaje de resumen

    private JFrame frame;
    private JLabel labelCajero1;
    private JLabel labelCajero2;
    private JLabel labelPersonasAtendidas;
    private JTextArea logTextArea;

    private AtomicInteger countHombres = new AtomicInteger(0);
    private AtomicInteger countMujeres = new AtomicInteger(0);
    private AtomicInteger countAncianos = new AtomicInteger(0);
    private AtomicInteger countDiscapacitados = new AtomicInteger(0);
    private AtomicInteger countEmbarazadas = new AtomicInteger(0);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoGUI::new);
    }

    public BancoGUI() {
        this.perceptron = new Perceptron(5, 0.01); // 5 entradas para los tipos de persona
        createGUI();
    }

    private void createGUI() {
        // Configuración de la ventana principal
        frame = new JFrame("Banco - Simulación de Atención");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Panel superior para mostrar el estado de los cajeros
        JPanel panelCajeros = new JPanel(new GridLayout(2, 1, 10, 10));
        labelCajero1 = new JLabel("Cajero 1: Libre", SwingConstants.CENTER);
        labelCajero2 = new JLabel("Cajero 2: Libre", SwingConstants.CENTER);
        labelPersonasAtendidas = new JLabel("Personas atendidas: 0", SwingConstants.CENTER);

        // Estilo para los JLabel
        labelCajero1.setFont(new Font("Arial", Font.BOLD, 16));
        labelCajero1.setOpaque(true);
        labelCajero1.setBackground(new Color(0xC1E1FF));

        labelCajero2.setFont(new Font("Arial", Font.BOLD, 16));
        labelCajero2.setOpaque(true);
        labelCajero2.setBackground(new Color(0xC1FFC1));

        panelCajeros.add(labelCajero1);
        panelCajeros.add(labelCajero2);

        // Panel inferior para el log de acciones
        logTextArea = new JTextArea(10, 40);
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        // Añadir componentes a la ventana principal
        frame.add(panelCajeros, BorderLayout.NORTH);
        frame.add(labelPersonasAtendidas, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Iniciar la simulación de cajeros
        Thread cajero1 = new Thread(new Cajero(1, TIEMPO_ATENCION_CAJERO1, TIEMPO_DESCANSO_CAJERO1));
        Thread cajero2 = new Thread(new Cajero(2, TIEMPO_ATENCION_CAJERO2, TIEMPO_DESCANSO_CAJERO2));

        cajero1.start();
        cajero2.start();

        // Mostrar la ventana
        frame.setVisible(true);
    }

    private class Cajero implements Runnable {

        private int id;
        private int tiempoAtencion;
        private int tiempoDescanso;
        private AtomicInteger personasEnAtencion = new AtomicInteger(0);

        public Cajero(int id, int tiempoAtencion, int tiempoDescanso) {
            this.id = id;
            this.tiempoAtencion = tiempoAtencion;
            this.tiempoDescanso = tiempoDescanso;
        }

        @Override
        public void run() {
            while (personasAtendidas.get() < NUM_PERSONAS) {
                atenderPersona();
                descanso();
            }

            // Mostrar el resumen al finalizar la simulación
            mostrarResumen();
        }

        private void atenderPersona() {
            int tipoPersona = (int) (Math.random() * 5); // 0: Hombre, 1: Mujer, 2: Adulto mayor, 3: Discapacitado, 4: Embarazada
            int tiempoAtencionPersona = getTiempoAtencionPersona(tipoPersona);

            // Crear el vector de entrada para el perceptrón
            int[] entradas = new int[5];
            entradas[tipoPersona] = 1; // Solo el tipo de persona actual es 1, los demás son 0

            // Usar el perceptrón para decidir cuál cajero debe atender a la persona
            int prediccion = perceptron.predecir(entradas);

            // Solo atender si el perceptrón predice este cajero
            if (prediccion == (id - 1)) {
                try {
                    SwingUtilities.invokeLater(() -> {
                        if (id == 1) {
                            labelCajero1.setText("Cajero 1: Atendiendo a " + getTipoPersona(tipoPersona));
                        } else {
                            labelCajero2.setText("Cajero 2: Atendiendo a " + getTipoPersona(tipoPersona));
                        }
                    });

                    Thread.sleep(tiempoAtencionPersona);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                personasAtendidas.incrementAndGet();
                personasEnAtencion.incrementAndGet();
                updateGUI(id, tipoPersona, tiempoAtencionPersona);

                // Actualizar contadores por tipo de persona
                switch (tipoPersona) {
                    case 0: countHombres.incrementAndGet(); break;
                    case 1: countMujeres.incrementAndGet(); break;
                    case 2: countAncianos.incrementAndGet(); break;
                    case 3: countDiscapacitados.incrementAndGet(); break;
                    case 4: countEmbarazadas.incrementAndGet(); break;
                }

                // Entrenar el perceptrón después de cada atención
                perceptron.entrenar(entradas, id - 1);
            }
        }

        private void descanso() {
            SwingUtilities.invokeLater(() -> {
                if (id == 1) {
                    labelCajero1.setText("Cajero 1: En descanso");
                } else {
                    labelCajero2.setText("Cajero 2: En descanso");
                }
            });

            try {
                Thread.sleep(tiempoDescanso);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private int getTiempoAtencionPersona(int tipoPersona) {
            switch (tipoPersona) {
                case 0:
                    return TIEMPO_ATENCION_HOMBRE;
                case 1:
                    return TIEMPO_ATENCION_MUJER;
                case 2:
                    return TIEMPO_ATENCION_ADULTO_MAYOR;
                case 3:
                    return TIEMPO_ATENCION_DISCAPACITADO;
                case 4:
                    return TIEMPO_ATENCION_EMBARAZADA;
                default:
                    return 0;
            }
        }

        private String getTipoPersona(int tipoPersona) {
            switch (tipoPersona) {
                case 0:
                    return "Hombre";
                case 1:
                    return "Mujer";
                case 2:
                    return "Adulto mayor";
                case 3:
                    return "Discapacitado";
                case 4:
                    return "Embarazada";
                default:
                    return "Desconocido";
            }
        }

        private void updateGUI(final int id, final int tipoPersona, final int tiempoAtencion) {
            SwingUtilities.invokeLater(() -> {
                if (id == 1) {
                    labelCajero1.setText("Cajero 1: Atendiendo a " + getTipoPersona(tipoPersona));
                } else {
                    labelCajero2.setText("Cajero 2: Atendiendo a " + getTipoPersona(tipoPersona));
                }
                labelPersonasAtendidas.setText("Personas atendidas: " + personasAtendidas.get());
                logTextArea.append("Cajero " + id + " atendió a " + getTipoPersona(tipoPersona) + " en " + tiempoAtencion / 1000.0 + " segundos\n");
            });
        }

        private synchronized void mostrarResumen() {
            if (!resumenMostrado) {
                resumenMostrado = true;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame,
                            "Resumen de la simulación:\n" +
                                    "Hombres atendidos: " + countHombres.get() + "\n" +
                                    "Mujeres atendidas: " + countMujeres.get() + "\n" +
                                    "Adultos mayores atendidos: " + countAncianos.get() + "\n" +
                                    "Discapacitados atendidos: " + countDiscapacitados.get() + "\n" +
                                    "Embarazadas atendidas: " + countEmbarazadas.get(),
                            "Resumen de la Simulación",
                            JOptionPane.INFORMATION_MESSAGE);
                });
            }
        }
    }
}