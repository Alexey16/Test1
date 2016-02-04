package com.company;

/**
 * Created by Алексей on 21.01.2016.
 */
public class QR {

    private double[][] a;
    private double[][] q;
    private double[][] r;

    public QR(double[][] b) {
        a = new double[b.length][b.length];
        double[][] temp;
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                a[i][j] = b[i][j];
            }
        }
        for (int k = 1; k <= a.length - 1; k++) {
            temp = getHouseHolder(k);
            if (k == 1) {
                r = Matrix.mult(temp, a);
                q = temp;
            } else {
                r = Matrix.mult(temp, r);
                q = Matrix.mult(temp, q);
            }
        }
        q = Matrix.transp(q);
    }

    private double[][] houseHolder(int n, double[] p) {
        double y = -2 / Matrix.scalMult(p, p);
        double[][] e = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    e[i][j] = 1;
                } else {
                    e[i][j] = 0;
                }
            }
        }
        return Matrix.sum(e, Matrix.mult(Matrix.mult(p, p), y));
    }

    private double[] getP(double[] v) {
        double[] p = new double[v.length];
        if (v[0] >= 0) {
            p[0] = v[0] + Matrix.evklNorm(v);
        } else {
            p[0] = v[0] - Matrix.evklNorm(v);
        }
        for (int i = 1; i < p.length; i++) {
            p[i] = v[i];
        }
        return p;
    }

    private double[] getV(int k) {
        double[] v = new double[a.length - k + 1];
        if (k == 1) {
            for (int i = 0; i < a.length; i++) {
                v[i] = a[i][0];
            }
            return v;
        }
        for (int i = k - 1; i < r.length; i++) {
            v[i - k + 1] = r[i][k - 1];
        }
        return v;
    }

    private double[][] getHouseHolder(int k) {
        if (k == 1) {
            return houseHolder(a.length, getP(getV(k)));
        }
        double[][] h = new double[a.length][a.length];
        double[][] hs = houseHolder(a.length - k + 1, getP(getV(k)));
        for (int i = 0; i < k - 1; i++) {
            h[i][i] = 1;
        }
        for (int i = 0; i < hs.length; i++) {
            for (int j = 0; j < hs.length; j++) {
                h[i + k - 1][j + k - 1] = hs[i][j];
            }
        }
        return h;
    }

    public void printQ() {
        for (int i = 0; i < q.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < q.length; j++) {
                System.out.print(q[i][j] + " ");
            }
        }
        System.out.println("");
    }

    public void printR() {
        for (int i = 0; i < r.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < r.length; j++) {
                System.out.print(r[i][j] + " ");
            }
        }
        System.out.println("");
    }

    public double[][] getR() {
        double[][] temp = new double[r.length][r.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = r[i][j];
            }
        }
        return temp;
    }

    public double[][] getQ() {
        double[][] temp = new double[q.length][q.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = q[i][j];
            }
        }
        return temp;
    }
}
