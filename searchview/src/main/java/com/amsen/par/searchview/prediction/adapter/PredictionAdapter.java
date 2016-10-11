package com.amsen.par.searchview.prediction.adapter;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public interface PredictionAdapter {
    void applyPredictions(List<Prediction> predictions);
    void setOnItemClickListener(OnPredictionClickListener listener);
}
