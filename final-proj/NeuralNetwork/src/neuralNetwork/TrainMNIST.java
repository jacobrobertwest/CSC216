package neuralNetwork;

import java.io.*;
import java.util.*;

public class TrainMNIST {
    public static void main(String[] args) {
        String trainPath = "resources/mnist_train.csv";
        String testPath = "resources/mnist_test.csv";
        int epochs = 5;
        int sessions = 10;
        double bestAccuracy = 0;
        NeuralNetwork bestModel = null;

        List<String[]> trainSet = loadCSV(trainPath);
        List<String[]> testSet = loadCSV(testPath);

        for (int s = 1; s <= sessions; s++) {
            System.out.println("Starting session " + s);

            NeuralNetwork nn = new NeuralNetwork(784, 64, 10);

            for (int e = 0; e < epochs; e++) {
                Collections.shuffle(trainSet);
                for (String[] row : trainSet) {
                    double[] inputs = parseInputs(row);
                    double[] targets = oneHot(Integer.parseInt(row[0]));
                    nn.train(inputs, targets);
                }
                System.out.println("Epoch " + (e + 1) + " complete");
            }

            int correct = 0;
            for (String[] row : testSet) {
                int actual = Integer.parseInt(row[0]);
                double[] inputs = parseInputs(row);
                int predicted = argmax(nn.predict(inputs));
                if (predicted == actual) correct++;
            }

            double accuracy = 100.0 * correct / testSet.size();
            System.out.printf("Session %d accuracy: %.2f%%\n", s, accuracy);

            if (accuracy > bestAccuracy) {
                bestAccuracy = accuracy;
                bestModel = nn;
                ModelIO.saveModel(bestModel, "resources/best_model.nn");
            }
        }

        System.out.printf("Best model accuracy: %.2f%% — saved as best_model.nn\n", bestAccuracy);
    }


    public static List<String[]> loadCSV(String path) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static double[] parseInputs(String[] row) {
        double[] inputs = new double[784];
        for (int i = 1; i <= 784; i++) {
            inputs[i - 1] = Double.parseDouble(row[i]) / 255.0;
        }
        return inputs;
    }

    public static double[] oneHot(int label) {
        double[] output = new double[10];
        output[label] = 1.0;
        return output;
    }

    public static int argmax(double[] arr) {
        int max = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[max]) max = i;
        }
        return max;
    }
}
