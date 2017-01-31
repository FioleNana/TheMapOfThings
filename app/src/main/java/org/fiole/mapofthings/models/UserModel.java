package org.fiole.mapofthings.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;

import java.io.Serializable;

/**
 * Created by fiona on 07.12.2015.
 */
public class UserModel implements Serializable
{
    private String id;
    private String displayName;
    private String email;
    private String imageUri;
    private Drawable profilePic;

    public UserModel(){
        this("none", "none", "none", "", null);
    }

    public UserModel(String id, String displayName, String email, String imageUri, Drawable profilePic){
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.imageUri = imageUri;
        this.profilePic = profilePic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() { return imageUri; }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public Drawable getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Drawable profilePic) {
        this.profilePic = profilePic;
    }
}
