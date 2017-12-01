package kr.jnu.embedded.snssearcher.ui.activities.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by Shane on 2017. 12. 1..
 */

public class FaceBookViewAdapter extends RecyclerView
        .Adapter<FaceBookViewAdapter
        .DataObjectHolder>
{
    private List<Item> mDataset;


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView nametext;
        TextView datetext;
        TextView texttext;
        ImageView userImageview;
        ImageView textImageview;


        public DataObjectHolder(View itemView) {
            super(itemView);
            nametext = (TextView) itemView.findViewById(R.id.name_text);
            datetext = (TextView) itemView.findViewById(R.id.date_text);
            texttext = (TextView) itemView.findViewById(R.id.text_text);
//            imageview = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

    public FaceBookViewAdapter(List<Item> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.nametext.setText(mDataset.get(position).getName());
        holder.datetext.setText(mDataset.get(position).getDate());
        holder.texttext.setText(mDataset.get(position).getText());
//        ImageLoader.getInstance().displayImage(mDataset.get(position).getUserImage(), holder.imageview);

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
