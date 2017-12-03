package kr.jnu.embedded.snssearcher.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;
import kr.jnu.embedded.snssearcher.ui.activities.adapter.viewAdapter;

/**
 * Created by Shane on 2017. 12. 1..
 */

public class FaceBookFragment extends Fragment {
    List<Item> items = App.facebookItem;
    protected viewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new viewAdapter(items);

        List<Item> item = new ArrayList<>();
        //TEST
        item.add(new Item("name1","","10월 23일","텍스트 테스트 123", ""));
        item.add(new Item("name2","","10월 23일","텍스트 테스트 123456", ""));
        item.add(new Item("name3","","10월 23일","텍스트 테스트 1233", ""));
        item.add(new Item("name4","","10월 23일","텍스트3", ""));
        item.add(new Item("name5","","10월 23일","텍스트트 123", ""));

        items.clear();
        items.addAll(item);

        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_facebook, container, false);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);

        return rv;
    }


}
