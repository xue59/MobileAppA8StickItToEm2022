package edu.neu.madscourse.a8stickittoem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdaptor extends BaseAdapter {

    Context context;
    int[] image;
    LayoutInflater inflater;
    private int selectedPosition = -1;

    public GridAdaptor(Context context, int[] image) {
        this.context = context;
        this.image = image;
    }

    public void setSelectedPosition(int position)
    {
        selectedPosition = position;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.emoji_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.grid_image);
        imageView.setImageResource(image[position]);

        // First check to see if the item has already been selected or not
        if(position == selectedPosition) {
            imageView.setBackgroundColor(Color.parseColor("#808080"));
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
}
