package recognition;

import java.util.Random;

public class NeuronTraining {
    private double μ;
    private int[][] dataSet;
    private int[] dataSetTrueIndexes;
    private int iterationNum = 10000;
    private double[] weights;
    private double bias = 0;
    private int inputNeuronsAmount = 10;

    private void fillWeights() {
        Random random = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextGaussian();
        }
    }

    public NeuronTraining(double μ, int[][] dataSet, int[] dataSetTrueIndexes, int bias) {
        this.μ = μ;
        this.dataSet = dataSet;
        this.dataSetTrueIndexes = dataSetTrueIndexes;
        weights = new double[dataSet.length];
        //this.bias = bias;
        fillWeights();
    }


    public double[] train(){
        for (int i = 0; i < iterationNum; i++) {
            double[][] Δweights = getAllDeltaWeights();
            double[] ΔwMean = calculateMeanVal(Δweights);
            setupNewWeights(ΔwMean);
        }
        return weights;
    }

    private void setupNewWeights(double[] ΔwMean) {
        for (int i = 0; i < ΔwMean.length; i++) {
            weights[i] = weights[i] + ΔwMean[i];
        }
    }

    private double[] calculateMeanVal(double[][] Δweights) {
        double[] ΔwMean = new double[inputNeuronsAmount];

        for (int i = 0; i < inputNeuronsAmount; i++) {
            double sum = 0;
            for (int j = 0; j < dataSet.length; j++) {
                sum += Δweights[j][i];
            }
            ΔwMean[i] = sum / dataSet.length;
        }
        return ΔwMean;
    }

    private double[][] getAllDeltaWeights() {
        double[][] Δweights = new double[dataSet.length][];

        for (int i = 0; i < dataSet.length; i++) {
            double s = calculateS(dataSet[i]);
            Δweights[i] = getSingleInputDeltaWeight(i, s);
        }

        return Δweights;
    }

    private double[] getSingleInputDeltaWeight(int index, double s) {
        double[] Δweights = new double[10];
        int idealVal = isTargetData(index)? 1: 0;

        for (int i = 0; i < inputNeuronsAmount; i++) {
            double Δw = μ * dataSet[index][i] * (idealVal - s);
            Δweights[i] = Δw;
        }

        return Δweights;
    }

    private boolean isTargetData(int index) {
        for (int number: dataSetTrueIndexes) {
            if (index == number)
                return true;
        }
        return false;
    }

    private double calculateS(int[] input) {
        double s = 0;
        for (int j = 0; j < inputNeuronsAmount; j++) {
            s += input[j] * weights[j];
        }
        s += bias;
        s = 1/ (1 + Math.pow(Math.E, (0 - s)));
        return s;
    }

}
