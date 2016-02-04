package com.company;

import java.util.Arrays;

/**
 * Created by Алексей on 22.01.2016.
 */
public class Hessenberg {

    private double[][] a;

    public Hessenberg(double[][] b) {
        a = Arrays.copyOf(b, b.length);
        double[][] temp;
        for (int k = 1; k <= a.length - 2; k++) {
            temp = getHouseHolder(k);
            a = Matrix.multWin(temp, a);
            a = Matrix.multWin(a, temp);
        }
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
        double[] v = new double[a.length - k];
        for (int i = k; i < a.length; i++) {
            v[i - k] = a[i][k - 1];
        }
        return v;
    }

    private double[][] getHouseHolder(int k) {
        double[][] h = new double[a.length][a.length];
        double[][] hs = houseHolder(a.length - k, getP(getV(k)));
        for (int i = 0; i < k; i++) {
            h[i][i] = 1;
        }
        for (int i = 0; i < hs.length; i++) {
            for (int j = 0; j < hs.length; j++) {
                h[i + k][j + k] = hs[i][j];
            }
        }
        return h;
    }

    public void printHes() {
        for (int i = 0; i < a.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < a.length; j++) {
                System.out.print(a[i][j] + " ");
            }
        }
        System.out.println("");
    }

    public double[][] getHes(){
        double[][] b  = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j <a.length; j++) {
                b[i][j]=a[i][j];
            }
        }
        return b;
    }
}
