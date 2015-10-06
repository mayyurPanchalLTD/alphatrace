package com.mayurpanchal.alphatrace.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mayurpanchal.alphatrace.R;
import com.mayurpanchal.alphatrace.fragments.SplashFragment;
import com.mayurpanchal.alphatrace.helpers.GesturesHandler;
import com.mayurpanchal.alphatrace.utils.CircleView;
import com.mayurpanchal.alphatrace.utils.ImagesHandler;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    boolean mSplashDidAppear = false;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init splash:
        SplashFragment sf = new SplashFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, sf, "splash");
        transaction.addToBackStack("splash");
        transaction.commit();

        mp = MediaPlayer.create(this, R.raw.bensoundlittleidea);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    public boolean getSplashDidAppear () {
        return mSplashDidAppear;
    }
    public void setSplashDidAppear (boolean splashDidAppear) {
        mSplashDidAppear = splashDidAppear;
    }

    public void turnMainSoundVolumeDown () {
        mp.setVolume(0.3f, 0.3f);
    }

    public void turnMainSoundVolumeUp () {
        mp.setVolume(1.0f, 1.0f);
    }
}
