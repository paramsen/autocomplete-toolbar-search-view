package com.amsen.par.searchview.prediction.view;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public interface PredictionPopupWindow {
    void applyPredictions(List<Prediction> predictions);
    void setOnPredictionClickListener(OnPredictionClickListener listener);
    void dismiss();
    void show();
}
