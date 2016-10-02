package com.amsen.par.searchview;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author Pär Amsen 2016
 */
public class PlaceSearchView extends SearchView {
    private Activity activity;
    private ViewGroup appBar;
    private PredictionPopupWindow popup;

    public PlaceSearchView(Context context) {
        super(context);

        init(context);
    }

    public PlaceSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public PlaceSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    /**
     * Because Android sometimes wraps the Context in a ContextWrapper
     * This is recursive for ContextWrappers wrapped in ContextWrappers.
     */
    public static Activity getActivity(Context context) {
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof Activity) {
            activity = (Activity) ((ContextWrapper) context).getBaseContext();
        } else if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof ContextWrapper) {
            activity = getActivity(((ContextWrapper) context).getBaseContext());
        }

        if (activity == null) {
            getActivity(context);
        }

        return activity;
    }

    private void init(Context context) {
        activity = getActivity(context);
        appBar = findActionBar(activity);

        setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0)
                    onQueryChange(newText);

                return true;
            }
        });

        setOnCloseListener(() -> {
            popup.dismiss();
            popup = null;

            return false;
        });
    }

    private void onQueryChange(String query) {

    }

    public void displayPopup(View anchorView) {
        popup.showAsDropDown(anchorView);
    }

    @NonNull
    private PredictionPopupWindow createPopup() {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.view_popup, null);
        PredictionPopupWindow popup = new PredictionPopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.prediction_popup_bg));
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            popup.setElevation(24);

        return popup;
    }

    public ViewGroup findActionBar(Activity activity) {
        int id = activity.getResources().getIdentifier("action_bar", "id", "android");
        ViewGroup actionBar = null;
        if (id != 0) {
            actionBar = (ViewGroup) activity.findViewById(id);
        }
        if (actionBar == null) {
            actionBar = findToolbar((ViewGroup) activity.findViewById(android.R.id.content)
                    .getRootView());
        }
        return actionBar;
    }

    private ViewGroup findToolbar(ViewGroup viewGroup) {
        ViewGroup toolbar = null;
        for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getClass().getName().equals("android.support.v7.widget.Toolbar")
                    || view.getClass().getName().equals("android.widget.Toolbar")) {
                toolbar = (ViewGroup) view;
            } else if (view instanceof ViewGroup) {
                toolbar = findToolbar((ViewGroup) view);
            }
            if (toolbar != null) {
                break;
            }
        }
        return toolbar;
    }
}
