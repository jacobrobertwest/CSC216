# Jacob West Final Project - CSC216

For my final project for CSC216, I chose to build a neural network in Java. Using information from a few YouTube tutorials I followed along with, I was able to build the core classes, data structures, methods, and other moving parts that underly a basic neural network.

As a way of expanding this work with my own ideas, I attempted to extend the functionality of this neural network to do the following:
- Iteratively train and test the neural network on identifying handwritten digits using MNIST data in CSV format that I downloaded from Kaggle over 10 iterations, evaluating accuracy of each iteration
- Export/serialize the most accurate iteration as an `ObjectOutputStream` that can later be read back in without re-training
- Build a small Swing application that reads in the serialized model, allows a user to "hand draw" a digit on a small canvas of the same resolution as the training data, and then have the model predict what the digit is

## Files

All the files I programmed for this project are located in the `/src/` folder.

The files that I programmed whilst following along to the tutorials are
- `NeuralNetwork.java`

The files that I programmed whilst expanding with my own ideas are
- `ModelIO.java`
- `TrainMNIST.java`
- `DigitPredictorGUI.java`

** Please note that I did not include the MNIST training or test data CSVs as the files were too big for Github.

## Analysis Questions

### If I tried to implement a solution using "brute force", would it work effectively? Why or why not?
A brute-force approach in the context of identifying a handwritten digit would likely mean manually matching the pixels of the drawn digit to the pixels of every training image using some type of Euclidian distance comparison, averaging that distance grouped by digit, and then outputting whichever digit has the smallest average distance. It seems like something like this could hypothetically could work, but I imagine it would run extremely slow and not scale very well. The estimated time complexity of a brute-force attempt like this would be something around O(N * P), where N is the number of training examples (60,000 for the MNIST dataset) and P is the number of pixels in each image (784 for MNIST). On the contrast, my implemented solution using a graph-based feedforward neural network likely has an estimated time complexity of O((P * H) + (H * O)) where H is the number of hidden nodes (64 in my case) and O is the number of output nodes. This is much faster!

### Did your solution work effectively to solve your problem? Why or why not?
For the actual training of the model to accurately predict digits, my solution worked wonderfully. I was able to achieve >90% accuracy on nearly every iteration of prediction on the randomly sampled test data. My implementation of the GUI, however, had some limitations that made it less effective, primarily being that drawing digits in a 28x28 GUI with a mouse likely results in digits that don't look like handwriting as much, and thus don't align with the training data as well as anticipated.

### What are some issues with your solution?
Upon doing some more research into neural networks, I did notice that the way I initialized biases - as all zeros, seems to not align with best practice. Many implementations I saw on YouTube included initializing biases as random small values. I am not quite sure as to why that is the case, but I hope to uncover that with more research. Furthermore, based on my understanding of statistics through some previously taken statistics classes, I have a feeling there is some level of overfitting going on, especially given that I used a single measure of accuracy as the way that I chose which iteration to serialize as the model used in the application. 
However, in general, the biggest issue with my solution seems to be the lower levels of accuracy I experience when using the application. Anecdotally, when testing out my application myself, I was seeing roughly a 50% accuracy on the predictions, whereas the underlying model had >90% accuracy on the test data. There are probably some bigger underlying root causes for this issue.

### What are some edge cases with your solution?
Some scenarios that might be considered "edge cases" for my solution include ...
- Having the model predict the digit for an empty canvas with no drawing - I didn't encode anything that prevents prediction if no input has been made, which could be considered an edge case.
- Digits that look similar to one another based on user handwriting - 1 and 7, 5 and 6 are both instances that might look somewhat similar that could result in inaccuracy
- Instances where parts of the inputted digit are drawn outside of the bounds of the canvas, which prevents the model from "seeing" the whole input. When testing around with the model, I did notice this happened a number of times and almost always resulted in an incorrect prediction.

### How might you improve your idea in the future?
A few improvements I might make to improve this implementation:
- Do some more research into bias initialization and implement this feature in a way that aligns more with best practices
- Do some more research into overfitting, and implement safegaurds in the model evaluation such that it accounts for it rather than just selecting the most nominally accurate model
- Figure out a way to make use of the GUI closer to handwriting on paper, rather than the current functionality that feels more like "filling in pixels"
- In retrospect, probably should have included confidence scores in the GUI so that it can show the user how confident the model is.