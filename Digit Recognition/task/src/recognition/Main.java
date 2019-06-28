package recognition;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static int[][] numbers = new int[20][];
    private static double[][] weights = new double[10][];
    private static double μ = 0.5;
    private static int secLayNeurCount = 10;
    private static String filePath = "/Users/iga/Desktop/weights.nn";
    private static int[] biases = new int[10];

    public static void main(String[] args) {
        fillBiases();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Learn the network\n2. Guess a number");
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println("Your choice: " + choice);

        if (choice == 1)
            learnNet();
        else guessNumber(scanner);
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

    private static void guessNumber(Scanner scanner) {
        weights = deserializeWeights();
        if (weights == null)
            throw new NullPointerException("Weights haven't been restored!");
        
        int[] input = readDigit(scanner);
        Recognizor recognizor = new Recognizor(weights, biases);
        int resVal = recognizor.guess(input);
        System.out.println("This number is " + resVal);
    }

    private static int[] readDigit(Scanner scanner) {
        final int ROW_NUM = 5;
        final int COLUMN_NUM = 3;

        StringBuilder grid = new StringBuilder("Input grid:");
        int[] image = new int[ROW_NUM * COLUMN_NUM];
        for (int i = 0; i < ROW_NUM; i++){
            String line = scanner.nextLine();
            grid.append(System.lineSeparator()).append(line);
            for (int j = 0; j < COLUMN_NUM; j++){
                int index = i * COLUMN_NUM + j;
                image[index] = line.charAt(j) == 'X'? 1: 0;
            }
        }
        System.out.println(grid.toString());


        return image;
    }

    private static double[][] deserializeWeights() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filePath)));
            return (double[][]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void learnNet() {
        System.out.println("Learning...");
        fillNumberGrid();
        for (int i = 0; i < secLayNeurCount; i++) {
            NeuronTraining training = new NeuronTraining(μ, numbers, new int[]{i, 10 + i}, biases[i]);
            weights[i] = training.train();
        }
        if (serializeWeights())
            System.out.println("Done! Saved to the file.");
        else throw new NullPointerException("Weights haven't been saved!");
    }

    private static boolean serializeWeights() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filePath)));
            oos.writeObject(weights);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void fillNumberGrid() {
        numbers[0] = new int[]{1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1};
        numbers[1] = new int[]{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0};
        numbers[2] = new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1};
        numbers[3] = new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1};
        numbers[4] = new int[]{1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1};
        numbers[5] = new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1};
        numbers[6] = new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1};
        numbers[7] = new int[]{1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1};
        numbers[8] = new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1};
        numbers[9] = new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1};

        numbers[10] = new int[]{1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1};
        numbers[11] = new int[]{0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1};
        numbers[12] = new int[]{1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1};
        numbers[13] = new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1};
        numbers[14] = new int[]{1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1};
        numbers[15] = new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0};
        numbers[16] = new int[]{0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1};
        numbers[17] = new int[]{1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1};
        numbers[18] = new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0};
        numbers[19] = new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1};
    }
}