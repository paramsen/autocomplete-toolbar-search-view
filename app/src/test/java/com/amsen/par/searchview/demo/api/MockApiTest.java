package com.amsen.par.searchview.demo.api;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Pär Amsen 2016
 */
public class MockApiTest {
    @Test
    public void getPredictions() throws Exception {
        MockApi api = new MockApi();

        List<String> predictions = api.generatePredictions(5, "a");

        assertEquals("abcde", predictions.get(0));
        assertEquals("a", predictions.get(predictions.size() - 1));
    }

}