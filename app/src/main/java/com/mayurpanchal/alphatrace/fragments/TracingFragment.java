package com.mayurpanchal.alphatrace.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayurpanchal.alphatrace.R;
import com.mayurpanchal.alphatrace.activities.MainActivity;
import com.mayurpanchal.alphatrace.helpers.GesturesHandler;
import com.mayurpanchal.alphatrace.utils.ImagesHandler;
import com.mayurpanchal.alphatrace.utils.LetterView;

import org.joda.time.DateTime;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by rsousa on 26/07/15.
 */
public class TracingFragment extends Fragment implements GestureOverlayView.OnGestureListener, GestureOverlayView.OnGesturePerformedListener {
    Typeface mFont;
    RelativeLayout mParentView, mBottomToast;
    ImageButton mHomeButton;
    TextView mTopDescription, mCurrentLetter, mTopTimer, mYourBestScoreDesc,
            mYourBestScore, mYourWorstScoreDesc, mYourWorstScore, mTopTimerSecs, mToastText;
    Bitmap mBitmap;
    GestureOverlayView mGOverlay;
    GestureLibrary gLibrary;
    boolean mMissedPoint;
    int mShowingImage = 0;
    Thread mScoreClock;
    SharedPreferences mSharedPreferences;
    private Handler mHandler;
    private Runnable mUpdateRunnable;
    DateTime mClock = new DateTime(0);
    static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.tracing_layout, null);
        mParentView = (RelativeLayout)fragmentView.findViewById(R.id.parentView);
        mHomeButton = (ImageButton)fragmentView.findViewById(R.id.home_button);
        mTopDescription = (TextView) fragmentView.findViewById(R.id.top_description);
        mCurrentLetter = (TextView) fragmentView.findViewById(R.id.current_letter);
        mTopTimer = (TextView) fragmentView.findViewById(R.id.top_timer);
        mYourBestScoreDesc = (TextView) fragmentView.findViewById(R.id.your_best_score_desc);
        mYourBestScore = (TextView) fragmentView.findViewById(R.id.your_best_score);
        mYourWorstScoreDesc = (TextView) fragmentView.findViewById(R.id.your_worst_score_desc);
        mYourWorstScore = (TextView) fragmentView.findViewById(R.id.your_worst_score);
        mTopTimerSecs = (TextView) fragmentView.findViewById(R.id.top_timer_secs);

        mGOverlay = (GestureOverlayView) fragmentView.findViewById(R.id.blankView);
        addLetter(R.drawable.a, R.drawable.arrow_a);

        gLibrary = GesturesHandler.loadGesturesLibrary(getActivity());

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        assignListeners();
        resetValues();
        setTypefaces();

        initBottomToast();

        // TODO: Play letter sound before
//        ((MainActivity)getActivity()).turnMainSoundVolumeDown();
//        MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.a);
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                ((MainActivity)getActivity()).turnMainSoundVolumeUp();
//            }
//        });
//        mp.start();
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getFragmentManager();
                manager.popBackStack();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).turnMainSoundVolumeUp();
    }

    // OnGestureListener methods:
    @Override
    public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if ((int) motionEvent.getX() >= 0 && (int) motionEvent.getY() >= 0 &&
                    (int) motionEvent.getX() < mGOverlay.getWidth() &&
                    (int) motionEvent.getY() < mGOverlay.getHeight()) {
                int pixel = mBitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());
                int argb = Color.argb(Color.alpha(pixel), Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                if (argb == -1) {
                    mMissedPoint = true;
                } else {
                    // if didn't miss
                }
            }
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // Any action...
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            // Any action...
        }
    }

    @Override
    public void onGestureStarted(android.gesture.GestureOverlayView gestureOverlayView, android.view.MotionEvent motionEvent) {
        if (mClock.getMillis() == 0)
            startClock();
    }

    @Override
    public void onGestureEnded(android.gesture.GestureOverlayView gestureOverlayView, android.view.MotionEvent motionEvent) {
        // when gesture ended
    }

    @Override
    public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
    }

    // OnGesturePerformedListener method:
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String action = predictions.get(0).name;
            if (action.equals(getResources().getStringArray(R.array.letters)[mShowingImage])) {
                if (mMissedPoint) {
                    mToastText.setText(String.format(getResources().getString(R.string.missed), action));
                    showNextLetter(false);
                } else {
                    mToastText.setText(String.format(getResources().getString(R.string.complete), action));
                    showNextLetter(true);
                }
                stopClock(true);
            } else {
                mMissedPoint = false;
                Toast.makeText(getActivity(),
                        String.format(getResources().getString(R.string.not_recognized), action),
                        Toast.LENGTH_SHORT).show();
                stopClock(false);
            }

        }
    }

    private void assignListeners() {
        mGOverlay.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                return false;
            }
        });
        mGOverlay.addOnGestureListener(this);
        mGOverlay.addOnGesturePerformedListener(this);
        mGOverlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        mGOverlay.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        mGOverlay.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    private void setTypefaces() {
        mFont = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.crayons_font));
        mTopDescription.setTypeface(mFont);
        mCurrentLetter.setTypeface(mFont);
        mTopTimer.setTypeface(mFont);
        mYourBestScoreDesc.setTypeface(mFont);
        mYourBestScore.setTypeface(mFont);
        mYourWorstScoreDesc.setTypeface(mFont);
        mYourWorstScore.setTypeface(mFont);
        mTopTimerSecs.setTypeface(mFont);
    }

    private void showNextLetter(boolean withBalloons) {
        if (mShowingImage < 25) {
            mShowingImage += 1;
            Resources res = getResources();
            // TODO: Play letter sound after
            bringTheBalloons(res.getIdentifier(res.getStringArray(R.array.letters)[mShowingImage - 1],
                "raw", getActivity().getPackageName()), withBalloons);

            // TODO: Play letter sound before
//            bringTheBalloons(res.getIdentifier(res.getStringArray(R.array.letters)[mShowingImage],
//                    "raw", getActivity().getPackageName()), withBalloons);

            mBottomToast.setVisibility(View.VISIBLE);
            mGOverlay.removeAllViews();
            addLetter(res.getIdentifier(res.getStringArray(R.array.letters)[mShowingImage],
                    "drawable", getActivity().getPackageName()),
                    res.getIdentifier(res.getStringArray(R.array.arrow_letters)[mShowingImage],
                            "drawable", getActivity().getPackageName()));
            resetValues();
        }
    }

    private void resetValues() {
        mMissedPoint = false;
        mTopDescription.setText(getResources().getString(R.string.current_letter) + " " +
                getResources().getStringArray(R.array.letters)[mShowingImage]);
        long best = mSharedPreferences.getLong("best" + getResources().getStringArray(R.array.letters)[mShowingImage], 0);
        long worst = mSharedPreferences.getLong("worst" + getResources().getStringArray(R.array.letters)[mShowingImage], 0);
        mYourBestScore.setText(sdf.format(new Date(mSharedPreferences.getLong("best" + getResources().getStringArray(R.array.letters)[mShowingImage], 0))) + "secs");
        mYourWorstScore.setText(sdf.format(new Date(mSharedPreferences.getLong("worst" + getResources().getStringArray(R.array.letters)[mShowingImage], 0))) + "secs");
        if (mBitmap != null)
            mBitmap.recycle();
        ViewTreeObserver viewTreeObserver = mGOverlay.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGOverlay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mBitmap = ImagesHandler.getBitmapFromView(mGOverlay);
            }
        });
    }

    private void setScores() {
        mHandler = new Handler();
        mUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                mTopTimer.setText(sdf.format(mClock.getMillis()));
            }
        };
    }

    private void startClock() {
        setScores();
        mScoreClock = new Thread() {
            @Override
            public void run() {
                try {
                    while (!this.isInterrupted()) {
                        sleep(1000);
                        mClock = mClock.plusSeconds(1);
                        mHandler.post(mUpdateRunnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mClock = new DateTime(0);
        mScoreClock.start();
    }

    private void stopClock(boolean saveScores) {
        mScoreClock.interrupt();
        if (saveScores) {
            long bestSoFar = mSharedPreferences.getLong("best" + getResources().getStringArray(R.array.letters)[mShowingImage], 0);
            long worstSoFar = mSharedPreferences.getLong("worst" + getResources().getStringArray(R.array.letters)[mShowingImage], 0);
            if (bestSoFar == 0) {
                mSharedPreferences.edit().putLong("best" + getResources().getStringArray(R.array.letters)[mShowingImage], mClock.getMillis()).apply();
            } else if (mClock.getMillis() < bestSoFar) {
                mSharedPreferences.edit().putLong("best" + getResources().getStringArray(R.array.letters)[mShowingImage], mClock.getMillis()).apply();
            }
            if (worstSoFar == 0) {
                mSharedPreferences.edit().putLong("worst" + getResources().getStringArray(R.array.letters)[mShowingImage], mClock.getMillis()).apply();
            } else if (mClock.getMillis() > worstSoFar) {
                mSharedPreferences.edit().putLong("worst" + getResources().getStringArray(R.array.letters)[mShowingImage], mClock.getMillis()).apply();
            }
        }

        mClock = new DateTime(0);
        mTopTimer.setText(sdf.format(mClock.getMillis()));
    }

    private void addLetter (int letterResource, int arrowResource) {
        LetterView letterView = new LetterView(getActivity(), letterResource);
        letterView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        letterView.setPadding(50, 10, 50, 10);
        mGOverlay.addView(letterView);

        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        imageView.setImageResource(arrowResource);
        mGOverlay.addView(imageView);
    }

    private void bringTheBalloons (final int letterSound, final boolean visibleBalloons) {
        final ImageView balloons = new ImageView(getActivity());
        balloons.setImageResource(R.drawable.all_balloons);
        balloons.setId(R.id.balloons_id);
        mParentView.addView(balloons);
        balloons.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        if (visibleBalloons) {
            balloons.setVisibility(View.VISIBLE);
        } else {
            balloons.setVisibility(View.GONE);
        }

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.balloons_translation);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visibleBalloons) {
                    ((MainActivity) getActivity()).turnMainSoundVolumeDown();
                    MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.screaming_applause);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // TODO: Play letter sound before
//                            MediaPlayer mp = MediaPlayer.create(getActivity(), letterSound);
//                            mp.start();
//                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                @Override
//                                public void onCompletion(MediaPlayer mediaPlayer) {
//                                    ((MainActivity) getActivity()).turnMainSoundVolumeUp();
//                                }
//                            });

                            // TODO: Play letter sound after
                            ((MainActivity) getActivity()).turnMainSoundVolumeUp();
                        }
                    });

                    // TODO: Play letter sound after
                    MediaPlayer mpLetter = MediaPlayer.create(getActivity(), letterSound);
                    mpLetter.start();
                } else {
                    // TODO: Play letter sound before
//                    MediaPlayer mp = MediaPlayer.create(getActivity(), letterSound);
//                    mp.start();
//                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mediaPlayer) {
//                            ((MainActivity) getActivity()).turnMainSoundVolumeUp();
//                            mBottomToast.setVisibility(View.GONE);
//                        }
//                    });
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomToast.setVisibility(View.GONE);
                mParentView.removeView(balloons);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        balloons.startAnimation(anim);
    }

    private void initBottomToast () {
        mBottomToast = new RelativeLayout(getActivity());
        mBottomToast.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.activity_extra_vertical_margin)));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mBottomToast.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mBottomToast.setBackgroundColor(getResources().getColor(R.color.pinkish_color));
        mToastText = new TextView(getActivity());
        mToastText.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        mToastText.setTextSize(30f);
        mToastText.setTextColor(getResources().getColor(android.R.color.white));
        mToastText.setTypeface(mFont);
        RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams)mToastText.getLayoutParams();
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mBottomToast.addView(mToastText, textParams);
        mParentView.addView(mBottomToast, params);

        mBottomToast.setVisibility(View.GONE);
    }
}