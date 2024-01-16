package com.example.a2048;

import android.content.Context;
import android.util.AttributeSet;
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
        this.value = value;
    }

    public GameBlock(Context context) {
        super(context);
    }

    public void setValue (int value) {
        this.value = value;
        this.setText(String.valueOf(value));

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

        int TextColor = ContextCompat.getColor(this.getContext(), textColorResID);
        int BGColor = ContextCompat.getColor(this.getContext(), backgroundColorResId);
        this.setTextColor(TextColor);
        this.setBackgroundColor(BGColor);
    }
}
