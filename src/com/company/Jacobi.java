package com.company;

/**
 * Created by Алексей on 21.01.2016.
 */
public class Jacobi {

    private Matrix matrix;
    private double[] x;
    private int n;
    private double exact;
    private double q;
    private int sumOps, multOps;

    public Jacobi(double e, Matrix matr) {
        matrix = matr;
        exact = e;
        n = matr.getSize();
        sumOps = 0;
        multOps = 0;
        createQ();
        if (q < 1) {
            createX();
        } else {
            System.out.println("Метод Якоби расходится");
        }
    }

    private void createX() {
        x = new double[n];
        double[] xk = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = matrix.getB(i);
        }
        double delta = 0;
        double s;
        double[] dx = new double[n];

        do {
            for (int i = 0; i < n; i++) {
                xk[i] = x[i];
            }
            for (int i = 0; i < n; i++) {
                s = 0;
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }

                    if (i < j) {
                        s += matrix.getA(i, j) * xk[j];
                        sumOps++;
                        multOps++;
                    }
                    if (i > j) {
                        s += matrix.getA(i, j) * xk[j];
                        sumOps++;
                        multOps++;
                    }

                }
                x[i] = -(s - matrix.getB(i)) / matrix.getA(i, i);
                sumOps++;
                multOps++;

            }
            for (int i = 0; i < n; i++) {
                dx[i] = x[i] - xk[i];
                sumOps++;
            }
            delta = Matrix.vectorNorm(dx);
        } while (delta > (1 - q) * exact / q);

    }

    private void createQ() {
        double[][] b = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    b[i][j] = 0;
                } else {
                    b[i][j] = -matrix.getA(i, j) / matrix.getA(i, i);
                    multOps++;
                }
            }
        }
        q = Matrix.matrixNorm(b);
    }

    public void printX() {
        for (int i = 0; i < n; i++) {
            System.out.println(x[i]);
        }
    }

    public double getMistake() {
        if (q < 1) {
            double[] y = new double[n];
            for (int i = 0; i < n; i++) {
                y[i] = (i + 1) - x[i];
            }
            return Matrix.vectorNorm(y) / n;
        } else {
            return 0;
        }
    }

    public int getSumOps() {
        if (q < 1) {
            return sumOps;
        } else {
            return 0;
        }
    }

    public int getMultOps() {
        if (q < 1) {
            return multOps;
        } else {
            return 0;
        }
    }
}
