package com.amsen.par.searchview.demo.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class MockApi {
    public List<String> getPredictions(String query) {
        if (query.length() > 0) {
            int amountOfResults = 5;

            return generatePredictions(amountOfResults, query);
        }

        return new ArrayList<>();
    }

    /**
     *  Generate an array of incrementing char sequences;
     *  for args lastChar=a amout=5;
     *  {a, ab, abc, abcd, abcde} is the result
     */
    protected List<String> generatePredictions(int amountOfResults, String query) {
        List<String> mock = new ArrayList<>();
        char lastChar = query.charAt(query.length() -1);

        for (int i = amountOfResults; i > 0; i--) {
            StringBuilder sb = new StringBuilder(query);

            for(int j = 1 ; j < i ; j++) {
                sb.append((char) (lastChar+j));
            }

            mock.add(sb.toString());
        }

        return mock;
    }
}
