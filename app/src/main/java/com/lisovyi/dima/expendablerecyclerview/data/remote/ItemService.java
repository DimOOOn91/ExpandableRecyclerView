package com.lisovyi.dima.expendablerecyclerview.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemService {

    @GET("/list")
    Call<ItemsResponce> getItems();

}
