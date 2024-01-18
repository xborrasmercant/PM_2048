package com.example.a2048;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int displayWidth, displayHeight;

    ScoreBox currentScore, bestScore;
    TextView gameLogo;
    GridLayout gameGrid;
    ConstraintLayout mainConstraintLayout;
    ConstraintLayout.LayoutParams mainCLayoutParams;
    Context gameBlockStyledContext = new ContextThemeWrapper(this, R.style.GameBlockStyle); // Context with custom style is created



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveDisplaySize();

        // Responsive spacing between blocks
        int spacing = (int) (displayWidth*0.012f);
        int baseSize = (int) (displayWidth*0.05);
        float scaleFactor = 0.83f;
        int valDigits = String.valueOf(2048).length();
        int textSize = (int) (baseSize * Math.pow(scaleFactor, valDigits - 1));

        // Initialization of views
        mainConstraintLayout = findViewById(R.id.mainConstraintLayout);


        gameLogo = new TextView(getBaseContext());

        gameLogo.setTextSize(textSize*1.8f);
        gameLogo.setText("2048");
        gameLogo.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.light_beige));
        gameLogo.setGravity(Gravity.CENTER);
        gameLogo.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getBaseContext(), R.color.shadowColor));
        gameLogo.setTypeface(ResourcesCompat.getFont(getBaseContext(), R.font.baloo_bhai_2_bold));
        gameLogo.setWidth((int) (displayWidth/3*1.15));
        gameLogo.setHeight((int) (displayWidth/3*1.15));
        gameLogo.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.tier4_2)));
        gameLogo.setId(View.generateViewId());
        mainConstraintLayout.addView(gameLogo);

        currentScore = new ScoreBox(getBaseContext(), 2560, "Score");
        currentScore.setId(View.generateViewId());
        mainConstraintLayout.addView(currentScore);

        bestScore = new ScoreBox(getBaseContext(), 1111111111, "Best");
        bestScore.setId(View.generateViewId());
        mainConstraintLayout.addView(bestScore);

        gameGrid = new GameGrid(getBaseContext(), 4, 4);
        gameGrid.setId(View.generateViewId());
        gameGrid.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.brown)));
        mainConstraintLayout.addView(gameGrid);

        int val = 2;

        for (int x = 0; x < 4; x++) {           // Column (x)
            for (int y = 0; y < 4; y++) {       // Row (y)
                GameBlock block = new GameBlock(gameBlockStyledContext, x, y, val); // Creating the gameblock with custom style
                valDigits = String.valueOf(val).length();
                textSize = (int) (baseSize * Math.pow(scaleFactor, valDigits - 1));

                block.setTextSize(textSize);
                block.setWidth((int) (displayWidth/4*0.75));
                block.setHeight((int) (displayWidth/4*0.75));

                val *= 2;

                // Setting GridLayout properties
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(x, 1f);
                params.columnSpec = GridLayout.spec(y, 1f);
                params.setMargins(spacing,spacing,spacing,spacing);
                gameGrid.setPadding(spacing, spacing, spacing, spacing);

                gameGrid.addView(block, params);
            }
        }
        refreshGrid(gameGrid);

        configureConstraints();
    }

    private void configureConstraints() {
        // ConstraintSet Configuration
        ConstraintSet cs = new ConstraintSet();
        int spacing = 32;
        int currentScoreID = currentScore.getId();
        int bestScoreID = bestScore.getId();
        int gameGridID = gameGrid.getId();
        int parentID = ConstraintSet.PARENT_ID;
        int gameLogoID = gameLogo.getId();

        cs.clone(mainConstraintLayout); // Clone the constraints of mainConstraintLayout into ConstraintSet

        cs.setVerticalBias(gameLogoID, 0.5f);
        cs.setVerticalBias(bestScoreID, 0.9f);


        cs.connect(gameLogoID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);
        cs.connect(gameLogoID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(gameLogoID, ConstraintSet.BOTTOM, gameGridID, ConstraintSet.TOP, spacing);

        // CurrentScore
        cs.connect(currentScoreID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(currentScoreID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);
        cs.connect(currentScoreID, ConstraintSet.START, gameLogoID, ConstraintSet.END, spacing);

        // BestScore
        cs.connect(bestScoreID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(bestScoreID, ConstraintSet.START, gameLogoID, ConstraintSet.END, spacing);
        cs.connect(bestScoreID, ConstraintSet.BOTTOM, gameGridID, ConstraintSet.TOP, spacing);

        // CurrentScore <---> BestScore
        cs.connect(currentScoreID, ConstraintSet.BOTTOM, bestScoreID, ConstraintSet.TOP, spacing/2);
        cs.connect(bestScoreID, ConstraintSet.TOP, currentScoreID, ConstraintSet.BOTTOM, spacing/2);

        cs.connect(gameGridID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(gameGridID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(gameGridID, ConstraintSet.BOTTOM, parentID, ConstraintSet.BOTTOM, spacing*8);

        cs.applyTo(mainConstraintLayout);
    }

    public void setScoreLayoutParams() {
        int spacing = 10;

        this.mainCLayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);}

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

    public void saveDisplaySize(){
        // Getting display width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;
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