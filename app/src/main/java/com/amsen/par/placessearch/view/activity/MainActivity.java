package com.amsen.par.placessearch.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.amsen.par.placessearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Pär Amsen 2016
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.hello)
    View v;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displayPopupWindow(search);
                return false;
            }
        });

        return true;
    }

    @OnClick(R.id.hello)
    public void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(MainActivity.this);
        ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.view_popup, null);
        popup.setContentView(layout);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popup.setOutsideTouchable(true);
        popup.setFocusable(false);
        popup.setBackgroundDrawable(getDrawable(R.drawable.test));
        popup.setElevation(24);

        popup.showAsDropDown(anchorView);

        layout.removeViewAt(2);
    }
}
