package com.mayurpanchal.alphatrace.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mayurpanchal.alphatrace.R;

/**
 * Created by rsousa on 20/08/15.
 */
public class LetterView extends RelativeLayout {

    public LetterView (Context context, int resource) {
        super(context);
        ImageView letter = new ImageView(context);
        letter.setImageResource(resource);
        letter.setAdjustViewBounds(true);
        letter.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams letterParams = (RelativeLayout.LayoutParams) letter.getLayoutParams();
        letterParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        letterParams.addRule(RelativeLayout.CENTER_VERTICAL);
        letter.setId(R.id.letters_id);
        letter.setLayoutParams(letterParams);

        View line1 = new View(context);
        View line2 = new View(context);
        View line3 = new View(context);
        line1.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                1));
        line2.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                1));
        line3.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                1));

        line1.setBackgroundColor(getResources().getColor(R.color.orange_color));
        line2.setBackgroundColor(getResources().getColor(R.color.orange_color));
        line3.setBackgroundColor(getResources().getColor(R.color.orange_color));

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)line1.getLayoutParams();
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)line2.getLayoutParams();
        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) line3.getLayoutParams();

        params1.addRule(RelativeLayout.ALIGN_PARENT_TOP, letter.getId());
        params2.addRule(RelativeLayout.CENTER_IN_PARENT, letter.getId());
        params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, letter.getId());

        line1.setLayoutParams(params1);
        line2.setLayoutParams(params2);
        line3.setLayoutParams(params3);

        this.addView(line1);
        this.addView(line2);
        this.addView(line3);
        this.addView(letter);
    }

}
