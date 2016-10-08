package com.amsen.par.searchview.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author Pär Amsen 2016
 */
public class ViewUtils {
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

    public static ViewGroup findActionBar(Activity activity) {
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

    private static ViewGroup findToolbar(ViewGroup viewGroup) {
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

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * Run on current thread after the delay.
     */
    public static void delay(final Runnable run, int delay, TimeUnit delayUnit) {
        new Handler().postDelayed(run, delayUnit.toMillis(delay));
    }
}
