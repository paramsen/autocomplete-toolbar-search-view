package com.amsen.par.searchview.prediction.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amsen.par.searchview.R;
import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;

import java.util.concurrent.TimeUnit;

import static com.amsen.par.searchview.util.ViewUtils.delay;

/**
 * @author Pär Amsen 2016
 */
public class DefaultPredictionHolder extends PredictionHolder{
    private TextView predictionView;

    public DefaultPredictionHolder(View itemView) {
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