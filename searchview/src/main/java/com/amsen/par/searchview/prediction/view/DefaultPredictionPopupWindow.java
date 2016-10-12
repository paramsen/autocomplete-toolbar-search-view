package com.amsen.par.searchview.prediction.view;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amsen.par.searchview.R;
import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.adapter.BasePredictionAdapter;
import com.amsen.par.searchview.prediction.adapter.DefaultPredictionAdapter;
import com.amsen.par.searchview.prediction.adapter.PredictionHolder;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class DefaultPredictionPopupWindow<T extends PredictionHolder> extends BasePredictionPopupWindow {
    private RecyclerView recycler;
    private BasePredictionAdapter adapter;

    public DefaultPredictionPopupWindow(Context context) {
        super(context, R.layout.view_popup);

        recycler = (RecyclerView) getContentView().findViewById(R.id.recycler);
        adapter = new DefaultPredictionAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    public DefaultPredictionPopupWindow(Context context, BasePredictionAdapter<?> adapter) {
        super(context, R.layout.view_popup);

        recycler = (RecyclerView) getContentView().findViewById(R.id.recycler);
        this.adapter = adapter;
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    @Override
    public void applyPredictions(List<Prediction> predictions) {
        adapter.applyPredictions(predictions);
    }

    @Override
    public void setOnPredictionClickListener(OnPredictionClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    public void setRecycler(RecyclerView recycler) {
        this.recycler = recycler;
    }

    public BasePredictionAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BasePredictionAdapter adapter) {
        this.adapter = adapter;
    }
}
