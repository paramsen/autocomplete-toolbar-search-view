package com.amsen.par.searchview.demo.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amsen.par.searchview.AutoCompleteSearchView;
import com.amsen.par.searchview.demo.R;
import com.amsen.par.searchview.demo.api.MockApi;
import com.amsen.par.searchview.prediction.Prediction;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Pär Amsen 2016
 */
public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics firebaseAnalytics;
    private MockApi api;
    private AutoCompleteSearchView searchView;
    private TimerTask fakeNetworkCall;
    private Timer fakeNetworkThread;

    private View callToActionView;
    private View searchedTitle;
    private TextView predictionResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        api = new MockApi();
        fakeNetworkThread = new Timer();

        callToActionView = findViewById(R.id.clickSearch);
        searchedTitle = findViewById(R.id.title);
        predictionResultView = (TextView) findViewById(R.id.selectedPrediction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        searchView = (AutoCompleteSearchView) searchViewItem.getActionView();

        searchView.setOnPredictionClickListener((position, prediction) -> {
            callToActionView.setVisibility(View.GONE);
            searchedTitle.setVisibility(View.VISIBLE);
            predictionResultView.setVisibility(View.VISIBLE);

            Toast.makeText(this, String.format("clicked [position:%d, value:%s, displayString:%s]", position, prediction.value, prediction.displayString), Toast.LENGTH_SHORT).show();
            predictionResultView.setText("You tapped: " + prediction.displayString);
            searchViewItem.collapseActionView();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    searchView.showLoader();

                    fakeNetworkPredictions(newText);
                }

                return true;
            }
        });
        return true;
    }

    /**
     * Async predictions with a delay of 400-1500ms
     * If called again before the old "request" finished
     * the old will be canceled and the new starts
     * instead.
     */
    private void fakeNetworkPredictions(final String query) {
        if (fakeNetworkCall != null) {
            fakeNetworkCall.cancel();
            fakeNetworkCall = null;
        }

        fakeNetworkCall = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    fakeNetworkCall = null;

                    List<String> rawPredictions = api.getPredictions(query);
                    List<Prediction> predictions = toSearchViewPredictions(rawPredictions);
                    searchView.applyPredictions(predictions);
                    searchView.hideLoader();
                });
            }
        };

        fakeNetworkThread.schedule(fakeNetworkCall, 400 + (long) (Math.random() * 1100));
    }

    private List<com.amsen.par.searchview.prediction.Prediction> toSearchViewPredictions(List<String> predictions) {
        List<Prediction> forSearchView = new ArrayList<>();

        for (String prediction : predictions) {
            forSearchView.add(new Prediction(prediction, prediction)); //first param is for complex objects, second for display string.
        }

        return forSearchView;
    }
}
