package org.fiole.mapofthings.adapters;

import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import org.fiole.mapofthings.MapsActivity;
import org.fiole.mapofthings.R;
import org.fiole.mapofthings.enums.MarkerType;

/**
 * Created by fiona on 10.02.2017.
 */
public class MotInfoWindowAdapter implements InfoWindowAdapter{
    private MapsActivity mapsActivity;

    public MotInfoWindowAdapter(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoView = mapsActivity.getLayoutInflater().inflate(R.layout.mot_info_window, null);
        TextView creater = (TextView) infoView.findViewById(R.id.creater);
        TextView createdOn = (TextView) infoView.findViewById(R.id.createdOn);
        TextView markerType = (TextView) infoView.findViewById(R.id.markerType);

        creater.setText("Test Creater");
        createdOn.setText("Test Created on");
        markerType.setText(String.valueOf(MarkerType.ANIMALS));

        return infoView;
    }
}
