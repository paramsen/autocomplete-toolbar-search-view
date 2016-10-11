package com.amsen.par.searchview.prediction.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amsen.par.searchview.R;

/**
 * @author Pär Amsen 2016
 */
public class DefaultPredictionAdapter extends BasePredictionAdapter<PredictionHolder> {
    public DefaultPredictionAdapter() {
        super();
    }

    @Override
    public PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultPredictionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_prediction, parent, false));
    }
}