package com.lisovyi.dima.expendablerecyclerview.data.remote;

import com.lisovyi.dima.expendablerecyclerview.data.model.GroupItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemService {

    @GET("/list")
    Call<List<GroupItem>> getItems();

}
