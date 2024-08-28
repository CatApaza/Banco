package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Clientes extends JPanel {
    private final String inicial;
    private final int tiempoAtencion; // Tiempo en segundos
    private int tiempoRestante; // Tiempo restante de atención
    private final Random random = new Random();

    // Tamaño del círculo reducido
    private static final int DIAMETRO_CIRCULO = 60;

    public Clientes(String inicial, int tiempoMinimoAtencion, int tiempoMaximoAtencion, Color randomColor) {
        this.inicial = inicial;
        this.tiempoAtencion = tiempoMinimoAtencion + random.nextInt(tiempoMaximoAtencion - tiempoMinimoAtencion + 1);
        this.tiempoRestante = tiempoAtencion; // Inicialmente el tiempo restante es el tiempo total de atención
        setPreferredSize(new Dimension(DIAMETRO_CIRCULO, DIAMETRO_CIRCULO)); // Tamaño del círculo
        setOpaque(false); // Hacer el panel transparente para ver el círculo
        setBackground(randomColor); // Establecer el color de fondo del panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Obtener el tamaño del panel
        int width = getWidth();
        int height = getHeight();

        // Calcular el radio y la posición para centrar el círculo
        int radio = DIAMETRO_CIRCULO / 2;
        int x = (width - DIAMETRO_CIRCULO) / 2;
        int y = (height - DIAMETRO_CIRCULO) / 2;

        // Dibujar el círculo
        g.setColor(getBackground()); // Usar el color de fondo del panel
        g.fillOval(x, y, DIAMETRO_CIRCULO, DIAMETRO_CIRCULO);

        // Dibujar la inicial en el centro del círculo
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16)); // Ajustar el tamaño de la fuente según el nuevo tamaño
        FontMetrics fm = g.getFontMetrics();
        int inicialX = x + (DIAMETRO_CIRCULO - fm.stringWidth(inicial)) / 2;
        int inicialY = y + ((DIAMETRO_CIRCULO + fm.getAscent()) / 2);
        g.drawString(inicial, inicialX, inicialY);

        // Dibujar el tiempo restante debajo de la inicial
        g.setFont(new Font("Arial", Font.PLAIN, 10)); // Ajustar el tamaño de la fuente según el nuevo tamaño
        g.setColor(Color.BLACK);
        String tiempoText = String.format("%d s", tiempoRestante);
        FontMetrics fm2 = g.getFontMetrics();
        int tiempoX = x + (DIAMETRO_CIRCULO - fm2.stringWidth(tiempoText)) / 2;
        int tiempoY = inicialY + 15; // Ajustar la posición para que esté debajo de la inicial
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

    // Método para obtener la inicial del cliente
    public String getInicial() {
        return inicial;
    }
}
