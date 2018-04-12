package com.lisovyi.dima.expendablerecyclerview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.lisovyi.dima.expendablerecyclerview.adapters.ItemListAdapter;
import com.lisovyi.dima.expendablerecyclerview.data.DataProvider;
import com.lisovyi.dima.expendablerecyclerview.data.DataProvider.ResponseListener;
import com.lisovyi.dima.expendablerecyclerview.data.model.GroupItem;
import com.lisovyi.dima.expendablerecyclerview.data.model.Item;
import com.lisovyi.dima.expendablerecyclerview.databinding.ActivityMainBinding;
import com.lisovyi.dima.expendablerecyclerview.util.AndroidUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<GroupItem> items = new ArrayList<>();

    {
        items.add(new GroupItem("1", "group1", new Item("2", "item 1"), new Item("3", "item 2"), new Item("4", "item 3")));
        items.add(new GroupItem("5", "group2"));
        items.add(new GroupItem("6", "group3", new Item("7", "item 1"), new Item("8", "item 2"), new Item("9", "item 3"), new Item("10", "item 4"), new Item("11", "item 5"), new Item("12", "item 6")));
        items.add(new GroupItem("13", "group4", new Item("14", "item 1"), new Item("15", "item 2"), new Item("16", "item 3"), new Item("17", "item 4"), new Item("18", "item 5"), new Item("19", "item 6")));
    }

    private ActivityMainBinding binding;
    private ItemListAdapter adapter;
    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViews();

        parseParameters(savedInstanceState);

        if (adapter.getItemCount() == 0) {
            requestItems();
        }
    }

    private void initViews() {
        binding.rvRoot.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemListAdapter();
        binding.rvRoot.setAdapter(adapter);
    }

    private void requestItems() {
        if (!AndroidUtil.internetConnected(this)) {
            showMessage(R.string.no_internet_connection);
            return;
        }
        getDataProvider().getItems(new ResponseListener<List<GroupItem>>() {
            @Override
            public void onSuccess(List<GroupItem> body) {
                adapter.setGroups(body);
            }

            @Override
            public void onFailure(int errorCode) {
                showMessage(R.string.something_went_wrong);

                MainActivity context = MainActivity.this;
                AndroidUtil.createDialog(context, context.getString(R.string.show_mock, errorCode),
                        new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adapter.setGroups(items);
                            }
                        }).show();
            }
        });
    }

    private void showMessage(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_LONG).show();
    }

    public DataProvider getDataProvider() {
        if (dataProvider == null) {
            dataProvider = new DataProvider();
        }
        return dataProvider;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (adapter.getItemCount() == 0) {
            parseParameters(savedInstanceState);
        }
    }

    private void parseParameters(Bundle params) {
        if (params != null && !params.isEmpty()) {
            adapter.restoreInstanceState(params);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.saveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
