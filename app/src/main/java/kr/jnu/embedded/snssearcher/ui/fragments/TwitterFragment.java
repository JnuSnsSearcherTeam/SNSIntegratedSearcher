package kr.jnu.embedded.snssearcher.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;
import kr.jnu.embedded.snssearcher.ui.activities.adapter.viewAdapter;

/**
 * Created by shane on 2017. 12. 3..
 */

public class TwitterFragment extends Fragment implements SNSSearcherContract.View{
    List<Item> items = App.twitterItem;
    protected viewAdapter adapter;
    SNSSearcherContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new viewAdapter(items);

        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_twitter, container, false);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);

        return rv;
    }

    @Override
    public void setPresenter(SNSSearcherContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);
    }

    @Override
    public void updateItem() {
        adapter.notifyDataSetChanged();
    }
}
