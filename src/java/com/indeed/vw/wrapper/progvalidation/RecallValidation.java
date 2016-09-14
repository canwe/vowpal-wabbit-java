package com.indeed.vw.wrapper.progvalidation;

import com.google.common.base.Preconditions;

/**
 * true positives / (true positives + false negatives)
 */
public class RecallValidation extends ProgressiveValidation {
    private final double decisionThreashold;
    private double truePositives = 0;
    private double falseNegatives = 0;

    public RecallValidation(final double decisionThreashold) {
        super("Re", true);
        this.decisionThreashold = decisionThreashold;
    }

    @Override
    public synchronized double getScore() {
        if (truePositives + falseNegatives == 0) {
            return 0;
        }
        return truePositives / (truePositives + falseNegatives);
    }

    @Override
    public synchronized void updateScore(final double prediction, final double actual) {
        boolean actualPositive = actual > 0;
        boolean predictionPositive = prediction > decisionThreashold;
        if (actualPositive && predictionPositive) {
            truePositives++;
        }
        if (actualPositive && !predictionPositive) {
            falseNegatives++;
        }
    }
}
