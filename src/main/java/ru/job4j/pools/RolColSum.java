package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;
        public Sums() {
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int[] rowSum = new int[matrix.length];
        int[] colSum = new int[matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                rowSum[row] = rowSum[row] + matrix[row][col];
                colSum[col] = colSum[col] + matrix[row][col];
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(rowSum[i]);
            sums[i].setColSum(colSum[i]);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        System.out.println("Created Sums:" + matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getTask(i, matrix).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int i, int[][] matrix) {
        Sums sums = new Sums();
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum = rowSum + matrix[i][j];
                colSum = colSum + matrix[j][i];
            }
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            return sums;
        });
    }

}

