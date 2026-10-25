package com.clase.analisisempirico;

public class Sumatoria {

    public static final int[] VALORES_N = {100, 1000, 10000, 100000,
            1000000, 10000000, 100000000};

    public static long sumatoriaGaussiana(int n) {
        return (long) n * (n + 1) / 2;
    }

    public static long sumatoriaIterativa(int n) {
        long suma = 0;
        for (int i = 0; i <= n; i++) {
            suma += i;
        }
        return suma;
    }

    public static long sumatoriaRecursiva(int n, int i) {
        if (n == i) {
            return i;
        } else {
            return i + sumatoriaRecursiva(n, i + 1);
        }
    }

    // Devuelve los tiempos en ns: [0] Gauss, [1] iterativa, [2] recursiva (-1 si falla)
    public static long[][] medirTiempos() {
        long[][] tiempos = new long[3][VALORES_N.length];

        for (int k = 0; k < VALORES_N.length; k++) {
            int n = VALORES_N[k];

            long inicio = System.nanoTime();
            sumatoriaGaussiana(n);
            tiempos[0][k] = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            sumatoriaIterativa(n);
            tiempos[1][k] = System.nanoTime() - inicio;

            try {
                inicio = System.nanoTime();
                sumatoriaRecursiva(n, 0);
                tiempos[2][k] = System.nanoTime() - inicio;
            } catch (StackOverflowError e) {
                tiempos[2][k] = -1;   // n demasiado grande para la recursión
            }
        }
        return tiempos;
    }

    public static void main(String[] args) {
        long[][] t = medirTiempos();
        for (int k = 0; k < VALORES_N.length; k++) {
            System.out.println("n=" + VALORES_N[k]
                    + " | Gauss: " + t[0][k] + " ns"
                    + " | Iterativa: " + t[1][k] + " ns"
                    + " | Recursiva: " + (t[2][k] < 0 ? "StackOverflowError" : t[2][k] + " ns"));
        }
    }
}
