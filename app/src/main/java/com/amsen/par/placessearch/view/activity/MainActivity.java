package com.amsen.par.placessearch.view.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.amsen.par.placessearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static rx.schedulers.Schedulers.test;

/**
 * @author Pär Amsen 2016
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.hello)
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }
}
