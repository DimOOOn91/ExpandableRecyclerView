package com.lisovyi.dima.expendablerecyclerview.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.lisovyi.dima.expendablerecyclerview.R;
import com.lisovyi.dima.expendablerecyclerview.databinding.ListChildItemBinding;
import com.lisovyi.dima.expendablerecyclerview.databinding.ListParentItemBinding;
import com.lisovyi.dima.expendablerecyclerview.data.model.GroupItem;
import com.lisovyi.dima.expendablerecyclerview.data.model.Item;
import com.lisovyi.dima.expendablerecyclerview.util.ExpandableItemList;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.BaseItemHelper> {

    private static final int PARENT_ITEM = 1;
    private static final int CHILD_ITEM = 2;

    private static final String SELECTED_ITEM_PARAM = "selected_item";

    private Item selectedItem = null;

    private ExpandableItemList items;

    public ItemListAdapter() {
    }

    public void setGroups(List<GroupItem> groups) {
        this.items = new ExpandableItemList(groups);
        notifyDataSetChanged();
    }

    public List<GroupItem> getGroups() {
        return items.getGroups();
    }

    @NonNull
    @Override
    public BaseItemHelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case PARENT_ITEM:
                ListParentItemBinding parentBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.list_parent_item, parent, false);
                return new ParentItemHelper(parentBinding);

            case CHILD_ITEM:
                ListChildItemBinding childBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.list_child_item, parent, false);
                return new ChildItemHelper(childBinding);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseItemHelper holder, int position) {
        switch (holder.getItemViewType()) {
            case PARENT_ITEM:
                GroupItem group = (GroupItem) items.getVisibleItem(position);
                final ParentItemHelper groupHolder = (ParentItemHelper) holder;
                groupHolder.initView(group);
                groupHolder.binding.btnParent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupItem group = groupHolder.binding.getItem();
                        if (group == null) {
                            return;
                        }
                        if (group.isExpanded()) {
                            boolean success = items.collapseGroup(group);
                            if (success) {
                                groupHolder.binding.ivExpandArrow.animate().rotation(0f).setDuration(200).start();
                                notifyItemRangeRemoved(holder.getAdapterPosition() + 1,
                                        group.getChildren().size());
                            }
                        } else {
                            boolean success = items.expandGroup(group);
                            if (success) {
                                groupHolder.binding.ivExpandArrow.animate().rotation(-180f).setDuration(200).start();
                                notifyItemRangeInserted(holder.getAdapterPosition() + 1,
                                        group.getChildren().size());
                            }
                        }
                    }
                });
                break;

            case CHILD_ITEM:
                final Item item = items.getVisibleItem(position);
                ChildItemHelper childHolder = (ChildItemHelper) holder;
                childHolder.initView(item);
                CheckBox cbItem = childHolder.binding.cbItem;
                cbItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectNewItem(item);
                    }
                });
                break;

            default:
                break;
        }
    }

    private void selectNewItem(Item item) {
        if (selectedItem != null) {
            selectedItem.setChecked(false);
            int position = items.getVisibleItemPosition(selectedItem);
            if (position != -1) {
                notifyItemChanged(position);
            }
        }
        if (selectedItem != null && selectedItem.equals(item)) {
            selectedItem = null;
        } else {
            selectedItem = item;
        }
    }


    @Override
    public int getItemCount() {
        return items != null ? items.getVisibleItemCount() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        Item item = items.getVisibleItem(position);
        return item != null && !item.isParent() ? CHILD_ITEM : PARENT_ITEM;
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SELECTED_ITEM_PARAM)) {
            selectedItem = savedInstanceState.getParcelable(SELECTED_ITEM_PARAM);
        }
        items = ExpandableItemList.fromBundle(savedInstanceState);
        if (items != null) {
            notifyDataSetChanged();
        }
    }

    public void saveInstanceState(Bundle outState) {
        if (items != null) {
            outState.putAll(items.asBundle());
        }
        if (selectedItem != null) {
            outState.putParcelable(SELECTED_ITEM_PARAM, selectedItem);
        }
    }

    public static class ParentItemHelper extends BaseItemHelper<ListParentItemBinding> {
        ParentItemHelper(ListParentItemBinding binding) {
            super(binding);
        }

        void initView(GroupItem groupItem) {
            binding.setItem(groupItem);
        }
    }

    public static class ChildItemHelper extends BaseItemHelper<ListChildItemBinding> {
        ChildItemHelper(ListChildItemBinding binding) {
            super(binding);
        }

        void initView(Item item) {
            binding.setItem(item);
        }
    }

    static class BaseItemHelper<T extends ViewDataBinding>
            extends RecyclerView.ViewHolder {
        T binding;

        BaseItemHelper(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
