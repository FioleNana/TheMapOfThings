package org.fiole.mapofthings.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.fiole.mapofthings.R;
import org.fiole.mapofthings.enums.MarkerType;

import java.util.List;

/**
 * Created by fiona on 20.03.2016.
 */
public class MarkerGridImageAdapter extends BaseAdapter {

    private Activity activity;
    private List<MarkerType> markers;

    public MarkerGridImageAdapter(Activity activity){
        this.activity = activity;
        this.markers = MarkerType.getAllMarkerTypes();
    }

    @Override
    public int getCount() {
        return markers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.gridview_choose_marker, null);
        }
        else {
            view = convertView;
        }

        MarkerType marker = markers.get(position);
        Drawable markerIcon = activity.getDrawable(marker.getPictureId());
        int markerColorRed = marker.getColor()[0];
        int markerColorGreen = marker.getColor()[1];
        int markerColorBlue = marker.getColor()[2];
        markerIcon.setTint(Color.rgb(markerColorRed, markerColorGreen, markerColorBlue));

        ImageView imageView = (ImageView) view.findViewById(R.id.choose_marker_grid_image);
        imageView.setImageDrawable(markerIcon);
        imageView.setMaxWidth((int) activity.getResources().getDimension(R.dimen.grid_size));

        TextView textView = (TextView) view.findViewById(R.id.choose_marker_grid_title);
        textView.setText(markers.get(position).getNameId());

        return view;
    }
}
