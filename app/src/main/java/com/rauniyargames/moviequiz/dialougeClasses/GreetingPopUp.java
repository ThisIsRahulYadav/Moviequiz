package com.rauniyargames.moviequiz.dialougeClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.rauniyargames.moviequiz.gameclasses.EmojiGameScreen;

import moviequiz.rauniyargames.com.moviequiz.R;

/**
 * Created by lenovo on 4/12/2018.
 */

public class GreetingPopUp extends Dialog implements View.OnClickListener{
    private Boolean isExist;
    public Activity activity;
    public GreetingPopUp(Activity activity,Boolean isExist) {
        super(activity);
        this.activity=activity;
        this.isExist=isExist;
    }
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.greeting_popup);
        setCanceledOnTouchOutside(false);
    Button nxt =findViewById(R.id.greeting_next);
    nxt.setOnClickListener(this);
}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.greeting_next:{
                dismiss();
                EmojiGameScreen emojiGameScreen = (EmojiGameScreen) activity;
                Log.e("value of boole isexist", String.valueOf(isExist));
//                  if(isExist){
                     emojiGameScreen.nextLevel();
   //               }else {
                    //  emojiGameScreen.currentLevel();

     //             }
                  }

        }
    }
}
