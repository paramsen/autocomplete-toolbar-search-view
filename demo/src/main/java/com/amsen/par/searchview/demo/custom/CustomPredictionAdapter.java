package com.amsen.par.searchview.demo.custom;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amsen.par.searchview.demo.R;
import com.amsen.par.searchview.prediction.adapter.BasePredictionAdapter;

/**
 * @author Pär Amsen 2016
 */
public class CustomPredictionAdapter extends BasePredictionAdapter<CustomPredictionViewHolder> {
    @Override
    public CustomPredictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomPredictionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_custom_prediction, parent, false));
    }
}
