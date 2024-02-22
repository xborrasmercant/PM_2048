package com.example.a2048;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

public class MenuActivity extends AppCompatActivity {
    ConstraintLayout menuLayout;
    private StylizedTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuLayout = new ConstraintLayout(this);
        menuLayout.setId(View.generateViewId());

        setContentView(menuLayout);
        addComponentsToLayout();
        styleActivity();
    }

    private void addComponentsToLayout() {
        // Add Title
        title = new StylizedTextView(this, "2048", 75, ContextCompat.getColor(this, R.color.light_text), ContextCompat.getColor(this, R.color.tier4_2));
        title.setId(View.generateViewId());
        menuLayout.addView(title);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        extendViewWidth(title);

        Button newGameButton = createButton("NEW GAME");
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, GameActivity.class));
            }
        });

        Button highScoresButton = createButton("LEADERBOARDS");
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, HighScoresActivity.class));
            }
        });

        Button exitButton = createButton("BACK");
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button[] buttons = new Button[]{newGameButton, highScoresButton, exitButton};
        for (Button button : buttons) {
            menuLayout.addView(button);
            button.setId(View.generateViewId());
        }

        configureConstraints(buttons);
    }

    private Button createButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(30);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(layoutParams);
        return button;
    }

    private void configureConstraints( Button[] buttons) {
        ConstraintSet cs = new ConstraintSet();
        int spacing = 32;
        int parentID = ConstraintSet.PARENT_ID;
        int buttonId = buttons[0].getId();
        int titleID = title.getId();

        cs.clone(menuLayout);

        // TITLE constraints
        cs.connect(titleID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(titleID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(titleID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing*5);
        cs.connect(titleID, ConstraintSet.BOTTOM, buttonId, ConstraintSet.TOP, spacing);

        // BUTTONS constraints
        for (int i = 0; i < buttons.length; i++) {
            buttonId = buttons[i].getId();
            cs.connect(buttonId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, spacing);
            cs.connect(buttonId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, spacing);

            if (i == 0) {
                cs.connect(buttonId, ConstraintSet.TOP, titleID, ConstraintSet.BOTTOM, spacing*2);
            } else {
                cs.connect(buttonId, ConstraintSet.TOP, buttons[i - 1].getId(), ConstraintSet.BOTTOM, spacing);
            }
        }

        cs.applyTo(menuLayout);
    }

    private void styleActivity() {
        menuLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_brown));
    }

    private void extendViewWidth(View view) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
    }
}