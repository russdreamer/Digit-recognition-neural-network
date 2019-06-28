package recognition;

import java.util.Scanner;

public class Main {
    private static int bias = -5;
    private static int[] weights = {2, 1, 2, 4, -4, 4, 2, -1, 2};
    private static final int NUM_ROW = 3;
    private static final int NUM_COLUMN = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            int totalWeight = 0;

            for (int i = 0; i < NUM_ROW; i++){
                String line = scanner.nextLine();
                totalWeight += getLineResult(line, i);
            }
            totalWeight += bias;

            int res = totalWeight < 0? 1: 0;
            System.out.println("This number is " + res);
        }
    }

    private static int getLineResult(String line, int lineNumber) {
        int sum = 0;
        for (int i = 0; i < NUM_COLUMN; i++){
            if (line.charAt(i) == 'X'){
                int index = lineNumber * NUM_COLUMN + i;
                sum += weights[index];
            }
        }
        return sum;
    }
}