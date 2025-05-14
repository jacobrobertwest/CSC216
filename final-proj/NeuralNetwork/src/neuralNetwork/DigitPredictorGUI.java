package neuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DigitPredictorGUI extends JFrame implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final DrawPanel drawPanel;
    private final JButton clearButton;
    private final JButton predictButton;
    private final JLabel resultLabel;
    private final int gridSize = 28;
    private final int scale = 10;
    private final NeuralNetwork model;

    public DigitPredictorGUI() {
        setTitle("Digit Predictor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(gridSize * scale + 300, gridSize * scale + 80);
        setLayout(new BorderLayout());

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.LIGHT_GRAY);

        model = ModelIO.loadModel("resources/best_model.nn");

        drawPanel = new DrawPanel(gridSize, scale);
        clearButton = new JButton("Clear");
        predictButton = new JButton("Predict");
        resultLabel = new JLabel("");

        clearButton.addActionListener(e -> drawPanel.clear());
        predictButton.addActionListener(e -> predictDigit());

        JPanel controls = new JPanel();
        controls.add(clearButton);
        controls.add(predictButton);
        controls.add(resultLabel);

        container.add(drawPanel, BorderLayout.CENTER);
        container.add(controls, BorderLayout.SOUTH);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.LIGHT_GRAY);
        wrapper.add(container, BorderLayout.WEST);
        add(wrapper, BorderLayout.CENTER);

        setResizable(false);
        setVisible(true);
    }

    private void predictDigit() {
        double[] inputs = drawPanel.getPixelData();
        System.out.println("Inputs: " + java.util.Arrays.toString(inputs));

        double[] outputs = model.predict(inputs);
        System.out.println("Outputs: " + java.util.Arrays.toString(outputs));

        int prediction = argmax(outputs);
        resultLabel.setText("Prediction: " + prediction);

    }

    private int argmax(double[] arr) {
        int max = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[max]) max = i;
        }
        return max;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitPredictorGUI::new);
    }
}

class DrawPanel extends JPanel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final int[][] pixels;
    private final int gridSize;
    private final int scale;

    public DrawPanel(int gridSize, int scale) {
        this.gridSize = gridSize;
        this.scale = scale;
        this.pixels = new int[gridSize][gridSize];
        setPreferredSize(new Dimension(gridSize * scale, gridSize * scale));
        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() / scale;
                int y = e.getY() / scale;
                drawWithBrush(x, y);
                repaint();
            }
        });
    }

    private void drawWithBrush(int x, int y) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < gridSize && ny < gridSize) {
                    int distance = Math.abs(dx) + Math.abs(dy);
                    int intensity;
                    if (distance == 0) {
                        intensity = 255;
                    } else if (distance == 1) {
                        intensity = 128;
                    } else {
                        intensity = 64;
                    }
                    pixels[ny][nx] = Math.min(255, pixels[ny][nx] + intensity);
                }
            }
        }
    }

    public void clear() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                pixels[y][x] = 0;
            }
        }
        repaint();
    }

    public double[] getPixelData() {
        double[] data = new double[gridSize * gridSize];
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                data[y * gridSize + x] = pixels[y][x] / 255.0;
            }
        }
        return data;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                int brightness = 255 - pixels[y][x];
                brightness = Math.max(0, Math.min(255, brightness));
                g.setColor(new Color(brightness, brightness, brightness));
                g.fillRect(x * scale, y * scale, scale, scale);
            }
        }
    }
}
