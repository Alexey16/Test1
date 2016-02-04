package com.company;

/**
 * Created by Алексей on 21.01.2016.
 */
public class QRSolve {

    private double[][] q, r;
    private double[] x, b;

    public QRSolve(double[][] matr, double[] row) {
        QR qr = new QR(matr);
        q = qr.getQ();
        r = qr.getR();
        b = new double[q.length];
        for (int i = 0; i < q.length; i++) {
            b[i] = row[i];
        }
        b = Matrix.mult(Matrix.transp(q), b);
        createX();
    }

    private void createX() {
        x = new double[q.length];
        double s;
        for (int i = q.length - 1; i >= 0; i--) {
            s = 0;
            for (int k = i + 1; k < q.length; k++) {
                s += r[i][k] * x[k];
            }
            x[i] = (b[i] - s) / r[i][i];
        }
    }

    public double getMistake() {
        int n = x.length;
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            y[i] = (i + 1) - x[i];
        }
        return Matrix.vectorNorm(y) / n;
    }

    public double[] getX() {
        double[] res = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = x[i];
        }
        return res;
    }

    public void printX() {
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
    }
}
