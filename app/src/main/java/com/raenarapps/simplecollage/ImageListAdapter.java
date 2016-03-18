package com.raenarapps.simplecollage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.raenarapps.simplecollage.pojo.Images;
import com.raenarapps.simplecollage.pojo.Item;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    List<Item> itemList;
    Context context;
    HashMap<Integer,String> selectedImagesMap;

    public ImageListAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        findSelectedImages(itemList);
    }

    private void findSelectedImages(List<Item> itemList) {
        selectedImagesMap = new HashMap<>();
        if (itemList != null){
            for (int i = 0; i < itemList.size(); i++) {
                Images images = itemList.get(i).getImages();
                if (images != null) {
                    if (images.isSelected())
                        selectedImagesMap.put(i,images.getStandardResolution().getUrl());
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Item item = itemList.get(position);
        final Images images = item.getImages();
        if (images != null) {
            final String url = images.getStandardResolution().getUrl();
            Picasso.with(context).load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.image);

            holder.frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!images.isSelected()) {
                        images.setIsSelected(true);
                        holder.updateCheckbox(true);
                        selectedImagesMap.put(position, url);
                    } else {
                        images.setIsSelected(false);
                        holder.updateCheckbox(false);
                        selectedImagesMap.remove(position);
                    }
                }
            });
            holder.updateCheckbox(images.isSelected());
        }
        holder.title.setText(item.getCaption().getText());
        holder.likes.setText(item.getLikes().getCount().toString());
        Long timeInMillis = Long.valueOf(item.getCaption().getCreatedTime()) * 1000;
        holder.date.setText(Utility.getFormattedDate(timeInMillis));
    }

    public HashMap<Integer,String> getSelectedImagesMap(){
        return selectedImagesMap;
    }

    @Override
    public int getItemCount() {
        if (itemList != null) return itemList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private ImageView checkbox;
        private FrameLayout frame;
        private TextView title;
        private TextView likes;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.listitem_image);
            title = (TextView) itemView.findViewById(R.id.listitem_title);
            likes = (TextView) itemView.findViewById(R.id.listitem_likes);
            date = (TextView) itemView.findViewById(R.id.listitem_date);
            checkbox = (ImageView) itemView.findViewById(R.id.listitem_checkbox);
            frame = (FrameLayout) itemView.findViewById(R.id.listitem_frame);
        }

        public void updateCheckbox(boolean isSelected) {
            if (isSelected) {
                checkbox.setImageResource(R.drawable.check_1_icon);
            } else {
                checkbox.setImageResource(R.drawable.check_0_icon);
            }
        }
    }
}
