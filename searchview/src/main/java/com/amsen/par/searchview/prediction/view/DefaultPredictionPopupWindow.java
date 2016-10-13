package com.amsen.par.searchview.prediction.view;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amsen.par.searchview.R;
import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.adapter.BasePredictionAdapter;
import com.amsen.par.searchview.prediction.adapter.DefaultPredictionAdapter;
import com.amsen.par.searchview.util.ViewUtils;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class DefaultPredictionPopupWindow extends BasePredictionPopupWindow {
    private RecyclerView recycler;
    private FrameLayout emptyContainer;
    private BasePredictionAdapter adapter;
    private ViewGroup appBar;

    public DefaultPredictionPopupWindow(Context context, ViewGroup appBar) {
        this(context, new DefaultPredictionAdapter(), appBar);
    }

    public DefaultPredictionPopupWindow(Context context, BasePredictionAdapter<?> adapter, ViewGroup appBar) {
        super(context, R.layout.view_popup);

        this.adapter = adapter;
        this.appBar = appBar;
        recycler = (RecyclerView) getContentView().findViewById(R.id.recycler);
        emptyContainer = (FrameLayout) getContentView().findViewById(R.id.emptyContainer);
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

    @Override
    public void show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            showAtLocation(appBar, Gravity.NO_GRAVITY, 0, (int) (appBar.getHeight() + ViewUtils.pxFromDp(getContext(), 25)));
        } else {
            showAsDropDown(appBar);
        }
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    public BasePredictionAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BasePredictionAdapter adapter) {
        this.adapter = adapter;
    }

    public FrameLayout getEmptyContainer() {
        return emptyContainer;
    }
}
