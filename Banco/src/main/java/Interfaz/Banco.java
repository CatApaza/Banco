package Interfaz;

import Hilos.Cronometro;
import Hilos.Hilo1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Banco extends JFrame {
    private JLabel labelCaja1;
    private JLabel labelCaja2;
    private final List<Clientes> clientes = new ArrayList<>();

    // Tiempo de atención y descanso de las cajas en segundos
    private static final int TIEMPO_ATENCION_CAJERO1 = 180; // 3 minutos = 180 segundos
    private static final int TIEMPO_DESCANSO_CAJERO1 = 30;  // 0.5 minutos = 30 segundos
    private static final int TIEMPO_ATENCION_CAJERO2 = 150; // 2.5 minutos = 150 segundos
    private static final int TIEMPO_DESCANSO_CAJERO2 = 48;  // 0.8 minutos = 48 segundos

    private static final int TIEMPO_MINIMO_CLIENTE = 60; // 1 minuto = 60 segundos
    private static final int TIEMPO_MAXIMO_CLIENTE = 240; // 4 minutos = 240 segundos

    public Banco() {
        // Configurar la ventana principal
        setTitle("Interfaz");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Crear un panel principal con un diseño de caja
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Crear un panel para los rectángulos (cajas)
        JPanel panelRectangulos = new JPanel();
        panelRectangulos.setLayout(new BoxLayout(panelRectangulos, BoxLayout.Y_AXIS));
        panelRectangulos.setPreferredSize(new Dimension(250, 0)); // Ancho fijo para el panel de rectángulos

        // Crear y agregar los rectángulos
        JPanel caja1 = new JPanel();
        caja1.setBackground(Color.RED);
        caja1.setPreferredSize(new Dimension(120, 50)); // Tamaño reducido
        caja1.setBorder(BorderFactory.createTitledBorder("Caja 1"));

        JPanel caja2 = new JPanel();
        caja2.setBackground(Color.BLUE);
        caja2.setPreferredSize(new Dimension(120, 50)); // Tamaño reducido
        caja2.setBorder(BorderFactory.createTitledBorder("Caja 2"));

        // Etiquetas para mostrar el estado de cada caja
        labelCaja1 = new JLabel("Tiempo Restante: 0.0 s");
        labelCaja2 = new JLabel("Tiempo Restante: 0.0 s");

        caja1.add(labelCaja1);
        caja2.add(labelCaja2);

        panelRectangulos.add(caja1);
        panelRectangulos.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio de 20 píxeles vertical
        panelRectangulos.add(caja2);

        // Crear un panel para los círculos con GridBagLayout
        JPanel panelCirculos = new JPanel();
        panelCirculos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los círculos
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio alrededor de cada círculo

        // Crear y agregar los círculos (clientes) con tiempos aleatorios en segundos
        Clientes cliente1 = new Clientes("E", TIEMPO_MINIMO_CLIENTE, TIEMPO_MAXIMO_CLIENTE);
        Clientes cliente2 = new Clientes("H", TIEMPO_MINIMO_CLIENTE, TIEMPO_MAXIMO_CLIENTE);
        Clientes cliente3 = new Clientes("K", TIEMPO_MINIMO_CLIENTE, TIEMPO_MAXIMO_CLIENTE);
        Clientes cliente4 = new Clientes("D", TIEMPO_MINIMO_CLIENTE, TIEMPO_MAXIMO_CLIENTE);
        Clientes cliente5 = new Clientes("M", TIEMPO_MINIMO_CLIENTE, TIEMPO_MAXIMO_CLIENTE);

        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);
        clientes.add(cliente5);

        gbc.gridx = 0; panelCirculos.add(cliente1, gbc);
        gbc.gridx = 1; panelCirculos.add(cliente2, gbc);
        gbc.gridx = 2; panelCirculos.add(cliente3, gbc);
        gbc.gridx = 3; panelCirculos.add(cliente4, gbc);
        gbc.gridx = 4; panelCirculos.add(cliente5, gbc);

        // Agregar los paneles al panel principal
        panelPrincipal.add(panelRectangulos, BorderLayout.WEST); // Panel de rectángulos a la izquierda
        panelPrincipal.add(panelCirculos, BorderLayout.CENTER); // Panel de círculos en el centro

        // Agregar el panel principal a la ventana
        add(panelPrincipal);

        // Iniciar los hilos para simular el tiempo de atención y descanso en segundos
        new Thread(new Hilo1("Caja 1", TIEMPO_ATENCION_CAJERO1, TIEMPO_DESCANSO_CAJERO1, labelCaja1)).start();
        new Thread(new Hilo1("Caja 2", TIEMPO_ATENCION_CAJERO2, TIEMPO_DESCANSO_CAJERO2, labelCaja2)).start();

        // Iniciar los cronómetros para las cajas
        new Cronometro(labelCaja1, TIEMPO_ATENCION_CAJERO1, TIEMPO_DESCANSO_CAJERO1).execute();
        new Cronometro(labelCaja2, TIEMPO_ATENCION_CAJERO2, TIEMPO_DESCANSO_CAJERO2).execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Banco interfaz = new Banco();
            interfaz.setVisible(true);
        });
    }
}
