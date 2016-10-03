package com.amsen.par.searchview;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.PredictionPopupWindow;
import com.amsen.par.searchview.util.ViewUtils;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class AutoCompleteSearchView extends SearchView {
    private Activity activity;
    private ViewGroup appBar;
    private PredictionPopupWindow popup;
    private OnPredictionClickListener listener;

    public AutoCompleteSearchView(Context context) {
        super(context);

        init(context);
    }

    public AutoCompleteSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public AutoCompleteSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        activity = ViewUtils.getActivity(context);
        appBar = ViewUtils.findActionBar(activity);

        setOnCloseListener(() -> {
            popup.dismiss();
            popup = null;

            return false;
        });
    }

    public void applyPredictions(List<Prediction> predictions) {
        if (popup == null) {
            popup = new PredictionPopupWindow(getContext());

            if(listener != null) {
                popup.setOnPredictionClickListener(listener);
            }
        }

        popup.applyPredictions(predictions);
        showPopup();
    }

    public void showPopup() {
        popup.showAsDropDown(appBar);
    }

    public void setOnPredictionClickListener(OnPredictionClickListener listener) {
        this.listener = listener;
    }
}
