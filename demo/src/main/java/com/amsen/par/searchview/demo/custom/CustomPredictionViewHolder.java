package com.amsen.par.searchview.demo.custom;

import android.view.View;
import android.widget.TextView;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.adapter.PredictionHolder;

/**
 * @author Pär Amsen 2016
 */
public class CustomPredictionViewHolder extends PredictionHolder {
    TextView title;

    public CustomPredictionViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(com.amsen.par.searchview.R.id.title);
    }

    @Override
    public void apply(int position, Prediction prediction, OnPredictionClickListener listener) {
        title.setText("Yes");
    }
}
