package com.amsen.par.searchview;

import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amsen.par.placessearch.model.Prediction;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Pär Amsen 2016
 */
public class PredictionPopupWindow extends PopupWindow {
    @BindViews({R.id.prediction1, R.id.prediction2, R.id.prediction3, R.id.prediction4, R.id.prediction5})
    List<TextView> predictionViews;

    private Unbinder bind;

    public PredictionPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);

        bind = ButterKnife.bind(this, contentView);
    }

    public void applyPredictions(List<Prediction> predictions) {
        for (int i = 0; i < predictions.size(); i++) {
            predictionViews.get(i).setText(predictions.get(i).description);
        }
    }

    @Override
    public void dismiss() {
        bind.unbind();
        super.dismiss();
    }

    @OnClick({R.id.prediction1, R.id.prediction2, R.id.prediction3, R.id.prediction4, R.id.prediction5})
    public void onClickPrediction(View v) {

    }
}
