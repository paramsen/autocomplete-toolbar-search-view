package com.amsen.par.searchview.prediction;

/**
 * @author Pär Amsen 2016
 */
public class Prediction {
    public final Object value;
    public final String displayString;

    public Prediction(Object value, String displayString) {
        this.value = value;
        this.displayString = displayString;
    }
}
