package com.raenarapps.simplecollage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raenarapps.simplecollage.pojo.Images;
import com.raenarapps.simplecollage.pojo.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    List<Item> itemList;
    Context context;

    public ImageListAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        Images images = item.getImages();
        if (images != null) {
            String url = images.getStandardResolution().getUrl();
            Picasso.with(context).load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.image);
        }
        holder.title.setText(item.getCaption().getText());
        holder.likes.setText(item.getLikes().getCount().toString());
        Long timeInMillis = Long.valueOf(item.getCaption().getCreatedTime())*1000;
        holder.date.setText(Utility.getFormattedDate(timeInMillis));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView likes;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.listitem_image);
            title = (TextView) itemView.findViewById(R.id.listitem_title);
            likes = (TextView) itemView.findViewById(R.id.listitem_likes);
            date = (TextView) itemView.findViewById(R.id.listitem_date);
        }
    }
}
