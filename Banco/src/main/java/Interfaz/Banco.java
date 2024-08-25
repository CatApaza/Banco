package Interfaz;

import Hilos.Cronometro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
    private JPanel panelCirculos;

    public Banco() {
        // Configurar la ventana principal
        setTitle("Banco");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Crear un panel principal con un diseño de BorderLayout
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        // Crear un panel para los botones y el mensaje de bienvenida
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BorderLayout());
        panelBotones.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espacio alrededor del panel de botones

        // Crear y agregar el mensaje de bienvenida
        JLabel mensajeBienvenida = new JLabel("¡Bienvenido al Banco! ¿Cómo podemos ayudarte hoy?");
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        mensajeBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        panelBotones.add(mensajeBienvenida, BorderLayout.NORTH);

        // Crear un panel para los botones
        JPanel panelBotonesInterno = new JPanel();
        panelBotonesInterno.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Crear el botón "Abrir Caja"
        JButton botonAbrirCaja = new JButton("Abrir Caja");
        botonAbrirCaja.setFont(new Font("Arial", Font.BOLD, 14));
        botonAbrirCaja.setBackground(new Color(0x4CAF50)); // Color verde
        botonAbrirCaja.setForeground(Color.WHITE);
        botonAbrirCaja.setFocusPainted(false);
        botonAbrirCaja.setBorderPainted(false);
        botonAbrirCaja.setOpaque(true);
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

        panelBotonesInterno.add(botonAbrirCaja);
        panelBotones.add(panelBotonesInterno, BorderLayout.SOUTH);

        // Crear un panel para los rectángulos (cajas)
        JPanel panelRectangulos = new JPanel();
        panelRectangulos.setLayout(new GridLayout(2, 1, 10, 10));
        panelRectangulos.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espacio alrededor del panel de cajas

        // Crear y agregar los rectángulos con colores y bordes dinámicos
        JPanel caja1 = new JPanel();
        caja1.setBackground(new Color(0xFFDDC1)); // Color pastel
        caja1.setBorder(BorderFactory.createTitledBorder("Caja 1"));
        caja1.setLayout(new BorderLayout());
        
        labelCaja1 = new JLabel("Tiempo Restante: 0.0 s");
        labelCaja1.setHorizontalAlignment(SwingConstants.CENTER);
        caja1.add(labelCaja1, BorderLayout.CENTER);

        JPanel caja2 = new JPanel();
        caja2.setBackground(new Color(0xC1E1C1)); // Color pastel
        caja2.setBorder(BorderFactory.createTitledBorder("Caja 2"));
        caja2.setLayout(new BorderLayout());

        labelCaja2 = new JLabel("Tiempo Restante: 0.0 s");
        labelCaja2.setHorizontalAlignment(SwingConstants.CENTER);
        caja2.add(labelCaja2, BorderLayout.CENTER);

        panelRectangulos.add(caja1);
        panelRectangulos.add(caja2);

        // Crear un panel para los círculos con GridBagLayout
        panelCirculos = new JPanel();
        panelCirculos.setLayout(new GridLayout(1, 5, 15, 15)); // Espacio de 15 píxeles entre círculos
        panelCirculos.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espacio alrededor del panel de círculos

        // Crear y agregar los círculos (clientes) con tiempos aleatorios en segundos
        for (int i = 0; i < 5; i++) {
            Clientes cliente = new Clientes(Character.toString((char) ('A' + i)), TIEMPO_MINIMO_CLIENTE, TIEMPO_MAXIMO_CLIENTE, getRandomColor());
            clientes.add(cliente);
            panelCirculos.add(cliente);
        }

        // Agregar paneles al panel principal
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
        cambiarColorCaja(labelCaja1, Color.GREEN); // Cambiar color de la caja 1 cuando esté en uso
        cambiarColorCaja(labelCaja2, Color.BLUE);  // Cambiar color de la caja 2 cuando esté en uso
    }

    // Método para cerrar las cajas
    private void cerrarCajas() {
        cronometroCaja1.cancel(true);
        cronometroCaja2.cancel(true);
        cambiarColorCaja(labelCaja1, new Color(0xFFDDC1)); // Restaurar color original
        cambiarColorCaja(labelCaja2, new Color(0xC1E1C1)); // Restaurar color original
    }

    // Método para cambiar el color de las cajas
    private void cambiarColorCaja(JLabel labelCaja, Color color) {
        JPanel panelCaja = (JPanel) labelCaja.getParent();
        panelCaja.setBackground(color);
        Border border = BorderFactory.createLineBorder(color.darker(), 3);
        panelCaja.setBorder(BorderFactory.createTitledBorder(border, ((TitledBorder) panelCaja.getBorder()).getTitle()));
    }

    // Método para generar un color aleatorio
    private Color getRandomColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return new Color(red, green, blue);
    }

    // Método para actualizar el tiempo restante en los clientes
    public void actualizarTiempoClientes() {
        for (Clientes cliente : clientes) {
            cliente.actualizarTiempo(cliente.getTiempoAtencion());
        }
    }

    // Método para reordenar los clientes
    private void reordenarClientes() {
        // Eliminar todos los clientes del panelCirculos
        panelCirculos.removeAll();

        // Reordenar los clientes (el primero pasa al final)
        Clientes cliente = clientes.remove(0);
        clientes.add(cliente);

        // Volver a agregar los clientes al panelCirculos
        for (Clientes c : clientes) {
            panelCirculos.add(c);
        }

        // Actualizar la interfaz para reflejar los cambios
        panelCirculos.revalidate();
        panelCirculos.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Banco interfaz = new Banco();
            interfaz.setVisible(true);
        });
    }
}
