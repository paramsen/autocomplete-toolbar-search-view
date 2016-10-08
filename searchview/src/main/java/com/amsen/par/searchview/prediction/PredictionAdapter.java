package com.amsen.par.searchview.prediction;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amsen.par.searchview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.amsen.par.searchview.util.ViewUtils.delay;

/**
 * @author Pär Amsen 2016
 */
public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.PredictionHolder> {
    private List<Prediction> predictions;
    private OnPredictionClickListener listener;

    public PredictionAdapter() {
        this.predictions = new ArrayList<>();
        this.listener = (position, prediction) -> {
        };
    }

    public void applyPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
        notifyDataSetChanged();
    }

    @Override
    public PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PredictionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_prediciton, parent, false));
    }

    @Override
    public void onBindViewHolder(PredictionHolder holder, int position) {
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

    public static class PredictionHolder extends RecyclerView.ViewHolder {
        private TextView predictionView;

        public PredictionHolder(View itemView) {
            super(itemView);
            predictionView = (TextView) itemView.findViewById(R.id.prediction1);
        }

        public void apply(int position, Prediction prediction, OnPredictionClickListener listener) {
            predictionView.setText(prediction.displayString);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                predictionView.setOnClickListener(e -> delay(() -> listener.onClick(position, prediction), 300, TimeUnit.MILLISECONDS)); //UI delay for ripple effect
            } else {
                predictionView.setOnClickListener(e -> listener.onClick(position, prediction));
            }
        }
    }
}