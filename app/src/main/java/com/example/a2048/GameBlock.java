package com.example.a2048;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.WindowMetrics;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class GameBlock extends androidx.appcompat.widget.AppCompatTextView {

    private int posX, posY, value;


    public GameBlock( Context context, int posX, int posY, int value ) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.setValue(value);
    }

    public GameBlock(Context context) {
        super(context);
    }

    public void setValue (int value) {
        this.value = value;

        if (value!=0) {
            this.setText(String.valueOf(value));
        }
        else {
            this.setText("");
        }

        this.setBlockTier(value);
    }

    public int getValue() {
        return value;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public void setBlockTier(int value) {
        int backgroundColorResId, textColorResID;

        Drawable background = this.getBackground();

        background = background.mutate();

        if (value>4096) {
            textColorResID = R.color.light_text;
            backgroundColorResId = R.color.tier7;
        }
        else {
            switch (value) {
                case 2:
                    textColorResID = R.color.dark_text;
                    backgroundColorResId = R.color.tier1_1;
                    break;
                case 4:
                    textColorResID = R.color.dark_text;
                    backgroundColorResId = R.color.tier1_2;
                    break;
                case 8:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier2_1;
                    break;
                case 16:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier2_2;
                    break;
                case 32:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier3_1;
                    break;
                case 64:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier3_2;
                    break;
                case 128:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier4_1;
                    break;
                case 256:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier4_2;
                    break;
                case 512:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier5_1;
                    break;
                case 1024:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier5_2;
                    break;
                case 2048:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier6_1;
                    break;
                case 4096:
                    textColorResID = R.color.light_text;
                    backgroundColorResId = R.color.tier6_2;
                    break;
                default:
                    textColorResID = R.color.dark_text;
                    backgroundColorResId = R.color.tier0;
                    break;
            }
        }

        int TextColor = ContextCompat.getColor(this.getContext(), textColorResID);
        int BGColor = ContextCompat.getColor(this.getContext(), backgroundColorResId);


        this.setBackground(getDrawableBackground(BGColor));
        this.setTextColor(TextColor);
    }

    public GradientDrawable getDrawableBackground(int BGColor) {
        GradientDrawable newBackground = new GradientDrawable();
        newBackground.setShape(GradientDrawable.RECTANGLE);
        newBackground.setColor(BGColor);
        newBackground.setCornerRadius(15);

        return newBackground;
    }
}
