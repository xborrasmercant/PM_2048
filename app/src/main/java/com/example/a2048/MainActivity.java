package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.color.utilities.Score;

public class MainActivity extends AppCompatActivity {
    int displayWidth, displayHeight;
    Context gameBlockStyledContext = new ContextThemeWrapper(this, R.style.GameBlockStyle); // Context with custom style is created
    Context scoreBoxStyledContext = new ContextThemeWrapper(this, R.style.ScoreBoxStyle); // Context with custom style is created


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting display width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;

        ConstraintLayout mainConstraintLayout = findViewById(R.id.mainConstraintLayout);


        ScoreBox currentScore = new ScoreBox(getBaseContext(), 2560, "Score");
        //currentScore.getScoreLabel().setTextSize(40f);
        //currentScore.getScoreTextView().setTextSize(40f);


        mainConstraintLayout.addView(currentScore.getScoreBoxLayout());







        GridLayout gameGrid = findViewById(R.id.gameGrid);
        gameGrid.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.brown)));

        // Responsive spacing between blocks
        int spacing = (int) (displayWidth*0.012f);
        int baseSize = (int) (displayWidth*0.05);
        float scaleFactor = 0.83f;

        int val = 2;

        for (int x = 0; x < 4; x++) {           // Column (x)
            for (int y = 0; y < 4; y++) {       // Row (y)
                GameBlock block = new GameBlock(gameBlockStyledContext, x, y, val); // Creating the gameblock with custom style
                int valDigits = String.valueOf(val).length();

                int textSize = (int) (baseSize * Math.pow(scaleFactor, valDigits - 1));

                block.setTextSize(textSize);

                val *= 2;

                // Setting GridLayout properties
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(x, 1f);
                params.columnSpec = GridLayout.spec(y, 1f);
                params.setMargins(spacing,spacing,spacing,spacing);
                gameGrid.setPadding(spacing, spacing, spacing, spacing);
                gameGrid.addView(block, params);
            }
        }
        refreshGrid(gameGrid);

    }

    public void refreshGrid(GridLayout gameGrid) {
        gameGrid.invalidate();
        gameGrid.requestLayout();
    }

    private GradientDrawable getDrawableBackground(int BGColor) {
        GradientDrawable newBackground = new GradientDrawable();
        newBackground.setShape(GradientDrawable.RECTANGLE);
        newBackground.setColor(BGColor);
        newBackground.setCornerRadius(15);

        return newBackground;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }
}