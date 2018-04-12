package com.lisovyi.dima.expendablerecyclerview.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class GroupItem extends Item implements Parcelable {

    @SerializedName("children")
    @Expose
    private List<Item> children;
    private boolean expanded;

    public GroupItem() {
        super();
    }

    public GroupItem(String id, String name, Item... children) {
        setId(id);
        setName(name);
        setChildren(Arrays.asList(children));
    }

    protected GroupItem(Parcel in) {
        super(in);
        children = in.createTypedArrayList(Item.CREATOR);
        expanded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(children);
        dest.writeByte((byte) (expanded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroupItem> CREATOR = new Creator<GroupItem>() {
        @Override
        public GroupItem createFromParcel(Parcel in) {
            return new GroupItem(in);
        }

        @Override
        public GroupItem[] newArray(int size) {
            return new GroupItem[size];
        }
    };

    public List<Item> getChildren() {
        return children;
    }

    public void setChildren(List<Item> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return children != null && children.size() > 0;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public boolean isParent() {
        return true;
    }
}
