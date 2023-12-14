package com.example.a2048;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class GameBlock extends Fragment {

    private String GameBlockID;

    public GameBlock (String GameBlockID) {
        this.GameBlockID = GameBlockID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        return view;
    }

    public void changeBlockValue(String value) {
        TextView bTextView = findViewById(R.id.blockValue);
        bTextView.setText(value);

        int backgroundColorResId, textColorResID;
        switch (value) {
            case "":
                textColorResID = R.color.dark_text;
                backgroundColorResId = R.color.tier0;
                break;
            case "2":
                textColorResID = R.color.dark_text;
                backgroundColorResId = R.color.tier1_1;
                break;
            case "4":
                textColorResID = R.color.dark_text;
                backgroundColorResId = R.color.tier1_2;
                break;
            case "8":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier2_1;
                break;
            case "16":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier2_2;
                break;
            case "32":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier3_1;
                break;
            case "64":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier3_2;
                break;
            case "128":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier4_1;
                break;
            case "256":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier4_2;
                break;
            case "512":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier5_1;
                break;
            case "1024":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier5_2;
                break;
            case "2048":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier6_1;
                break;
            case "4096":
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier6_2;
                break;
            default:
                textColorResID = R.color.light_text;
                backgroundColorResId = R.color.tier7;
                break;
        }

        int TextColor = ContextCompat.getColor(this, textColorResID);
        int BGColor = ContextCompat.getColor(this, backgroundColorResId);
        bTextView.setTextColor(TextColor);
        bTextView.setBackgroundColor(BGColor);
    }

}
