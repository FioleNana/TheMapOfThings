package org.fiole.mapofthings.models;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.zzf;

import java.util.HashMap;

/**
 * Created by fiona on 20.03.2016.
 */
public class MotMarkerManager {

    private HashMap<String, Integer> markerMap;

    public void putMarkerAndId(Marker marker, Integer id){
        markerMap.put(marker.getId(), id);
    }

    public Integer getIdOfMarker(Marker marker){
        return markerMap.get(marker.getId());
    }

    public MarkerInfos getInfosOfMarker(Marker marker){
        Integer id = getIdOfMarker(marker);
        MarkerInfos infos = new MarkerInfos(id);
        return infos;
    }
}
