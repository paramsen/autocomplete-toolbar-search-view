package com.amsen.par.placessearch.demo.api;

import com.amsen.par.placessearch.demo.model.Prediction;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * @author Pär Amsen 2016
 */
public class PlacesApiTest {
    PlacesApi resource;

    @Before
    public void before() {
        resource = new PlacesApi();
    }

    @Test
    public void getPredictions() throws Exception {
        List<Prediction> predictions = resource.getPredictions("Amoeba").toBlocking().first();

        assertTrue(predictions.size() > 0);
        assertTrue(predictions.get(0).terms.size() > 0);
    }
}