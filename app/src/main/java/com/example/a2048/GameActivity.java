package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    int displayWidth, displayHeight;
    ScoreBox currentScore, bestScore;
    TextView gameLogo;
    Grid grid;
    FrameLayout playableArea;
    GestureDetector gestureDetector;
    ConstraintLayout gameLayout;
    ConstraintLayout.LayoutParams mainCLayoutParams;
    LinearLayout footer;
    Button undoBtn, resetBtn;
    Typeface mainTypeface;


    private static final int MIN_SWIPE_DISTANCE = 120; // Adjust this based on your needs

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Ensure that the GestureDetector gets the touch events
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameLayout = new ConstraintLayout(getBaseContext());
        setContentView(gameLayout);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                String direction = "NULL";

                float deltaX = e2.getX() - e1.getX();
                float deltaY = e2.getY() - e1.getY();
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // Horizontal swipe
                    if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE) {
                        if (deltaX > 0) {
                            direction = "RIGHT";

                        } else {
                            direction = "LEFT";

                        }
                    }
                } else {
                    // Vertical swipe
                    if (Math.abs(deltaY) > MIN_SWIPE_DISTANCE) {
                        if (deltaY > 0) {
                            direction = "DOWN";
                        } else {
                            direction = "UP";
                        }
                    }
                }

                Log.d("Gesture", "Fling detected: " + direction);
                updateGameGrid(direction);
                return true;
            }
        });


        // Responsive spacing between blocks
        saveDisplaySize();
        int spacing = (int) (displayWidth*0.012f);
        int baseSize = (int) (displayWidth*0.05);
        float scaleFactor = 0.83f;
        int valDigits = String.valueOf(2048).length();
        int textSize = (int) (baseSize * Math.pow(scaleFactor, valDigits - 1));
        int val = 2;

        addComponentsToLayout();
        configureConstraints();
    }


    // METHODS
    private void updateGameGrid(String direction) {
        grid.handleSweep(direction);
        grid.valueToRandomGameBlock();
        resizeGameBlockText();
        //redrawGridLayout();

    }

    public void resizeGameBlockText() {
        for (Block[] row : grid.getGameBlockMatrix()) {
            for (Block gb : row) {
                gb.getTextView().setTextSize(getResponsiveTextSize(gb.getValue()));
            }
        }
    }

    private void redrawGridLayout() {
        int spacing = (int) (displayWidth*0.012f);
        grid.removeAllViews();

        for (int x = 0; x < 4; x++) {           // Column (x)
            for (int y = 0; y < 4; y++) {       // Row (y)
                GridLayout.LayoutParams params = createGridLayoutParams(x, y, spacing);
                grid.addView(grid.getGameBlockMatrix()[x][y], params);
            }
        }
    }

    private void initGameGridLayout() {
        int spacing = (int) (displayWidth*0.012f);

        for (int x = 0; x < 4; x++) {           // Column (x)
            for (int y = 0; y < 4; y++) {       // Row (y)
                Block block = grid.getGameBlockMatrix()[x][y];

                block.getTextView().setTextSize(getResponsiveTextSize(block.getValue()));

                GridLayout.LayoutParams params = createGridLayoutParams(x, y, spacing);
                grid.addView(grid.getGameBlockMatrix()[x][y], params);
            }
        }
    }

    private void addComponentsToLayout() {
        int spacing = 24;
        mainTypeface = ResourcesCompat.getFont(this, R.font.baloo_bhai_2_bold);

        gameLogo = new TextView(getBaseContext());
        gameLogo.setTextSize(getResponsiveTextSize(2048)*1.8f);
        gameLogo.setText("2048");
        gameLogo.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.light_beige));
        gameLogo.setGravity(Gravity.CENTER);
        gameLogo.setShadowLayer(5, 6, 6, ContextCompat.getColor(this.getBaseContext(), R.color.shadowColor));
        gameLogo.setTypeface(ResourcesCompat.getFont(getBaseContext(), R.font.baloo_bhai_2_bold));
        gameLogo.setWidth((int) (displayWidth/3*1.15));
        gameLogo.setHeight((int) (displayWidth/3*1.15));
        gameLogo.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.tier4_2)));
        gameLogo.setId(View.generateViewId());
        gameLayout.addView(gameLogo);

        currentScore = new ScoreBox(getBaseContext(), 2560, "Score");
        currentScore.setId(View.generateViewId());
        gameLayout.addView(currentScore);

        bestScore = new ScoreBox(getBaseContext(), 1111111111, "Best");
        bestScore.setId(View.generateViewId());
        gameLayout.addView(bestScore);

        grid = new Grid(getBaseContext(), 4, 4);
        grid.setId(View.generateViewId());
        grid.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.brown)));
        gameLayout.addView(grid);
        initGameGridLayout();

        footer = new LinearLayout(getBaseContext());
        footer.setId(View.generateViewId());
        footer.setGravity(Gravity.CENTER);
        footer.setOrientation(LinearLayout.HORIZONTAL);
        gameLayout.addView(footer);
        extendViewWidth(footer);

        int footerTextSize = 32;

        undoBtn = new Button(getBaseContext());
        undoBtn.setId(View.generateViewId());
        undoBtn.setText("UNDO");
        undoBtn.setTypeface(mainTypeface);
        undoBtn.setTextSize(footerTextSize);
        undoBtn.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.light_beige));
        undoBtn.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.brown)));
        setFooterParams(undoBtn, 0, 16);
        footer.addView(undoBtn);

        resetBtn = new Button(getBaseContext());
        resetBtn.setId(View.generateViewId());
        resetBtn.setText("RESET");
        resetBtn.setTypeface(mainTypeface);
        resetBtn.setTextSize(footerTextSize);
        resetBtn.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.light_beige));
        resetBtn.setBackground(getDrawableBackground(ContextCompat.getColor(this.getBaseContext(), R.color.brown)));
        resetBtn.setOnClickListener(v -> grid.resetGrid());
        setFooterParams(resetBtn, 16, 0);
        footer.addView(resetBtn);
    }

    private void configureConstraints() {
        // ConstraintSet Configuration
        ConstraintSet cs = new ConstraintSet();
        int spacing = 32;
        int currentScoreID = currentScore.getId();
        int bestScoreID = bestScore.getId();
        int gameGridID = grid.getId();
        int parentID = ConstraintSet.PARENT_ID;
        int gameLogoID = gameLogo.getId();
        int resetBtnID = resetBtn.getId();
        int undoBtnID = undoBtn.getId();
        int footerID = footer.getId();

        cs.clone(gameLayout); // Clone the constraints of gameLayout into ConstraintSet

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
        cs.connect(gameGridID, ConstraintSet.BOTTOM, footerID, ConstraintSet.TOP, spacing);

        cs.connect(footerID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(footerID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(footerID, ConstraintSet.BOTTOM, gameGridID, ConstraintSet.TOP, spacing);
        cs.connect(footerID, ConstraintSet.BOTTOM, parentID, ConstraintSet.BOTTOM, spacing);

        cs.applyTo(gameLayout);
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

    private void extendViewWidth(View view) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
    }

    private void setFooterParams(View view, int lMargin, int rMargin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(lMargin,0, rMargin,0);

        view.setLayoutParams(params);
    }

    public void saveDisplaySize(){
        // Getting display width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;
    }

    public int getResponsiveTextSize(int val) {
        int baseSize = (int) (displayWidth*0.05);
        float scaleFactor = 0.83f;

        int valDigits = String.valueOf(val).length();

        return (int) (baseSize * Math.pow(scaleFactor, valDigits - 1));
    }

    public GridLayout.LayoutParams createGridLayoutParams(int x, int y, int spacing) {

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(x);
        params.columnSpec = GridLayout.spec(y);
        params.setMargins(spacing,spacing,spacing,spacing);
        grid.setPadding(spacing, spacing, spacing, spacing);

        return params;
    }

    // GETTERS AND SETTERS
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