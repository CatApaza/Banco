package Perceptrones;

public class Perceptron {
    private double[] pesos;
    private double tasaAprendizaje;

    public Perceptron(int numEntradas, double tasaAprendizaje) {
        this.pesos = new double[numEntradas];
        this.tasaAprendizaje = tasaAprendizaje;
        inicializarPesos();
    }

    private void inicializarPesos() {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = Math.random() * 2 - 1; // Inicializa con valores entre -1 y 1
        }
    }

    public int predecir(int[] entradas) {
        double suma = 0.0;
        for (int i = 0; i < entradas.length; i++) {
            suma += entradas[i] * pesos[i];
        }
        return suma >= 0 ? 1 : 0; // Retorna 1 para Cajero 1, 0 para Cajero 2
    }

    public void entrenar(int[] entradas, int salidaEsperada) {
        int prediccion = predecir(entradas);
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] += tasaAprendizaje * (salidaEsperada - prediccion) * entradas[i];
        }
    }
}
