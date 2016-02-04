package com.company;

/**
 * Created by Алексей on 22.01.2016.
 */
public class QRSpectrum {

    private QR qr;
    private double[][] a;
    private double exact;
    private double[] spectrum;

    public QRSpectrum(double[][] data, double e) {
        exact = e;
        a = new double[data.length][data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                a[i][j] = data[i][j];
            }
        }
        qr = new QR(a);
        while (condition() > 0) {
            a = Matrix.mult(qr.getR(), qr.getQ());
            qr = new QR(a);
        }
        spectrum = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            spectrum[i] = a[i][i];
        }
    }

    private int condition() {
        double cond = 0;
        for (int i = 0; i < a.length - 1; i++) {
            cond += Math.abs(a[i + 1][i]);
        }
        if (cond > exact) {
            return 1;
        } else {
            return -1;
        }
    }

    public double[] getLambdas() {
        double[] l = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            l[i] = a[i][i];
        }
        return l;
    }

    public void printSpecrum() {

        for (int j = 0; j < a.length; j++) {
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
}
