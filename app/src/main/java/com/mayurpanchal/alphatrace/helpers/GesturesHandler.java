package com.mayurpanchal.alphatrace.helpers;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;

import com.mayurpanchal.alphatrace.R;

/**
 * Created by rsousa on 25/07/15.
 */
public final class GesturesHandler {
    public static GestureLibrary loadGesturesLibrary (Context context) {
        GestureLibrary gestureLibrary = GestureLibraries.fromRawResource(context, R.raw.gestures);
        gestureLibrary.load();
        return gestureLibrary;
    }
}
