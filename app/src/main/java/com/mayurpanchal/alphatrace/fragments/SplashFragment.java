package com.mayurpanchal.alphatrace.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mayurpanchal.alphatrace.R;
import com.mayurpanchal.alphatrace.activities.MainActivity;

/**
 * Created by rsousa on 26/07/15.
 */
public class SplashFragment extends Fragment {
    Typeface mCrayonFont, mKlinicSlab;
    ImageView mSplashLogo;
    TextView mSplashPhrase, mVersionName, mLoading;
    ProgressBar mSplashProgress;
    private final Handler myHandler = new Handler();

    final Runnable updateRunnable = new Runnable() {
        public void run() {
            initTracingFragment();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.splash_layout, null);

        mSplashLogo = (ImageView) fragmentView.findViewById(R.id.splash_logo);
        mSplashPhrase = (TextView) fragmentView.findViewById(R.id.splash_phrase);
        mVersionName = (TextView) fragmentView.findViewById(R.id.version_name);
        mLoading = (TextView) fragmentView.findViewById(R.id.splash_loading);
        mSplashProgress = (ProgressBar) fragmentView.findViewById(R.id.splash_progress);

        if (((MainActivity)getActivity()).getSplashDidAppear()) {
            hideElements ();
            return fragmentView;
        }

        mCrayonFont = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.crayons_font));
        mKlinicSlab = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.klinic_slab_font));

        mSplashPhrase.setTypeface(mCrayonFont);
        mVersionName.setTypeface(mKlinicSlab);
        mLoading.setTypeface(mKlinicSlab);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                    myHandler.post(updateRunnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setSplashDidAppear(true);

        mSplashLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sp.edit().clear().apply();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

    private void hideElements () {
        mSplashLogo.setVisibility(View.GONE);
        mSplashPhrase.setVisibility(View.GONE);
        mVersionName.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
        mSplashProgress.setVisibility(View.GONE);
    }
    private void initTracingFragment () {
        MenuFragment menuFragment = new MenuFragment();
        if (getFragmentManager() != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, menuFragment, "menu");
            transaction.addToBackStack("menu");
            transaction.commit();
        }
    }
}
