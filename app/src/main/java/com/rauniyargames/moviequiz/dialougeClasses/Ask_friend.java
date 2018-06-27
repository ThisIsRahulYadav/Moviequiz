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

public class Ask_friend extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Ask_friend(Activity activity) {
        super(activity);
        this.activity=activity;

    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.ask_frnd);
        Button yes =findViewById(R.id.ask_Frnd_Yes);
        Button cancel=findViewById(R.id.ask_Frnd_Cancel);
    yes.setOnClickListener(this);
    cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.ask_Frnd_Yes:{
            EmojiGameScreen emoji_game_screen=(EmojiGameScreen) activity;
            emoji_game_screen.permission_Granted();
            emoji_game_screen.ask_To_Friend();
            dismiss();
        } break;
        case R.id.ask_Frnd_Cancel:{
        dismiss();
        }break;
    }
    }
}
