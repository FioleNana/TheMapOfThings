package org.fiole.mapofthings.models;

import org.fiole.mapofthings.enums.MarkerType;

/**
 * Created by fiona on 20.03.2016.
 */
public class MarkerInfos {

    private int id;
    private String creater;
    private String createdOn;
    private MarkerType markerType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public MarkerType getMarkerType() {
        return markerType;
    }

    public void setMarkerType(MarkerType markerType) {
        this.markerType = markerType;
    }

}
