package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int size=50;
        double p=1.8;

        try(Scanner sc = new Scanner(System.in);){
            System.out.print("Введите размерность матрицы: ");
            if(sc.hasNextInt()) {
                size = sc.nextInt();
            } else {
                System.out.println("Ошибка ввода размерности");
            }
            System.out.print("Введите параметр альфа для плохо обусловленной матрицы: ");
            if(sc.hasNextDouble()) {
                p = sc.nextDouble();
            } else {
                System.out.println("Ошибка ввода параметра");
            }
        }


        Matrix matr = new Matrix(Matrix.generate(size));
        System.out.println("\n\nЧасть 1 - Случайная матрица размера "+size+"x"+size+"\n");
        double luMist, luDMist, JaMist;
        int luSumOps, luDSumOps, JaSumOps, luMultOps, luDMultOps, JaMultOps, luDTransOps;
        Lu lu = new Lu(matr);
        LuDrive luD = new LuDrive(matr);
        Jacobi ja = new Jacobi(0.00001, matr);
        luMist = lu.getMistake();
        luDMist = luD.getMistake();
        JaMist = ja.getMistake();
        luSumOps = lu.getSumOps();
        luDSumOps = luD.getSumOps();
        JaSumOps = ja.getSumOps();
        luMultOps = lu.getMultOps();
        luDMultOps = luD.getMultOps();
        JaMultOps = ja.getMultOps();
        luDTransOps = luD.getTransOps();
        System.out.println("Метод:\t\t\t\t\t\t\t\tLU\t\t\t\t\t\tLU-в.в.эл.");
        System.out.println("Относительная погрешность:\t\t" + luMist + "\t" + luDMist);
        System.out.println("Число операций сложения и вычитания:\t" + luSumOps + "\t\t\t" + luDSumOps);
        System.out.println("Число операций умножения и деления:\t" + luMultOps + "\t\t\t" + luDMultOps);
        System.out.println("Число операций перестановки:\t\t0\t\t\t" + luDTransOps + "\n");

        double[][] a = matr.getMatrix();
        a = Matrix.mult(Matrix.transp(a), a);
        GivenseSpectrum qr = new GivenseSpectrum(a, 0.1);
        System.out.println("Определитель матрицы A:\t\t" + lu.determine());
        System.out.println("Число обусловленности:\t\t" + lu.getCond());
        System.out.println("Максимальное собственное число:\t" + qr.maxOwmNumb());
        System.out.println("Минимальное собственное число:\t" + qr.minOwmNumb());

        System.out.println("\n\nЧасть 2 - Плохо обусловленная матрица размера "+size+"x"+size+" c параметром альфа="+p+"\n");


        double[][] b = new double[size][size];
        for (int i = 0; i < size; i++) {
            b[i][i]=Math.pow(p, Math.abs(size-2*i-2))/2;
        }
        for (int i = 1; i < size-1; i++) {
            b[0][i]=b[0][0]/Math.pow(p, i+1);
            b[i][0]=b[0][i];
            b[size-1][i]=b[size-1][size-1]/Math.pow(p, i+1);
            b[i][size-1]=b[0][i];
        }
        Matrix matr2 = new Matrix(b);
        double luMist2, luDMist2, JaMist2;
        int luSumOps2, luDSumOps2, JaSumOps2, luMultOps2, luDMultOps2, JaMultOps2, luDTransOps2;
        Lu lu2 = new Lu(matr2);
        LuDrive luD2 = new LuDrive(matr2);
        Jacobi ja2 = new Jacobi(0.00001, matr2);
        luMist2 = lu2.getMistake();
        luDMist2 = luD2.getMistake();
        JaMist2 = ja2.getMistake();
        luSumOps2 = lu2.getSumOps();
        luDSumOps2 = luD2.getSumOps();
        JaSumOps2 = ja2.getSumOps();
        luMultOps2 = lu2.getMultOps();
        luDMultOps2 = luD2.getMultOps();
        JaMultOps2 = ja2.getMultOps();
        luDTransOps2 = luD2.getTransOps();
        System.out.println("Метод:\t\t\t\t\tLU\t\t\tLU-в.в.эл.");
        System.out.println("Относительная погрешность:\t\t" + luMist2 + "\t" + luDMist2);
        System.out.println("Число операций сложения и вычитания:\t" + luSumOps2 + "\t\t\t" + luDSumOps2);
        System.out.println("Число операций умножения и деления:\t" + luMultOps2 + "\t\t\t" + luDMultOps2);
        System.out.println("Число операций перестановки:\t\t0\t\t\t" + luDTransOps2 + "\n");
        GivenseSpectrum f = new GivenseSpectrum(Matrix.mult(Matrix.transp(b), b), 0.1);
        System.out.println("Определитель матрицы A:\t\t" + lu2.determine());
        System.out.println("Число обусловленности:\t\t" + lu2.getCond());
        System.out.println("Максимальное собственное число:\t" + f.maxOwmNumb());
        System.out.println("Минимальное собственное число:\t" + f.minOwmNumb());
    }
}
