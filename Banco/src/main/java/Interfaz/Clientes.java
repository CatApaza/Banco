package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Clientes extends JPanel {
    private final String inicial;
    private final int tiempoAtencion; // Tiempo en segundos
    private final Random random = new Random();

    public Clientes(String inicial, int tiempoMinimoAtencion, int tiempoMaximoAtencion) {
        this.inicial = inicial;
        // Genera un tiempo de atención aleatorio en segundos
        this.tiempoAtencion = tiempoMinimoAtencion + random.nextInt(tiempoMaximoAtencion - tiempoMinimoAtencion + 1);
        setPreferredSize(new Dimension(70, 70)); // Tamaño del círculo
        setOpaque(false); // Hacer el panel transparente para ver el círculo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar el círculo
        g.setColor(Color.GREEN);
        g.fillOval(0, 0, getWidth(), getHeight());

        // Dibujar la inicial en el centro del círculo
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(inicial)) / 2;
        int y = (getHeight() + fm.getAscent()) / 2;
        g.drawString(inicial, x, y);
    }

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }
}
