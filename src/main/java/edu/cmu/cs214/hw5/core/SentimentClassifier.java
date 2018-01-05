package edu.cmu.cs214.hw5.core;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

/**
 * Sentiment classifier. Builds the classifier from the classifier.txt file and
 * uses it to determine sentiment in text.
 * 
 * We suppress warnings here as the code is taken directly from the library's
 * website.
 * 
 * @author JordanBrown
 *
 */
public class SentimentClassifier {
  private static final String POS = "pos";
  private static final String NEU = "neu";
  private static final String FILEPATH = "src/resources/classifier.txt";
  @SuppressWarnings("rawtypes")
  private LMClassifier classifier;

  /**
   * Constructs the sentiment classifier from the file specific in FILEPATH.
   */
  @SuppressWarnings("rawtypes")
  public SentimentClassifier() {
    try {
      classifier = (LMClassifier) AbstractExternalizable.readObject(new File(FILEPATH));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param text
   *          Text to classify sentiment.
   * @return The sentiment return by running through the classifier.
   */
  public Sentiment classify(String text) {
    ConditionalClassification classification = classifier.classify(text);
    switch (classification.bestCategory()) {
    case (POS):
      return Sentiment.POSITIVE;
    case (NEU):
      return Sentiment.NEUTRAL;
    default:
      return Sentiment.NEGATIVE;
    }
  }
}