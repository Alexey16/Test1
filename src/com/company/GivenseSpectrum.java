package com.company;

/**
 * Created by Алексей on 22.01.2016.
 */
public class GivenseSpectrum {

    private double[][] q;
    private double[][] r;
    private double c, s;
    private int i, j;
    private double[][] a;
    private double exact;
    private double[] spectrum;

    public GivenseSpectrum(double[][] data, double e) {

        spectrum = new double[data.length];
        exact = e;
        Hessenberg temp = new Hessenberg(data);
        a = temp.getHes();
        do {
            Calculated();
            givens(a);
            a = Matrix.multWin(r, q);
        } while (condition() > exact);
        createSpectrum();
    }

    private double condition() {
        double cond = 0;
        for (int i = 0; i < a.length - 1; i++) {
            cond += Math.abs(a[i + 1][i]);
        }
        return cond;
    }

    public double[] getLambdas() {
        double[] l = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            l[i] = a[i][i];
        }
        return l;
    }

    public double[] createSpectrum() {
        for (int i = 0; i < a.length; i++) {
            spectrum[i] = a[i][i];
        }
        return spectrum;
    }

    public void printSpecrum() {
        for (int j = 0; j < spectrum.length; j++) {
            System.out.print(spectrum[j] + " ");
        }
        System.out.println("");
    }

    public double maxOwmNumb() {
        double max = spectrum[0];
        for (int i = 1; i < spectrum.length; i++) {
            if (spectrum[i] > max) {
                max = spectrum[i];
            }
        }
        return max;
    }

    public double minOwmNumb() {
        double min = spectrum[0];
        for (int i = 1; i < spectrum.length; i++) {
            if (spectrum[i] < min) {
                min = spectrum[i];
            }
        }
        return min;
    }

    private void givens(double[][] data) {
        r = new double[data.length][data.length];
        q = new double[data.length][data.length];
        for (int k = 0; k < data.length; k++) {
            q[k][k] = 1;
            for (int m = 0; m < data.length; m++) {
                r[k][m] = data[k][m];
            }
        }
        j = 0;
        double[][] temp;
        while (j < r.length - 1) {
            i = j + 1;
            createCS();
            temp = getGij();
            r = Matrix.multWin(temp, r);
            q = Matrix.multWin(temp, q);
            j++;
        }
        q = Matrix.transp(q);
    }

    private double[][] getGij() {
        double[][] e = new double[r.length][r.length];
        for (int k = 0; k < r.length; k++) {
            e[k][k] = 1;
        }
        e[i][i] = c;
        e[i][j] = -s;
        e[j][i] = s;
        e[j][j] = c;
        return e;
    }

    private void createCS() {
        double tau = r[i][j] * r[i][j] + r[j][j] * r[j][j];
        tau = Math.sqrt(tau);
        c = r[j][j] / tau;
        s = r[i][j] / tau;
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

    private void Calculated() {
        double s = 0;
        for (int k = 0; k < a.length - 1; k++) {
            s += a[a.length - 1][k];
        }
        if (s <= exact) {
            spectrum[a.length - 1] = a[a.length - 1][a.length - 1];
            double[][] b = new double[a.length - 1][a.length - 1];
            for (int k = 0; k < b.length; k++) {
                for (int l = 0; l < b.length; l++) {
                    b[k][l] = a[k][l];
                }
            }
            a = b;
        }
    }
}
