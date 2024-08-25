package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Clientes extends JPanel {
    private final String inicial;
    private final int tiempoAtencion; // Tiempo en segundos
    private int tiempoRestante; // Tiempo restante de atención
    private final Random random = new Random();

    public Clientes(String inicial, int tiempoMinimoAtencion, int tiempoMaximoAtencion) {
        this.inicial = inicial;
        this.tiempoAtencion = tiempoMinimoAtencion + random.nextInt(tiempoMaximoAtencion - tiempoMinimoAtencion + 1);
        this.tiempoRestante = tiempoAtencion; // Inicialmente el tiempo restante es el tiempo total de atención
        setPreferredSize(new Dimension(90, 90)); // Tamaño del círculo
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

        // Dibujar el tiempo restante debajo de la inicial
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.BLACK);
        String tiempoText = String.format("%d s", tiempoRestante);
        FontMetrics fm2 = g.getFontMetrics();
        int tiempoX = (getWidth() - fm2.stringWidth(tiempoText)) / 2;
        int tiempoY = y + fm.getAscent() + 15; // Ajustar la posición para que esté debajo de la inicial
        g.drawString(tiempoText, tiempoX, tiempoY);
    }

    // Método para actualizar el tiempo restante
    public void actualizarTiempo(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
        repaint(); // Volver a dibujar el panel para mostrar el nuevo tiempo
    }

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }
}
