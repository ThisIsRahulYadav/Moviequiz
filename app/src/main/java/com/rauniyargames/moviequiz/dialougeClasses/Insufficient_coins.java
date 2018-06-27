package com.rauniyargames.moviequiz.dialougeClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.rauniyargames.moviequiz.gameclasses.EmojiGameScreen;

import moviequiz.rauniyargames.com.moviequiz.R;

/**
 * Created by lenovo on 4/6/2018.
 */

public class Insufficient_coins extends Dialog implements View.OnClickListener {
    public Activity activity;
    String text_toDisplay;
 //   String TextToDisplay;
    public Insufficient_coins(Activity activity,String textToDisplay) {
        super(activity);
        this.activity=activity;
        this.text_toDisplay=textToDisplay;
        //this.textToDisplay=textToDisplay;
    }
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.popup);
            setCanceledOnTouchOutside(false);

            Button yes=findViewById(R.id.popupYes);
            Button cancel=findViewById(R.id.popupCancel);
            yes.setOnClickListener(this);
            cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popupYes:{
                new Get_points(activity).show();
                dismiss();
                break;}
            case R.id.popupCancel:{ dismiss();
                EmojiGameScreen emoji_game_screen=(EmojiGameScreen) activity;
                emoji_game_screen.finish();
                break;}
        }

    }
}
