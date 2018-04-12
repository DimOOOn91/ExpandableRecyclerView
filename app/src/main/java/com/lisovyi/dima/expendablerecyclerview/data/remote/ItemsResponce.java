package com.lisovyi.dima.expendablerecyclerview.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lisovyi.dima.expendablerecyclerview.data.model.GroupItem;

import java.util.List;

public class ItemsResponce {

    @SerializedName("response")
    @Expose
    List<GroupItem> items;

    public ItemsResponce() {
    }

    public List<GroupItem> getItems() {
        return items;
    }

    public void setItems(List<GroupItem> items) {
        this.items = items;
    }
}
