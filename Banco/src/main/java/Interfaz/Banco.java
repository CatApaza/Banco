package Interfaz;

import Hilos.Cronometro;
import Hilos.Hilo1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private Cronometro cronometroCaja1;
    private Cronometro cronometroCaja2;

    private boolean cajasAbiertas = false;

    public Banco() {
        // Configurar la ventana principal
        setTitle("Interfaz");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Crear un panel principal con un diseño de caja
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Crear un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Crear el botón "Abrir Caja"
        JButton botonAbrirCaja = new JButton("Abrir Caja");
        botonAbrirCaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cajasAbiertas) {
                    cerrarCajas();
                    botonAbrirCaja.setText("Abrir Caja");
                } else {
                    abrirCajas();
                    botonAbrirCaja.setText("Cerrar Caja");
                }
                cajasAbiertas = !cajasAbiertas;
            }
        });

        panelBotones.add(botonAbrirCaja);

        // Crear un panel para los rectángulos (cajas)
        JPanel panelRectangulos = new JPanel();
        panelRectangulos.setLayout(new BoxLayout(panelRectangulos, BoxLayout.Y_AXIS));
        panelRectangulos.setPreferredSize(new Dimension(250, 0)); // Ancho fijo para el panel de rectángulos

        // Crear y agregar los rectángulos
        JPanel caja1 = new JPanel();
        caja1.setBackground(Color.RED);
        caja1.setPreferredSize(new Dimension(150, 50)); // Tamaño ajustado
        caja1.setBorder(BorderFactory.createTitledBorder("Caja 1"));

        JPanel caja2 = new JPanel();
        caja2.setBackground(Color.BLUE);
        caja2.setPreferredSize(new Dimension(150, 50)); // Tamaño ajustado
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
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los círculos
        gbc.insets = new Insets(20, 20, 20, 20); // Espacio alrededor de cada círculo

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
        panelPrincipal.add(panelBotones, BorderLayout.NORTH); // Panel de botones en la parte superior
        panelPrincipal.add(panelRectangulos, BorderLayout.WEST); // Panel de rectángulos a la izquierda
        panelPrincipal.add(panelCirculos, BorderLayout.CENTER); // Panel de círculos en el centro

        // Agregar el panel principal a la ventana
        add(panelPrincipal);

        // Inicializar los cronómetros para las cajas
        cronometroCaja1 = new Cronometro(labelCaja1, TIEMPO_ATENCION_CAJERO1, TIEMPO_DESCANSO_CAJERO1, this);
        cronometroCaja2 = new Cronometro(labelCaja2, TIEMPO_ATENCION_CAJERO2, TIEMPO_DESCANSO_CAJERO2, this);
    }

    // Método para abrir las cajas
    private void abrirCajas() {
        cronometroCaja1.execute();
        cronometroCaja2.execute();
    }

    // Método para cerrar las cajas
    private void cerrarCajas() {
        cronometroCaja1.cancel(true);
        cronometroCaja2.cancel(true);
    }

    // Método para actualizar el tiempo restante en los clientes
    public void actualizarTiempoClientes() {
        for (Clientes cliente : clientes) {
            cliente.actualizarTiempo(cliente.getTiempoAtencion());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Banco interfaz = new Banco();
            interfaz.setVisible(true);
        });
    }
}
