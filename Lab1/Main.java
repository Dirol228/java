public class Main {



    public static double[][] multiplyMatrices(double[][] A, double[][] B) throws IllegalArgumentException {
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("Матриці неможливо перемножити: невідповідність розмірів.");
        }

        int rows = A.length;
        int cols = B[0].length;
        int common = A[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < common; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }


    public static double sumOfRowMax(double[][] matrix) {
        double sum = 0;
        for (double[] row : matrix) {
            double max = row[0];
            for (double val : row) {
                if (val > max) {
                    max = val;
                }
            }
            sum += max;
        }
        return sum;
    }


    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%8.2f", val);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {

            double[][] A = {
                    {1, 2, 3},
                    {4, 5, 6}
            };

            double[][] B = {
                    {7, 8},
                    {9, 10},
                    {11, 12}
            };

            System.out.println("Матриця A:");
            printMatrix(A);

            System.out.println("\nМатриця B:");
            printMatrix(B);


            double[][] C = multiplyMatrices(A, B);

            System.out.println("\nРезультат добутку матриць (C = A * B):");
            printMatrix(C);


            double sumMax = sumOfRowMax(C);
            System.out.println("\nСума найбільших елементів у кожному рядку матриці C = " + sumMax);

        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Несподівана помилка: " + e);
        }
    }
}
