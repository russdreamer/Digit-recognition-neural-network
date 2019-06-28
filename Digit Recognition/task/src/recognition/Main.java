package recognition;

import java.util.Scanner;

public class Main {
    private static final int ROW_NUM = 5;
    private static final int COLUMN_NUM = 3;
    private static Scanner scanner = new Scanner(System.in);
    private static int[] biases = new int[10];
    private static int[][] weights = new int[10][];

    public static void main(String[] args) {
        fillBiases();
        fillWeights();
        while (scanner.hasNextLine()){
            boolean[] layerArr = readDigit();
            int[] secondLayer = processImage(layerArr);
            int res = findMaxValue(secondLayer);
            System.out.println("This number is " + res);
        }
    }

    private static int findMaxValue(int[] secondLayer) {
        int maxIndex = 0;
        for (int i = 0; i < secondLayer.length; i++) {
            if (secondLayer[i] > secondLayer[maxIndex])
                maxIndex = i;
        }

        return maxIndex;
    }


    private static void fillWeights() {
        weights[0] = new int[]{1, 1, 1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, 1, 1};
        weights[1] = new int[]{-1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1};
        weights[2] = new int[]{1, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, 1 };
        weights[3] = new int[]{1, 1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1 };
        weights[4] = new int[]{1, -1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1};
        weights[5] = new int[]{1, 1, 1, 1, -1, -1, 1, 1, 1, -1, -1, 1, 1, 1, 1 };
        weights[6] = new int[]{1, 1, 1, 1, -1, -1, 1, 1, 1, 1, -1, 1, 1, 1, 1 };
        weights[7] = new int[]{1, 1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1};
        weights[8] = new int[]{1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1};
        weights[9] = new int[]{1, 1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1};
    }

    private static void fillBiases() {
        biases[0] = -1;
        biases[1] = 6;
        biases[2] = 0;
        biases[3] = 0;
        biases[4] = 2;
        biases[5] = 0;
        biases[6] = -1;
        biases[7] = 4;
        biases[8] = -2;
        biases[9] = -1;
    }

    private static int[] processImage(boolean[] image) {
        int[] resArr = new int[10];
        for (int i = 0; i < biases.length; i++){
            int res = sendToSecondLayer(image, i) + biases[i];
            resArr[i] = res;
        }

        return resArr;
    }

    private static int sendToSecondLayer(boolean[] image, int index) {
        int sum = 0;
        for (int i = 0; i < image.length; i++){
            if (image[i])
                sum += weights[index][i];
        }
        return sum;
    }

    private static boolean[] readDigit() {
        boolean[] image = new boolean[ROW_NUM * COLUMN_NUM];
        for (int i = 0; i < ROW_NUM; i++){
            String line = scanner.nextLine();

            for (int j = 0; j < COLUMN_NUM; j++){
                int index = i * COLUMN_NUM + j;
                image[index] = line.charAt(j) == 'X';
            }
        }

        return image;
    }
}