package com.company;

import java.util.Scanner;

/**
 * Created by Алексей on 21.01.2016.
 */
public class Matrix {

    private double[][] a;
    private double[] b;
    private int size;

    public Matrix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите размерность матрицы");
        size = sc.nextInt();
        a = new double[size][size];
        System.out.println("Введите элементы матрицы");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i][j] = sc.nextDouble();
            }
        }
        sc.close();
        createB();
    }

    public Matrix(double[][] data) {
        a = data;
        size = data.length;
        createB();
    }

    private void createB() {
        b = new double[size];
        for (int i = 0; i < size; i++) {
            b[i] = 0;
            for (int j = 0; j < size; j++) {
                b[i] += a[i][j] * (j + 1);
            }
        }
    }

    public void printMatr() {
        for (int i = 0; i < size; i++) {
            System.out.print("\n");
            for (int j = 0; j < size; j++) {
                System.out.print(a[i][j] + " ");
            }
        }
        System.out.println("");
    }

    public void printB() {
        for (int j = 0; j < size; j++) {
            System.out.print(b[j] + " ");

        }
        System.out.println("");
    }

    public double getA(int i, int j) {
        return a[i][j];
    }

    public double getB(int i) {
        return b[i];
    }

    public double[] getB() {
        double[] temp = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            temp[i] = b[i];
        }
        return temp;
    }

    public int getSize() {
        return size;
    }

    public static double vectorNorm(double[] vector) {
        double norm = 0;
        for (int i = 0; i < vector.length; i++) {
            norm += Math.abs(vector[i]) * Math.abs(vector[i]);
        }
        return Math.sqrt(norm);
    }

    public double getMatrixNorm() {
        double s;
        double max = 0;
        for (int i = 0; i < size; i++) {
            s = 0;
            for (int j = 0; j < size; j++) {
                s += Math.abs(a[i][j]);
            }
            if (s > max) {
                max = s;
            }
        }
        return max;
    }

    public static double matrixNorm(double[][] data) {
        double s;
        double max = 0;
        for (int i = 0; i < data.length; i++) {
            s = 0;
            for (int j = 0; j < data.length; j++) {
                s += Math.abs(data[i][j]);
            }
            if (s > max) {
                max = s;
            }
        }
        return max;
    }

    public static double scalMult(double[] x, double[] y) {
        if (x.length != y.length) {
            System.out.println("размеры векторов не совпадают");
            return -1;
        }

        double z = 0;
        for (int i = 0; i < x.length; i++) {
            z += x[i] * y[i];
        }
        return z;
    }

    public static double evklNorm(double[] x) {
        return Math.sqrt(Matrix.scalMult(x, x));
    }

    public static double[] vectorSum(double[] x, double[] y) {

        double[] z = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            z[i] = x[i] + y[i];
        }
        return z;

    }

    public static double[][] sum(double[][] x, double[][] y) {
        if (x.length != y.length) {
            System.out.println("размеры матриц не совпадают!");
            return null;
        } else {
            double[][] z = new double[x.length][x.length];
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x.length; j++) {
                    z[i][j] = x[i][j] + y[i][j];
                }
            }
            return z;
        }
    }

    public static double[][] mult(double[][] x, double[][] y) {
        double[][] z = new double[x.length][x.length];

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                for (int k = 0; k < x.length; k++) {
                    z[i][j] += x[i][k] * y[k][j];
                }
            }
        }
        return z;
    }

    public static double[] mult(double[][] x, double[] y) {
        double[] z = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                z[i] += x[i][j] * y[j];
            }
        }
        return z;
    }

    public static double[][] multWin(double[][] a, double[][] b) {
        int n = a.length;

        double[][] c = new double[n][n];

        double[] Bcolj = new double[n];
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                Bcolj[k] = b[k][j];
            }
            for (int i = 0; i < n; i++) {
                double[] Arowi = a[i];
                double s = 0;
                for (int k = 0; k < n; k++) {
                    s += Arowi[k] * Bcolj[k];
                }
                c[i][j] = s;
            }
        }
        return c;

    }

    public static double[][] mult(double[] x, double[] y) {
        double[][] z = new double[x.length][x.length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                z[i][j] = x[i] * y[j];
            }
        }
        return z;
    }

    public static double[][] mult(double[][] x, double y) {
        double[][] z = new double[x.length][x.length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                z[i][j] = x[i][j] * y;
            }
        }
        return z;
    }

    public static double[][] transp(double[][] a) {
        double[][] b = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                b[i][j] = a[j][i];
            }
        }
        return b;
    }

    public static double[][] generate(int n) {
        double[][] m = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = Math.random() * 100;
            }
        }
        return m;
    }

    public double[][] getMatrix() {
        double[][] tmp = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                tmp[i][j] = a[i][j];
            }
        }
        return tmp;
    }
}
