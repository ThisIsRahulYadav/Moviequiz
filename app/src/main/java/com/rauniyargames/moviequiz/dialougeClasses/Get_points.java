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
import com.rauniyargames.moviequiz.helperclasses.ReferenceWrapper;

import moviequiz.rauniyargames.com.moviequiz.R;

/**
 * Created by lenovo on 4/6/2018.
 */

public class Get_points extends Dialog implements View.OnClickListener {
public Activity activity;
    private ReferenceWrapper refer;
    public Get_points(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    protected void onCreate(Bundle savedIntstanceState){
        super.onCreate(savedIntstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.getpoints);
        setCanceledOnTouchOutside(false);
        refer = ReferenceWrapper.getReferenceWrapper(activity);

        Button rate_us = findViewById(R.id.rateUs);
        Button share_frnd = findViewById(R.id.shareFRND);
        Button facebook_like = findViewById(R.id.fbLike);
        Button whatch_video = findViewById(R.id.watchVideo);
        Button cross = findViewById(R.id.getPointsCross);
        rate_us.setOnClickListener(this);
        share_frnd.setOnClickListener(this);
        facebook_like.setOnClickListener(this);
        whatch_video.setOnClickListener(this);
        cross.setOnClickListener(this);
        //ReferenceWrapper refer = ReferenceWrapper.getReferenceWrapper(activity);

        int clicked = refer.getSharedPreference(activity, "clicked", 0);
        Log.e("the value ?????????", String.valueOf(clicked));
        if (clicked > 0) {
            Log.e("the value of rate us af", String.valueOf(clicked));
            findViewById(R.id.rate_us_layout).setVisibility(View.GONE);
       }

        int clicked1 = refer.getSharedPreference(activity,  ("clicked1"), 0);
        Log.e("the value facebook like", String.valueOf(clicked1));
        if (clicked1 > 0) {
            findViewById(R.id.facebook_like_layout).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rateUs:
                dismiss();
                EmojiGameScreen emoji_game_screen_rate_us =(EmojiGameScreen) activity;
                emoji_game_screen_rate_us.rate_US();
                break;

            case R.id.shareFRND:
                dismiss();
                EmojiGameScreen emoji_game_screen_share_frnds =(EmojiGameScreen) activity;
                emoji_game_screen_share_frnds.share_to_friends();
                break;

            case R.id.fbLike:
                dismiss();
                EmojiGameScreen emoji_game_screen_facebook_like =(EmojiGameScreen) activity;
                emoji_game_screen_facebook_like.like_Over_Facebook();
                break;

            case R.id.watchVideo:
                EmojiGameScreen emoji_game_screen_watch_video =(EmojiGameScreen) activity;
              //  emoji_game_screen_watch_video.watch_video();
                break;
            case R.id.getPointsCross:
                dismiss();
                break;
        }

    }
}
