package com.amsen.par.searchview.prediction;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.amsen.par.searchview.R;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author Pär Amsen 2016
 */
public class PredictionPopupWindow extends PopupWindow {
    private Context context;
    private RecyclerView recycler;
    private PredictionAdapter adapter;

    public PredictionPopupWindow(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.view_popup, null), MATCH_PARENT, WRAP_CONTENT, false);

        this.context = context;
        recycler = (RecyclerView) getContentView().findViewById(R.id.recycler);
        adapter = new PredictionAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);

        setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.prediction_popup_bg));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setAnimationStyle(R.style.Animation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(24);
    }

    public void applyPredictions(List<Prediction> predictions) {
        adapter.applyPredictions(predictions);
    }

    public void setOnPredictionClickListener(OnPredictionClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }

}
