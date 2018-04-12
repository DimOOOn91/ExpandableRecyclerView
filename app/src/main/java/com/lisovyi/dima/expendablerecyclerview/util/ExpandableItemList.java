package com.lisovyi.dima.expendablerecyclerview.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lisovyi.dima.expendablerecyclerview.data.model.GroupItem;
import com.lisovyi.dima.expendablerecyclerview.data.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ExpandableItemList {

    private static final String GROUPS_PARAM = "groups";

    private List<GroupItem> groups;
    private List<Item> visibleItems;

    public ExpandableItemList(@NonNull List<GroupItem> groups) {
        this.groups = groups;
        visibleItems = new ArrayList<>(groups.size());
        for (int i = 0; i < groups.size(); i++) {
            GroupItem group = groups.get(i);
            visibleItems.add(group);
            if (group.hasChildren() && group.isExpanded()) {
                visibleItems.addAll(group.getChildren());
            }
        }
    }

    public Bundle asBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GROUPS_PARAM, (ArrayList<GroupItem>) groups);
        return bundle;
    }

    public static ExpandableItemList fromBundle(Bundle bundle) {
        if (bundle.containsKey(GROUPS_PARAM)) {
            ArrayList<GroupItem> list = bundle.getParcelableArrayList(GROUPS_PARAM);
            if (list != null) {
                return new ExpandableItemList(list);
            }
        }
        return null;
    }

    /**
     * Expand Items for provided GroupItem
     * @param group that should be expanded
     * @return true if group was expanded and false if group is null or it has been already expanded
     */
    public boolean expandGroup(GroupItem group) {
        if (group == null || group.isExpanded() || !group.hasChildren()) {
            return false;
        }
        int currentGroupPosition = getVisibleItemPosition(group);
        List<Item> children = group.getChildren();
        for (int i = children.size() - 1; i >= 0; i--) {
            visibleItems.add(currentGroupPosition + 1, children.get(i));
        }
        group.setExpanded(true);
        return true;
    }

    /**
     * Collapse Items for provided GroupItem
     * @param group that should be collapsed
     * @return true if group was collapsed and false if group is null or it has been already collapsed
     */
    public boolean collapseGroup(GroupItem group) {
        if (group == null || !group.isExpanded()) {
            return false;
        }
        visibleItems.removeAll(group.getChildren());
        group.setExpanded(false);
        return true;
    }

    /**
     * Return index in the array ot -1 if element was collapsed
     * @param item of the list
     * @return index in the array ot -1 if element was collapsed
     */
    public int getVisibleItemPosition(@NonNull Item item) {
        return visibleItems.indexOf(item);
    }

    /**
     * Return count of the groups and expanded items
     * @return count of the groups and expanded items
     */
    public int getVisibleItemCount() {
        return visibleItems.size();
    }


    /**
     * Return the Item or GroupItem from the provided position.
     * If position out of visible items list returns null
     * @param position of visible item
     * @return the Item from the provided position or null if position out of visible items list
     */
    @Nullable
    public Item getVisibleItem(int position) {
        return position >= 0 && position < visibleItems.size() ? visibleItems.get(position) : null;
    }

    /**
     * Returns the list of GroupItem
     * @return the list of GroupItem
     */
    public List<GroupItem> getGroups() {
        return groups;
    }



}
