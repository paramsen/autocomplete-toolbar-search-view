package com.amsen.par.searchview.demo.custom;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amsen.par.searchview.demo.R;
import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.adapter.PredictionHolder;

/**
 * @author Pär Amsen 2016
 */
public class CustomPredictionViewHolder extends PredictionHolder {
    ViewGroup container;
    TextView title;
    ImageView image;

    public CustomPredictionViewHolder(View itemView) {
        super(itemView);
        container = (ViewGroup) itemView.findViewById(R.id.container);
        title = (TextView) itemView.findViewById(R.id.title);
        image = (ImageView) itemView.findViewById(R.id.image);
    }

    @Override
    public void apply(int position, Prediction prediction, OnPredictionClickListener listener) {
        title.setText(prediction.displayString);
        container.setOnClickListener(e -> listener.onClick(position, prediction));
    }
}
