package com.amsen.par.placessearch.api;

import com.amsen.par.placessearch.api.response.PlacesResponse;
import com.amsen.par.placessearch.model.Prediction;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * @author Pär Amsen 2016
 */
public class PlacesResourceTest {
    PlacesResource resource;

    @Before
    public void before() {
        resource = new PlacesResource();
    }

    @Test
    public void getPredictions() throws Exception {
        List<Prediction> predictions = resource.getPredictions("Amoeba").toBlocking().first();

        assertTrue(predictions.size() > 0);
        assertTrue(predictions.get(0).terms.size() > 0);
    }
}