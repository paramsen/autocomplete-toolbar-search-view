package com.amsen.par.searchview.prediction.adapter;

import android.support.v7.widget.RecyclerView;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public abstract class BasePredictionAdapter<T extends PredictionHolder> extends RecyclerView.Adapter<T> implements PredictionAdapter {
    private List<Prediction> predictions;
    private OnPredictionClickListener listener;

    public BasePredictionAdapter() {
        this.predictions = new ArrayList<>();
        this.listener = (position, prediction) -> {
        };
    }

    public void applyPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        holder.apply(position, predictions.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public void setOnItemClickListener(OnPredictionClickListener listener) {
        this.listener = listener;
        notifyDataSetChanged();
    }
}
