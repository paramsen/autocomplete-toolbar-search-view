package com.amsen.par.searchview.demo.model;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class Prediction {
    public final String id;
    public final String placeId;
    public final String description;
    public final List<Terms> terms;

    public Prediction(String id, String placeId, String description, List<Terms> terms) {
        this.id = id;
        this.placeId = placeId;
        this.description = description;
        this.terms = terms;
    }
}
