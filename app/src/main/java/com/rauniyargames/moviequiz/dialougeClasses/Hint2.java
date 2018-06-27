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

public class Hint2 extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Hint2(Activity activity) {
        super(activity);
        this.activity=activity;
    }
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_hint2_emoji_screen);
        setCanceledOnTouchOutside(false);
        Button yes=findViewById(R.id.hint_2_Yes_Emoji_Screen);
        Button cancel=findViewById(R.id.hint_2_Cancel_Emoji_Screen);
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.hint_2_Yes_Emoji_Screen:{
            dismiss();
           EmojiGameScreen emoji_game_screen =(EmojiGameScreen) activity;
            emoji_game_screen.hint2();
        }break;
        case R.id.hint_2_Cancel_Emoji_Screen:{
        dismiss();
        }break;
    }
    }
}
