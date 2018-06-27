package com.rauniyargames.moviequiz.dialougeClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowAnimationFrameStats;
import android.widget.Button;
import android.widget.TextView;

import moviequiz.rauniyargames.com.moviequiz.R;

/**
 * Created by lenovo on 4/6/2018.
 */

public class Answer_display extends Dialog implements View.OnClickListener {
    Activity activity;
    String text_to_Display;

    public Answer_display(Activity activity, String text_To_Display) {
        super(activity);
        this.activity = activity;
        this.text_to_Display = text_To_Display;
        Log.e("hint1 textToDisplay", String.valueOf(this.text_to_Display));
    }

    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hint1_answer);
        setCanceledOnTouchOutside(false);
        Button text = (Button) findViewById(R.id.hint_1_answer);
        Log.e("text to displayyyy",text_to_Display);
        text.setText(text_to_Display);
        Button cancel = findViewById(R.id.hint_1_cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hint_1_cancel: {
                dismiss();
            }
        }
    }
}