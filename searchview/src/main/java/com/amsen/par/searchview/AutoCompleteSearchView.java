package com.amsen.par.searchview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.amsen.par.searchview.prediction.Prediction;
import com.amsen.par.searchview.prediction.view.DefaultPredictionPopupWindow;
import com.amsen.par.searchview.prediction.view.PredictionPopupWindow;
import com.amsen.par.searchview.util.ViewUtils;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.amsen.par.searchview.util.ViewUtils.pxFromDp;

/**
 * @author Pär Amsen 2016
 */
public class AutoCompleteSearchView extends SearchView {
    private Activity activity;
    private ViewGroup appBar;
    private ProgressBar progressBar;
    private PredictionPopupWindow popup;
    private OnPredictionClickListener onPredictionClickListener;
    private OnQueryTextListener externalOnQueryTextListener;
    private OnCloseListener externalOnCloseListener;

    private boolean attached;
    private String latestQuery;
    private boolean useDefaultProgressBar = false;
    private boolean useDefaultPredictionPopupWindow = true;
    private float statusBarHeight = pxFromDp(getContext(), 25);

    public AutoCompleteSearchView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoCompleteSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoCompleteSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        activity = ViewUtils.getActivity(context);
        appBar = ViewUtils.findActionBar(activity);

        setImeOptions(EditorInfo.IME_ACTION_DONE);

        super.setOnCloseListener(() -> {
            if (externalOnCloseListener != null)
                externalOnCloseListener.onClose();

            dismissPopup();

            if (!useDefaultPredictionPopupWindow)
                popup = null;

            return false;
        });

        super.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, 0);

                if (externalOnQueryTextListener != null) {
                    return externalOnQueryTextListener.onQueryTextSubmit(query);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (externalOnQueryTextListener != null) {
                    externalOnQueryTextListener.onQueryTextChange(newText);
                }

                if (newText.length() == 0) {
                    onEmptyQuery();
                }

                latestQuery = newText;

                return true;
            }
        });
    }

    @Override
    public void setOnCloseListener(OnCloseListener listener) {
        this.externalOnCloseListener = listener;
    }

    private void onEmptyQuery() {
        dismissPopup();
        hideProgressBar();
    }

    public void setPredictionPopupWindow(PredictionPopupWindow popup) {
        if (useDefaultPredictionPopupWindow)
            throw new RuntimeException("You are using the builtin popup, call setUseDefaultPredictionPopupWindow(false)");

        this.popup = popup;
    }

    private ProgressBar initProgressBar(Context context) {
        XmlPullParser parser = getResources().getXml(R.xml.progressbar_raw_layout);

        try {
            parser.next();
            parser.nextTag();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AttributeSet attr = Xml.asAttributeSet(parser);

        MaterialProgressBar progressBar = new MaterialProgressBar(context, attr, 0, me.zhanghai.android.materialprogressbar.R.style.Widget_MaterialProgressBar_ProgressBar_Horizontal_NoPadding);
        progressBar.setTag(getClass().getName());
        progressBar.setVisibility(GONE);

        int progressBarHeight = (int) pxFromDp(getContext(), 4);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, progressBarHeight);
        int extraMargin = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            extraMargin = (int) pxFromDp(getContext(), 1); //margin on newer devices

        layoutParams.topMargin = (int) (statusBarHeight + appBar.getHeight() - progressBarHeight - extraMargin);
        progressBar.setLayoutParams(layoutParams);

        FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
        decorView.addView(progressBar, layoutParams);

        return progressBar;
    }

    public void setUseDefaultProgressBar(boolean useDefaultProgressBar) {
        this.useDefaultProgressBar = useDefaultProgressBar;

        if (useDefaultProgressBar) {
            progressBar = initProgressBar(getContext());
        }
    }

    public void setProgressBarTheme(@StyleRes int theme) {
        FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
        decorView.removeView(progressBar);
        progressBar = initProgressBar(new ContextThemeWrapper(getContext(), theme));
    }

    public void setUseDefaultPredictionPopupWindow(boolean useDefaultPredictionPopupWindow) {
        this.useDefaultPredictionPopupWindow = useDefaultPredictionPopupWindow;
    }

    public void applyPredictions(List<Prediction> predictions) {
        if (latestQuery.length() > 0) {
            if (popup == null) {
                if (!useDefaultPredictionPopupWindow)
                    throw new RuntimeException("You have declared to not use the default popup, you need to call setPredictionPopupWindow with your instance");

                popup = new DefaultPredictionPopupWindow(getContext(), appBar);

                if (onPredictionClickListener != null) {
                    popup.setOnPredictionClickListener(onPredictionClickListener);
                }
            }

            popup.applyPredictions(predictions);
            showPopup();
        }
    }

    public void dismissPredictionView() {
        dismissPopup();
    }

    @Override
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        externalOnQueryTextListener = listener;
    }

    public void showProgressBar() {
        if (attached && progressBar != null)
            progressBar.setVisibility(VISIBLE);
    }

    public void hideProgressBar() {
        if (progressBar != null)
            progressBar.setVisibility(GONE);
    }

    public void showPopup() {
        if (appBar == null)
            throw new RuntimeException("Could not automatically find the appBar (Toolbar/ActionBar/Etc), supply it yourself via AutoCompleteSearchView.setAppBar or expect major errors, this is probably due to subclassing the Toolbar/ActionBar or using a 3rd party one");

        if (attached) {
            popup.show();
        }
    }

    public void setAppBar(ViewGroup appBar) {
        this.appBar = appBar;
    }

    public void dismissPopup() {
        if (popup != null) {
            popup.dismiss();
        }
    }

    public void setOnPredictionClickListener(OnPredictionClickListener listener) {
        this.onPredictionClickListener = listener;

        if (popup != null)
            popup.setOnPredictionClickListener(listener);
    }

    public PredictionPopupWindow getPopup() {
        return popup;
    }

    public boolean isAttached() {
        return attached;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        attached = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        attached = false;

        dismissPopup();

        super.onDetachedFromWindow();
    }
}
