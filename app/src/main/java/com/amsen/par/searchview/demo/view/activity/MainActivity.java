package com.amsen.par.searchview.demo.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import com.amsen.par.searchview.AutoCompleteSearchView;
import com.amsen.par.searchview.demo.R;
import com.amsen.par.searchview.demo.api.PlacesApi;
import com.amsen.par.searchview.demo.model.Prediction;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Pär Amsen 2016
 */
public class MainActivity extends AppCompatActivity {
    private PlacesApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = new PlacesApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        AutoCompleteSearchView searchView = (AutoCompleteSearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnPredictionClickListener((position, prediction) -> {
            Toast.makeText(this, String.format("clicked [position:%d, id:%d, prediction:%s]", position, prediction.id, prediction.prediction), Toast.LENGTH_SHORT).show();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                api.getPredictions(newText)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(MainActivity.this::toSearchViewPredictions)
                        .subscribe(searchView::applyPredictions, Throwable::printStackTrace);

                return true;
            }
        });
        return true;
    }

    private List<com.amsen.par.searchview.prediction.Prediction> toSearchViewPredictions(List<Prediction> predictions) {
        List<com.amsen.par.searchview.prediction.Prediction> forSearchView = new ArrayList<>();

        for (Prediction prediction : predictions) {
            forSearchView.add(new com.amsen.par.searchview.prediction.Prediction(prediction.id.hashCode(), prediction.description));
        }

        return forSearchView;
    }
}
