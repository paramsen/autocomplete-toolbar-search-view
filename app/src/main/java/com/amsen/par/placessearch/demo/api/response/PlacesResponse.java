package com.amsen.par.placessearch.demo.api.response;

import com.amsen.par.placessearch.demo.model.Prediction;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class PlacesResponse {
    //You probably want to create a separate
    //model for your API mapping, I'm using the domain
    //model to avoid complexity
    public final List<Prediction> predictions;

    public PlacesResponse(List<Prediction> predictions) {
        this.predictions = predictions;
    }
}
