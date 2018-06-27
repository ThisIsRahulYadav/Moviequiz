package com.rauniyargames.moviequiz.dialougeClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.rauniyargames.moviequiz.gameclasses.EmojiGameScreen;

import moviequiz.rauniyargames.com.moviequiz.R;

/**
 * Created by lenovo on 4/6/2018.
 */

public class Hint1 extends Dialog implements View.OnClickListener {
    public Activity activity;

    public Hint1(Activity activity) {
        super(activity);
        this.activity=activity;
    }
 protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_hint);
        setCanceledOnTouchOutside(false);
     Button yes=findViewById(R.id.hint_1_Yes);
     Button cancel=findViewById(R.id.hint_1_Cancel);
     yes.setOnClickListener(this);
     cancel.setOnClickListener(this);
 }
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.hint_1_Yes:{
            dismiss();
           EmojiGameScreen emoji_game_screen =(EmojiGameScreen) activity;
            emoji_game_screen.emoji_hint1();
        }break;
        case R.id.hint_1_cancel:{
        dismiss();
        }break;
    }
    }
}
