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

public class Show_answer extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Show_answer(Activity activity) {
        super(activity);
        this.activity=activity;
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_hint3);
        setCanceledOnTouchOutside(false);
        Button yes=findViewById(R.id.hint_3_Yes);
        Button cancel=findViewById(R.id.hint_3_Cancel);
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hint_3_Yes:{
                dismiss();
               EmojiGameScreen emoji_game_screen =(EmojiGameScreen) activity;
                emoji_game_screen.showAnswer();break;}
            case R.id.hint_3_Cancel:{
            dismiss();
            }break;
        }
    }
}
