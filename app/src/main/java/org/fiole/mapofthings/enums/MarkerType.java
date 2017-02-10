package org.fiole.mapofthings.enums;

import android.graphics.Color;
import org.fiole.mapofthings.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fiona on 20.03.2016.
 */
public enum MarkerType {
    DEFAULT(0, R.drawable.ic_marker_default, R.string.marker_default, new int[]{0, 0, 0}),
    ANIMALS(1, R.drawable.ic_marker_animals, R.string.marker_animals, new int[]{150, 40, 27}),
    PHOTOGRAPHY(2, R.drawable.ic_marker_photography, R.string.marker_photography, new int[]{75, 75, 75}),
    EVSTATION(3, R.drawable.ic_marker_evstation, R.string.marker_evstation, new int[]{78,205,196}),
    GATHERING(4, R.drawable.ic_marker_gathering, R.string.marker_gathering,new int[]{68,108,179}),
    ROMANTIC(5, R.drawable.ic_marker_romantic, R.string.marker_romantic, new int[]{207, 0, 15}),
    MAIL(6, R.drawable.ic_marker_mail, R.string.marker_mail, new int[]{247, 202, 24}),
    DOGS(7, R.drawable.ic_marker_dogs, R.string.marker_dogs, new int[]{108, 122, 137}),
    CIGARETTES(8, R.drawable.ic_marker_cigarette, R.string.marker_cigarettes, new int[]{171, 183, 183}),
    CAMPFIRE(9, R.drawable.ic_marker_campfire, R.string.marker_campfire, new int[]{217, 30, 24}),
    WIFI(10, R.drawable.ic_marker_wifi, R.string.marker_wifi,new int[]{38, 166, 91}),
    FISHING(11, R.drawable.ic_marker_fishing, R.string.marker_fishing, new int[]{34, 49, 63}),
    RECYCLINGBANK(12, R.drawable.ic_marker_recyclingbank, R.string.marker_recyclingbank, new int[]{30, 130, 76}),
    SCRAPGLASS(13, R.drawable.ic_marker_scrapglass, R.string.marker_scrapglass, new int[]{242, 120, 75}),
    CHEWINGGUM(14, R.drawable.ic_marker_chewinggum, R.string.marker_chewinggum, new int[]{246, 36, 89}),
    CANDY(15, R.drawable.ic_marker_candy, R.string.marker_candy, new int[]{245, 215, 110}),
    DRINKS(16, R.drawable.ic_marker_drinks, R.string.marker_drinks, new int[]{137, 196, 244}),
    TOILET(17, R.drawable.ic_marker_toilet, R.string.marker_toilet, new int[]{191, 191, 191}),
    FROG(18, R.drawable.ic_marker_frog, R.string.marker_frog, new int[]{77, 175, 124})
    ;


    private Integer markerId;
    private Integer pictureId;
    private Integer nameId;
    private int[] color;

    MarkerType(Integer markerId, Integer pictureId, Integer nameId, int[] color){
        this.markerId = markerId;
        this.pictureId = pictureId;
        this.nameId = nameId;
        this.color = color;
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

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
