package com.amsen.par.searchview.prediction.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;

/**
 * @author Pär Amsen 2016
 */
public abstract class PredictionHolder extends RecyclerView.ViewHolder {
    public PredictionHolder(View itemView) {
        super(itemView);
    }

    public abstract void apply(int position, Prediction prediction, OnPredictionClickListener listener);
}
