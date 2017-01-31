package org.fiole.mapofthings.enums;

import org.fiole.mapofthings.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fiona on 20.03.2016.
 */
public enum MarkerType {
    DEFAULT(0, R.drawable.ic_marker_default, R.string.marker_default),
    ANIMALS(1, R.drawable.ic_marker_animals, R.string.marker_animals),
    PHOTOGRAPHY(2, R.drawable.ic_marker_photography, R.string.marker_photography),
    EVSTATION(3, R.drawable.ic_marker_evstation, R.string.marker_evstation),
    GATHERING(4, R.drawable.ic_marker_gathering, R.string.marker_gathering),
    ROMANTIC(5, R.drawable.ic_marker_romantic, R.string.marker_romantic),
    MAIL(6, R.drawable.ic_marker_mail, R.string.marker_mail),
    DOGS(7, R.drawable.ic_marker_dogs, R.string.marker_dogs),
    CIGARETTES(8, R.drawable.ic_marker_cigarette, R.string.marker_cigarettes),
    CAMPFIRE(9, R.drawable.ic_marker_campfire, R.string.marker_campfire),
    WIFI(10, R.drawable.ic_marker_wifi, R.string.marker_wifi),
    FISHING(11, R.drawable.ic_marker_fishing, R.string.marker_fishing),
    RECYCLINGBANK(12, R.drawable.ic_marker_recyclingbank, R.string.marker_recyclingbank),
    SCRAPGLASS(13, R.drawable.ic_marker_scrapglass, R.string.marker_scrapglass),
    CHEWINGGUM(14, R.drawable.ic_marker_chewinggum, R.string.marker_chewinggum),
    CANDY(15, R.drawable.ic_marker_candy, R.string.marker_candy),
    DRINKS(16, R.drawable.ic_marker_drinks, R.string.marker_drinks),
    TOILET(17, R.drawable.ic_marker_toilet, R.string.marker_toilet),
    FROG(18, R.drawable.ic_marker_frog, R.string.marker_frog)
    ;


    private Integer markerId;
    private Integer pictureId;
    private Integer nameId;

    MarkerType(Integer markerId, Integer pictureId, Integer nameId){
        this.markerId = markerId;
        this.pictureId = pictureId;
        this.nameId = nameId;
    }

    public static List<MarkerType> getAllMarkerTypes(){
        List<MarkerType> list = new ArrayList<MarkerType>();
        for(MarkerType marker : values()){
            if(marker.getMarkerId().intValue() > 0){
                list.add(marker);
            }
        }

        return list;
    }

    public static MarkerType getMarkerFromMarkerId(Integer markerId){
        for(MarkerType marker : values()){
            if(markerId.intValue() == marker.getMarkerId().intValue()){
                return marker;
            }
        }

        return DEFAULT;
    }

    public static Integer getPictureIdFromMarkerId(Integer markerId){
        for(MarkerType marker : values()){
            if(markerId.intValue() == marker.getMarkerId().intValue()){
                return marker.getPictureId();
            }
        }

        return DEFAULT.getPictureId();
    }

    public static Integer getNameIdFromMarkerId(Integer markerId){
        for(MarkerType marker : values()){
            if(markerId.intValue() == marker.getMarkerId().intValue()){
                return marker.getNameId();
            }
        }

        return DEFAULT.getNameId();
    }

    public Integer getMarkerId() {
        return markerId;
    }

    public void setMarkerId(Integer markerId) {
        this.markerId = markerId;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }
}
