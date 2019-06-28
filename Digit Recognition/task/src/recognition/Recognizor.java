package recognition;

public class Recognizor {
    private double[][] weights;
    private int[] biases;

    public Recognizor(double[][] weights, int[] biases) {
        this.weights = weights;
        this.biases = biases;
    }

    public int guess(int[] input){
        double[] results = new double[weights.length];

        for (int i = 0; i < weights.length; i++) {
            double s = calculateS(input, weights[i], i);
            results[i] = s;
        }

        return getMaxRes(results);
    }

    private int getMaxRes(double[] results) {
        double maxVal = Double.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < results.length; i++) {
            //System.out.println(results[i]);
            if (results[i] > maxVal){
                maxVal = results[i];
                index = i;
            }
        }
        return index;
    }

    private double calculateS(int[] input, double[] neuronWeights, int i) {
        double s = 0;
        for (int j = 0; j < 10; j++) {
            s += input[j] * neuronWeights[j];
        }
        //s += biases[i];
        s = 1/ (1 + Math.pow(Math.E, (0 - s)));
        return s;
    }
}
