package com.company;

/**
 * Created by Алексей on 21.01.2016.
 */
public class LuDrive {

    private int n;
    private double[][] a;
    private double[] b;
    private double[][] LU;
    private double[] y, x;
    private int[] q;
    private int sumOps, multOps, transOps;

    public LuDrive(Matrix m) {
        n = m.getSize();
        b = new double[n];
        q = new int[n];
        a = new double[n][n];
        for (int i = 0; i < n; i++) {
            b[i] = m.getB(i);
            q[i] = i;
            for (int j = 0; j < n; j++) {
                a[i][j] = m.getA(i, j);
            }
        }
        sumOps = 0;
        multOps = 0;
        transOps = 0;
        createLu();
        createY();
        createX();
    }

    private void createLu() {
        double s;
        int buf;
        double tmp;
        LU = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= j) {
                    s = 0;
                    for (int k = 0; k <= (i - 1); k++) {
                        s += getL(i, k) * getU(k, j);
                        sumOps++;
                        multOps++;
                    }
                    LU[i][j] = a[i][j] - s;
                    sumOps++;
                    if (getU(i, i) == 0) {
                        System.out.println("error => U["+i+","+i+"]=0");
                    }
                } else {
                    s = 0;
                    for (int k = 0; k <= (j - 1); k++) {
                        s += getL(i, k) * getU(k, j);
                        sumOps++;
                        multOps++;
                    }
                    LU[i][j] = (a[i][j] - s) / getU(j, j);
                    sumOps++;
                    multOps++;
                }
            }
            int max = i;
            for (int m = i + 1; m < n; m++) {
                if (Math.abs(LU[i][m]) > Math.abs(LU[i][max])) {
                    max = m;
                }
            }
            for (int k = 0; k <= i; k++) {
                tmp = LU[k][i];
                LU[k][i] = LU[k][max];
                LU[k][max] = tmp;
                transOps++;
            }
            changeColumns(i, max);
            buf = q[i];
            q[i] = q[max];
            q[max] = buf;
            transOps++;
        }
    }

    public double getL(int i, int j) {
        if (i >= j) {
            if (i == j) {
                return 1;
            }
            return LU[i][j];
        } else {
            return 0;
        }
    }

    public double getU(int i, int j) {
        if (i <= j) {
            return LU[i][j];
        } else {
            return 0;
        }
    }

    private void createY() {
        y = new double[n];
        double s;
        for (int i = 0; i < n; i++) {
            s = 0;
            for (int k = 0; k <= (i - 1); k++) {
                s += getL(i, k) * y[k];
                sumOps++;
                multOps++;
            }
            y[i] = b[i] - s;
            sumOps++;
        }
    }

    private void createX() {
        x = new double[n];
        double[] buf = new double[n];
        double s;
        for (int i = n - 1; i >= 0; i--) {
            s = 0;
            for (int k = i + 1; k < n; k++) {
                s += getU(i, k) * buf[k];
                sumOps++;
                multOps++;
            }
            buf[i] = (y[i] - s) / getU(i, i);
            sumOps++;
            multOps++;
        }
        for (int i = 0; i < n; i++) {
            x[q[i]] = buf[i];
        }
    }

    public void printX() {
        for (int i = 0; i < n; i++) {
            System.out.println(x[i]);
        }
    }

    public void printQ() {
        for (int i = 0; i < n; i++) {
            System.out.println(q[i]);
        }
    }

    public void printY() {
        for (int i = 0; i < n; i++) {
            System.out.println(y[i]);
        }
    }

    public void printL() {
        for (int i = 0; i < n; i++) {
            System.out.print("\n");
            for (int j = 0; j < n; j++) {
                System.out.print(getL(i, j) + " ");
            }
        }
        System.out.println("");
    }

    public void printU() {
        for (int i = 0; i < n; i++) {
            System.out.print("\n");
            for (int j = 0; j < n; j++) {
                System.out.print(getU(i, j) + " ");
            }
        }
        System.out.println("");
    }

    private void changeColumns(int k, int m) {
        double tmp;
        for (int i = 0; i < n; i++) {
            tmp = a[i][k];
            a[i][k] = a[i][m];
            a[i][m] = tmp;
            transOps++;
        }
    }

    public void printA() {
        for (int i = 0; i < n; i++) {
            System.out.print("\n");
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j]);
            }
        }
        System.out.println("");
    }

    public double getMistake() {
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            y[i] = (i + 1) - x[i];
        }
        return Matrix.vectorNorm(y) / n;
    }

    public int getSumOps() {
        return sumOps;
    }

    public int getTransOps() {
        return transOps;
    }

    public int getMultOps() {
        return multOps;
    }

    public double[][] getLm() {
        double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = getL(i, j);
            }
        }
        return data;
    }

    public double[][] getUm() {
        double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = getU(i, j);
            }
        }
        return data;
    }

    public Matrix inverseMatrix() {
        double[][] data = new double[n][n];
        double s;
        for (int j = n - 1; j >= 0; j--) {
            for (int i = n - 1; i >= 0; i--) {
                s = 0;
                if (i == j) {
                    for (int k = j + 1; k < n; k++) {
                        s += getU(i, k) * data[k][j];
                    }
                    data[j][j] = (1 - s) / getU(j, j);
                }
                if (i < j) {
                    for (int k = i + 1; k < n; k++) {
                        s += getU(i, k) * data[k][j];
                    }
                    data[i][j] = -s / getU(i, i);
                }
                if (i > j) {
                    for (int k = j + 1; k < n; k++) {
                        //s += getU(i, k) * getL(k, j);
                        s += data[i][k] * getL(k, j);
                    }
                    data[i][j] = -s;
                }
            }
        }
        return new Matrix(data);
    }

    public double determine() {
        double  s = 1;
        for (int i = 0; i < n; i++) {
            s = s * getU(i, i);
        }
        return s;
    }

    public double getCond() {
        return matrixNorm() * inverseMatrix().getMatrixNorm();
    }

    private double matrixNorm() {
        double s;
        double max = 0;
        for (int i = 0; i < n; i++) {
            s = 0;
            for (int j = 0; j < n; j++) {
                s += Math.abs(a[i][j]);
            }
            if (s > max) {
                max = s;
            }
        }
        return max;
    }
}
