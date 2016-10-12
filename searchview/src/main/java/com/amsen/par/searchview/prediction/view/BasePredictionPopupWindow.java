package com.amsen.par.searchview.prediction.view;


import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.amsen.par.searchview.R;
import com.amsen.par.searchview.prediction.adapter.PredictionHolder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author Pär Amsen 2016
 */
public abstract class BasePredictionPopupWindow<T extends PredictionHolder> extends PopupWindow implements PredictionPopupWindow {
    public BasePredictionPopupWindow(Context context, @LayoutRes int layout) {
        super(LayoutInflater.from(context).inflate(layout, null), MATCH_PARENT, MATCH_PARENT, false);

        setBackgroundDrawable(ContextCompat.getDrawable(getContentView().getContext(), R.drawable.prediction_popup_bg));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setAnimationStyle(R.style.Animation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            setElevation(24);
    }

    public Context getContext() {
        return getContentView().getContext();
    }
}
