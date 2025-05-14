package neuralNetwork;
import java.io.Serializable;
import java.util.Random;

public class NeuralNetwork implements Serializable {
    int inputNodes, hiddenNodes, outputNodes;
    double[][] weightsIH, weightsHO;
    double[] biasH, biasO;
    double learningRate = 0.1;
    Random rand = new Random();

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        weightsIH = new double[hiddenNodes][inputNodes];
        weightsHO = new double[outputNodes][hiddenNodes];
        biasH = new double[hiddenNodes];
        biasO = new double[outputNodes];

        initWeights();
    }

    private void initWeights() {
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < inputNodes; j++) {
                weightsIH[i][j] = rand.nextGaussian() * 0.01;
            }
            biasH[i] = 0;
        }

        for (int i = 0; i < outputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                weightsHO[i][j] = rand.nextGaussian() * 0.01;
            }
            biasO[i] = 0;
        }
    }
    
    public double[] predict(double[] inputArray) {
        double[] hidden = new double[hiddenNodes];

        for (int i = 0; i < hiddenNodes; i++) {
            hidden[i] = biasH[i];
            for (int j = 0; j < inputArray.length; j++) {
                hidden[i] += inputArray[j] * weightsIH[i][j];
            }
            hidden[i] = sigmoid(hidden[i]);
        }

        double[] output = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            output[i] = biasO[i];
            for (int j = 0; j < hidden.length; j++) {
                output[i] += hidden[j] * weightsHO[i][j];
            }
            output[i] = sigmoid(output[i]);
        }

        return output;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double dsigmoid(double y) {
        return y * (1 - y); 
    }
    
    public void train(double[] inputArray, double[] targetArray) {
        double[] hidden = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            hidden[i] = biasH[i];
            for (int j = 0; j < inputArray.length; j++) {
                hidden[i] += inputArray[j] * weightsIH[i][j];
            }
            hidden[i] = sigmoid(hidden[i]);
        }

        double[] outputs = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            outputs[i] = biasO[i];
            for (int j = 0; j < hiddenNodes; j++) {
                outputs[i] += hidden[j] * weightsHO[i][j];
            }
            outputs[i] = sigmoid(outputs[i]);
        }

        double[] outputErrors = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            outputErrors[i] = targetArray[i] - outputs[i];
        }

        for (int i = 0; i < outputNodes; i++) {
            double gradient = dsigmoid(outputs[i]) * outputErrors[i] * learningRate;
            for (int j = 0; j < hiddenNodes; j++) {
                weightsHO[i][j] += gradient * hidden[j];
            }
            biasO[i] += gradient;
        }

        double[] hiddenErrors = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            hiddenErrors[i] = 0;
            for (int j = 0; j < outputNodes; j++) {
                hiddenErrors[i] += outputErrors[j] * weightsHO[j][i];
            }
        }

        for (int i = 0; i < hiddenNodes; i++) {
            double gradient = dsigmoid(hidden[i]) * hiddenErrors[i] * learningRate;
            for (int j = 0; j < inputArray.length; j++) {
                weightsIH[i][j] += gradient * inputArray[j];
            }
            biasH[i] += gradient;
        }
    }
    
    private static final long serialVersionUID = 1L;
}