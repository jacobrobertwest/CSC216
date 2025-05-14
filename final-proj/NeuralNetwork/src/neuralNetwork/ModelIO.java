package neuralNetwork;
import java.io.*;

public class ModelIO {
    public static void saveModel(NeuralNetwork model, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(model);
            System.out.println("Saved model to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNetwork loadModel(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (NeuralNetwork) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
