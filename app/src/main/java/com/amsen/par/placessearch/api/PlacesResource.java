package com.amsen.par.placessearch.api;

import com.amsen.par.placessearch.api.response.PlacesResponse;
import com.amsen.par.placessearch.model.Prediction;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Pär Amsen 2016
 */
public class PlacesResource {
    private final static String API_KEY = "<YOUR_API_KEY>";

    private OkHttpClient client = new OkHttpClient();
    private Moshi moshi = new Moshi.Builder().build();
    private JsonAdapter<PlacesResponse> adapter = moshi.adapter(PlacesResponse.class);

    public Observable<List<Prediction>> getPredictions(String query) {
        return fromApi(query)
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<Prediction>> fromApi(String query) {
        return Observable.create(subscriber -> {
            if(API_KEY.contains("YOUR_API_KEY"))
                throw new RuntimeException("Set your API key for this example!");

            Request request = new Request.Builder()
                    .url("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + query + "&key=" + API_KEY)
                    .build();

            Response response;

            try {
                response = client.newCall(request)
                        .execute();
            } catch (IOException e) {
                subscriber.onError(e);
                return;
            }

            PlacesResponse fromJson;

            try {
                ResponseBody body = response.body();
                fromJson = adapter.fromJson(body.source());
                body.close();
            } catch (IOException e) {
                subscriber.onError(e);
                return;
            }

            subscriber.onNext(fromJson.predictions);
            subscriber.onCompleted();
        });
    }
}
