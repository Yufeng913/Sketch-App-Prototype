package com.example.rob.tutorial6;

/*
Screenshot java class

Source: https://www.youtube.com/watch?v=frfsNSzuhvc
 */

import android.graphics.Bitmap;
import android.view.View;

public class Screenshot {

    public static Bitmap takeScreenShot(View v) {

        // Sets the drawing cache and bitmap to enable screenshot creation
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    // Takes screenshot
    public static Bitmap takeScreenShotOfRootView(View v) {
        return takeScreenShot(v.getRootView());
    }
}
