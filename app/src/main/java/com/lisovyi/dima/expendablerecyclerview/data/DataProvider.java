package com.lisovyi.dima.expendablerecyclerview.data;

import android.util.Log;

import com.lisovyi.dima.expendablerecyclerview.data.model.GroupItem;
import com.lisovyi.dima.expendablerecyclerview.data.remote.ItemService;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataProvider {

    private static final String BASE_URL = "https://e97b7234-fde2-4f99-8414-b368658a363b.mock.pstmn.io";

    private final Retrofit retrofit;

    public DataProvider() {
        retrofit = createRestAdapter();
    }

    private Retrofit createRestAdapter() {
        return new Retrofit.Builder()
                .client(createClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(logging);
        return okHttpClient.build();
    }

    public void getItems(ResponseListener<List<GroupItem>> listener) {
        ItemService itemService = retrofit.create(ItemService.class);
        Call call = itemService.getItems();
        call.enqueue(new SimpleCallback<>(listener));
    }

    public static class SimpleCallback<T> implements Callback<T> {

        private ResponseListener<T> listener;

        SimpleCallback(ResponseListener<T> listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                Log.d("DataProvider", "success response from API");
                if (listener != null) {
                    listener.onSuccess(response.body());
                }
            } else {
                Log.d("DataProvider", "failure response from API");
                if (listener != null) {
                    listener.onFailure(response.code());
                }
            }
            listener = null;
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d("DataProvider", "error loading from API");
            if (listener != null) {
                listener.onFailure(0);
            }
            listener = null;
        }
    }

    public interface ResponseListener<T> {
        void onSuccess(T body);
        void onFailure(int errorCode);
    }
}
