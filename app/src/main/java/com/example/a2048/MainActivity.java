package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gameGrid = findViewById(R.id.gameGrid);

        int spacing = 15;
        int val = 2;

        for (int x = 0; x < 4; x++) {           // Column (x)
            for (int y = 0; y < 4; y++) {       // Row (y)
                Context styledContext = new ContextThemeWrapper(this, R.style.GameBlockStyle); // Context with custom style is created
                GameBlock block = new GameBlock(styledContext); // Creating the gameblock with custom style


                // Setting block properties
                block.setPosX(x);
                block.setPosY(y);
                block.setValue(val);
                block.setTextSize(40);

                val *= 2;


                // Setting GridLayout properties
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 200;
                params.height = 200;
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
}