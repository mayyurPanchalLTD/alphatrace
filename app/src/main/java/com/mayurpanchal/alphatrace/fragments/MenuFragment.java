package com.mayurpanchal.alphatrace.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayurpanchal.alphatrace.R;

/**
 * Created by rsousa on 13/08/15.
 */
public class MenuFragment extends Fragment {
    Typeface mCrayonFont, mCookieMonster;
    TextView mTextMenu1, mTextSubMenu1, mTextMenu2, mTextSubMenu2, mTextMenu3, mTextSubMenu3, mTextMenu4,
            mTextSubMenu4, mTextMenu5, mTextSubMenu5, mTextMenu6, mTextSubMenu6, mTextMenu7,
            mTextMenu8;
    RelativeLayout mMenu1, mMenu2, mMenu3, mMenu4, mMenu5, mMenu6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_menu, null);
        mTextMenu1 = (TextView)fragmentView.findViewById(R.id.text_menu_1);
        mTextMenu2 = (TextView)fragmentView.findViewById(R.id.text_menu_2);
        mTextMenu3 = (TextView)fragmentView.findViewById(R.id.text_menu_3);
        mTextMenu4 = (TextView)fragmentView.findViewById(R.id.text_menu_4);
        mTextMenu5 = (TextView)fragmentView.findViewById(R.id.text_menu_5);
        mTextMenu6 = (TextView)fragmentView.findViewById(R.id.text_menu_6);
        mTextMenu7 = (TextView)fragmentView.findViewById(R.id.menu_7);
        mTextMenu8 = (TextView)fragmentView.findViewById(R.id.menu_8);

        mTextSubMenu1 = (TextView)fragmentView.findViewById(R.id.text_submenu_1);
        mTextSubMenu2 = (TextView)fragmentView.findViewById(R.id.text_submenu_2);
        mTextSubMenu3 = (TextView)fragmentView.findViewById(R.id.text_submenu_3);
        mTextSubMenu4 = (TextView)fragmentView.findViewById(R.id.text_submenu_4);
        mTextSubMenu5 = (TextView)fragmentView.findViewById(R.id.text_submenu_5);
        mTextSubMenu6 = (TextView)fragmentView.findViewById(R.id.text_submenu_6);

        mMenu1 = (RelativeLayout)fragmentView.findViewById(R.id.menu_button_1);
        mMenu2 = (RelativeLayout)fragmentView.findViewById(R.id.menu_button_2);
        mMenu3 = (RelativeLayout)fragmentView.findViewById(R.id.menu_button_3);
        mMenu4 = (RelativeLayout)fragmentView.findViewById(R.id.menu_button_4);
        mMenu5 = (RelativeLayout)fragmentView.findViewById(R.id.menu_button_5);
        mMenu6 = (RelativeLayout)fragmentView.findViewById(R.id.menu_button_6);

        setTypefaces ();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TracingFragment tf = new TracingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, tf, "tracing");
                transaction.addToBackStack("tracing");
                transaction.commit();
            }
        });
        mMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mMenu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mMenu1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMenu1.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenu1.setAlpha(1f);
                        break;
                }
                return false;
            }
        });
        mMenu2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMenu2.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenu2.setAlpha(1f);
                        break;
                }
                return false;
            }
        });
        mMenu3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMenu3.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenu3.setAlpha(1f);
                        break;
                }
                return false;
            }
        });
        mMenu4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMenu4.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenu4.setAlpha(1f);
                        break;
                }
                return false;
            }
        });
        mMenu5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMenu5.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenu5.setAlpha(1f);
                        break;
                }
                return false;
            }
        });
        mMenu6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMenu6.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenu6.setAlpha(1f);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

    public void setTypefaces () {
        mCrayonFont = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.crayons_font));
        mCookieMonster = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.selected_font));

        mTextMenu1.setTypeface(mCrayonFont);
        mTextMenu2.setTypeface(mCrayonFont);
        mTextMenu3.setTypeface(mCrayonFont);
        mTextMenu4.setTypeface(mCrayonFont);
        mTextMenu5.setTypeface(mCrayonFont);
        mTextMenu6.setTypeface(mCrayonFont);
        mTextSubMenu1.setTypeface(mCrayonFont);
        mTextSubMenu2.setTypeface(mCrayonFont);
        mTextSubMenu3.setTypeface(mCrayonFont);
        mTextSubMenu4.setTypeface(mCrayonFont);
        mTextSubMenu5.setTypeface(mCrayonFont);
        mTextSubMenu6.setTypeface(mCrayonFont);
        mTextMenu7.setTypeface(mCookieMonster);
        mTextMenu8.setTypeface(mCookieMonster);
    }
}








