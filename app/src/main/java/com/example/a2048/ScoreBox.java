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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class ScoreBox extends LinearLayout {
    private int scoreValue;
    private TextView labelView, valueView;
    private ConstraintLayout.LayoutParams scoreLayoutParams;

    public ScoreBox(@NonNull Context context, int scoreValue, String labelString) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.scoreValue = scoreValue;

        // Score TextView Config
        this.valueView = new TextView(context);
        this.valueView.setId(View.generateViewId());
        this.valueView.setText(String.valueOf(scoreValue));
        setBoxParams(valueView, 8, 16);

        // Score Label Config
        this.labelView = new TextView(context);
        this.labelView.setId(View.generateViewId());
        this.labelView.setText(labelString);
        setBoxParams(labelView, 16, 8);


        this.addView(labelView);
        this.addView(valueView);

        giveStyleToComponents();
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
        labelView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.beige));
        labelView.setGravity(Gravity.CENTER);
        labelView.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getContext(), R.color.shadowColor));
        labelView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.baloo_bhai_2_bold));
        labelView.setTextSize(25f);


        valueView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.light_beige));
        valueView.setGravity(Gravity.CENTER);
        valueView.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getContext(), R.color.shadowColor));
        valueView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.baloo_bhai_2_bold));
        valueView.setTextSize(25f);

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


    private void setBoxParams(View view, int topMargin, int botMargin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
        params.setMargins(0, topMargin, 0, botMargin);

        view.setLayoutParams(params);
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public TextView getLabelView() {
        return labelView;
    }

    public void setLabelView(TextView labelView) {
        this.labelView = labelView;
    }

    public TextView getValueView() {
        return valueView;
    }

    public void setValueView(TextView valueView) {
        this.valueView = valueView;
    }

    public ConstraintLayout.LayoutParams getScoreLayoutParams() {
        return scoreLayoutParams;
    }

    public void setScoreLayoutParams(ConstraintLayout.LayoutParams scoreLayoutParams) {
        this.scoreLayoutParams = scoreLayoutParams;
    }
}

