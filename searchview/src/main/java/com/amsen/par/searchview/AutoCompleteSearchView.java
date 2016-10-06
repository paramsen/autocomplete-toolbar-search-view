package com.amsen.par.searchview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.PredictionPopupWindow;
import com.amsen.par.searchview.util.ViewUtils;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * @author Pär Amsen 2016
 */
public class AutoCompleteSearchView extends SearchView {
    private Activity activity;
    private ViewGroup appBar;
    private ProgressBar loader;
    private PredictionPopupWindow popup;
    private OnPredictionClickListener listener;
    private OnQueryTextListener externalListener;

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
        loader = initLoader();

        setOnCloseListener(() -> {
            dismissPopup();
            popup = null;

            return false;
        });

        super.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (externalListener != null) {
                    return externalListener.onQueryTextSubmit(query);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (externalListener != null) {
                    externalListener.onQueryTextChange(newText);
                }

                if (newText.length() == 0) {
                    dismissPopup();
                }

                return true;
            }
        });
    }

    private ProgressBar initLoader() {
        XmlPullParser parser = getResources().getXml(R.xml.test);

        try {
            parser.next();
            parser.nextTag();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AttributeSet attr = Xml.asAttributeSet(parser);

        MaterialProgressBar progressBar = new MaterialProgressBar(getContext(), attr, 0, me.zhanghai.android.materialprogressbar.R.style.Widget_MaterialProgressBar_ProgressBar_Horizontal_NoPadding);
        progressBar.setTag(getClass().getName());
        progressBar.setVisibility(GONE);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) ViewUtils.pxFromDp(getContext(), 4));
        layoutParams.topMargin = (int) (ViewUtils.pxFromDp(getContext(), 25) + appBar.getHeight() - ViewUtils.pxFromDp(getContext(), 4));
        progressBar.setLayoutParams(layoutParams);

        FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
        decorView.addView(progressBar, layoutParams);

        return progressBar;
    }

    public void applyPredictions(List<Prediction> predictions) {
        if (popup == null) {
            popup = new PredictionPopupWindow(getContext());

            if (listener != null) {
                popup.setOnPredictionClickListener(listener);
            }
        }

        popup.applyPredictions(predictions);
        showPopup();
    }

    public void dismissPredictionView() {
        dismissPopup();
    }

    @Override
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        externalListener = listener;
    }

    public void showLoader() {
        loader.setVisibility(VISIBLE);
    }

    public void hideLoader() {
        loader.setVisibility(GONE);
    }

    private void showPopup() {
        popup.showAsDropDown(appBar);
    }

    private void dismissPopup() {
        if (popup != null) {
            popup.dismiss();
        }
    }

    public void setOnPredictionClickListener(OnPredictionClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        dismissPopup();

        try {
            FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
            decorView.removeView(loader);
        } catch (Exception e) {
            //disposed
        }

        super.onDetachedFromWindow();
    }
}
