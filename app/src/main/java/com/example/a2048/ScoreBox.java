package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ConstraintLayout.LayoutParams scoreLayoutParams;

    public ScoreBox(@NonNull Context context, int scoreValue, String labelString) {
        super(context);

        this.scoreValue = scoreValue;

        // Score TextView Config
        this.scoreTextView = new TextView(context);
        this.scoreTextView.setId(View.generateViewId());
        this.scoreTextView.setText(String.valueOf(scoreValue));

        // Score Label Config
        this.scoreLabel = new TextView(context);
        this.scoreLabel.setId(View.generateViewId());
        this.scoreLabel.setText(labelString);

        giveStyleToComponents();
        setScoreLayoutParams();

        this.addView(scoreLabel);
        this.addView(scoreTextView);

        configureConstraints();
    }

    private void configureConstraints() {
        // ConstraintSet Configuration
        ConstraintSet cs = new ConstraintSet();
        int spacing = 0;

        cs.clone(this); // Clone the constraints of scoreBoxLayout into ConstraintSet

        cs.connect(scoreTextView.getId(), ConstraintSet.TOP, scoreLabel.getId(), ConstraintSet.BOTTOM, 0);

        cs.connect(scoreLabel.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, spacing);
        cs.connect(scoreLabel.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, spacing);
        cs.connect(scoreLabel.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, spacing);

        cs.connect(scoreTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, spacing);
        cs.connect(scoreTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, spacing);
        cs.connect(scoreTextView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, spacing);

        cs.applyTo(this);


    }

    private GradientDrawable getDrawableBackground(int BGColor) {
        GradientDrawable newBackground = new GradientDrawable();
        newBackground.setShape(GradientDrawable.RECTANGLE);
        newBackground.setColor(BGColor);
        newBackground.setCornerRadius(15);

        return newBackground;
    }

    public void giveStyleToComponents() {
        this.setBackground(getDrawableBackground(ContextCompat.getColor(this.getContext(), R.color.dark_brown)));

        // Edit Style
        scoreLabel.setTextColor(ContextCompat.getColor(this.getContext(), R.color.beige));
        scoreLabel.setGravity(Gravity.CENTER);
        scoreLabel.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getContext(), R.color.shadowColor));
        scoreLabel.setTypeface(ResourcesCompat.getFont(getContext(), R.font.baloo_bhai_2_bold));
        scoreLabel.setTextSize(25f);


        scoreTextView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.light_beige));
        scoreTextView.setGravity(Gravity.CENTER);
        scoreTextView.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getContext(), R.color.shadowColor));
        scoreTextView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.baloo_bhai_2_bold));
        scoreTextView.setTextSize(25f);

    }

    @SuppressLint("ResourceAsColor")
    public void setScoreLayoutParams() {
        int spacing = 0;

        scoreLayoutParams = new ConstraintLayout.LayoutParams(
                0, // Width matches the parent
                ConstraintLayout.LayoutParams.WRAP_CONTENT  // Height wraps the content
        );

        this.setLayoutParams(scoreLayoutParams);
    }

    public LayoutParams getScoreLayoutParams() {
        return scoreLayoutParams;
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