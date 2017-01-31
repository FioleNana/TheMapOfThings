package org.fiole.mapofthings.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import org.fiole.mapofthings.R;

import java.io.IOException;
import java.net.URL;

/**
 * Created by fiona on 19.03.2016.
 */
public class ImageUtils {

    public Drawable getImageFromUriAndMakeRound(Activity activity, Uri imageUri){
        Resources res = activity.getResources();
        Bitmap userIm;

        try{
            if(imageUri != null && !"".equals(imageUri)){
                URL imageUrl = new URL(imageUri.toString());
                userIm = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } else {
                return activity.getDrawable(R.drawable.ic_account_circle);
            }
        } catch(IOException e) {
            return activity.getDrawable(R.drawable.ic_account_circle);
        }

        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, userIm);
        dr.setCornerRadius(Math.max(userIm.getWidth(), userIm.getHeight()) / 2.0f);
        return dr;
    }
}
