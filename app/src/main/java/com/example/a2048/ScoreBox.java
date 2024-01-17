package com.example.a2048;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class ScoreBox extends ConstraintLayout {
   private int scoreValue;
   private TextView scoreLabel, scoreTextView;
   private ConstraintLayout scoreBoxLayout;
   private ConstraintLayout.LayoutParams scoreLayoutParams;

    public ScoreBox(@NonNull Context context, int scoreValue, String labelString) {
        super(context);
        this.scoreValue = scoreValue;
        this.scoreTextView = new TextView(context);
        scoreTextView.setId(View.generateViewId());

        this.scoreLabel = new TextView(context);
        scoreLabel.setId(View.generateViewId());

        // Set the text for score and label
        this.scoreTextView.setText(String.valueOf(scoreValue));
        this.scoreLabel.setText(labelString);

        // Initialize ConstraintLayout and add the TextViews to it
        this.scoreBoxLayout = new ConstraintLayout(context);
        setScoreLayoutParams();

        this.scoreBoxLayout.addView(scoreLabel, scoreLayoutParams);
        this.scoreBoxLayout.addView(scoreTextView, scoreLayoutParams);

        this.scoreBoxLayout.setBackground(getDrawableBackground(ContextCompat.getColor(this.getContext(), R.color.dark_brown)));


        scoreLabel.setTextColor(ContextCompat.getColor(this.getContext(), R.color.beige));
        scoreLabel.setGravity(Gravity.CENTER);
        scoreLabel.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        scoreLabel.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getContext(), R.color.shadowColor));
        scoreLabel.setTypeface(ResourcesCompat.getFont(context, R.font.baloo_bhai_2_bold));


        scoreTextView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.light_beige));
        scoreTextView.setGravity(Gravity.CENTER);
        scoreTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        scoreTextView.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getContext(), R.color.shadowColor));
        scoreTextView.setTypeface(ResourcesCompat.getFont(context, R.font.baloo_bhai_2_bold));




        configureConstraints();
    }

    private void configureConstraints() {
        // ConstraintSet Configuration
        ConstraintSet cs = new ConstraintSet();
        int spacing = 32;

        cs.clone(scoreBoxLayout); // Clone the constraints of scoreBoxLayout into ConstraintSet

        cs.connect(scoreTextView.getId(), ConstraintSet.TOP, scoreLabel.getId(), ConstraintSet.BOTTOM, 0);

        cs.connect(scoreLabel.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, spacing);
        cs.connect(scoreLabel.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, spacing);
        cs.connect(scoreLabel.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, spacing);

        cs.connect(scoreTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, spacing);
        cs.connect(scoreTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, spacing);
        cs.connect(scoreTextView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, spacing);

        cs.applyTo(scoreBoxLayout);

    }


    private GradientDrawable getDrawableBackground(int BGColor) {
        GradientDrawable newBackground = new GradientDrawable();
        newBackground.setShape(GradientDrawable.RECTANGLE);
        newBackground.setColor(BGColor);
        newBackground.setCornerRadius(15);

        return newBackground;
    }

    public ConstraintLayout getScoreBoxLayout() {
        return scoreBoxLayout;
    }

    public void setScoreBoxLayout(ConstraintLayout scoreBoxLayout) {
        this.scoreBoxLayout = scoreBoxLayout;
    }

    public LayoutParams getScoreLayoutParams() {
        return scoreLayoutParams;
    }

    public void setScoreLayoutParams() {
        int spacing = 10;

        this.scoreLayoutParams = new LayoutParams(300, 300);

        scoreLayoutParams.dimensionRatio = "1:1";
        scoreLabel.setTextSize(40f);
        scoreTextView.setTextSize(40f);
    }


    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public TextView getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(TextView scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
    }
}
