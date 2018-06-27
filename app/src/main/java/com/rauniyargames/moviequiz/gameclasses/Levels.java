package com.rauniyargames.moviequiz.gameclasses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.rauniyargames.moviequiz.helperclasses.ReferenceWrapper;

import moviequiz.rauniyargames.com.moviequiz.R;

import static android.view.Gravity.CENTER;

public class Levels extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private ReferenceWrapper refer;
    private String category;
    private MediaPlayer buttonClickSound;
    private int currentlevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        refer = ReferenceWrapper.getReferenceWrapper(this);
        category = getIntent().getStringExtra("CATEGORY");

        addOnClickListener();
        levelFont();
    }

    private void playClickSound() {
        int clickSound = refer.getSharedPreference(this, "sound", 1);
        if (clickSound > 0) {
            try {
                if (buttonClickSound != null) {
                    buttonClickSound.stop();
                    buttonClickSound.reset();
                    buttonClickSound.release();
                }
            } catch (Exception ex) {

            }
            buttonClickSound = null;

            buttonClickSound = MediaPlayer.create(this, R.raw.on_click_sound);
            buttonClickSound.start();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        level_Button();
        int sound = refer.getSharedPreference(this, "music", 1);
        if (sound > 0) {
            // music_switch.setChecked(true);

            refer.startSound(Levels.this);
        } else {
            //  music_switch.setChecked(false);
            refer.stopSound(Levels.this);

        }

        currentlevel = refer.getSharedPreference(Levels.this, category + ("CURRENT"), 0);
        Log.e("value of CURRENT game", String.valueOf(currentlevel));
    }

    private void addOnClickListener() {
        Button back = (Button) findViewById(R.id.backLevel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playClickSound();
                Levels.this.finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        int sound = refer.getSharedPreference(Levels.this, "music", 1);
        Log.e("sound at pause", "" + sound);
        if (sound > 0) {
           /* game_bg.setLooping(false);
            game_bg.stop();
            game_bg.release();*/
            refer.stopSound(Levels.this);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playClickSound();
            Intent myintent = new Intent(Levels.this, EmojiGameScreen.class);
            Log.d("v value on click", String.valueOf(v.getTag()));
            myintent.putExtra("CATEGORY", category);
            startActivity(myintent);
            int level = (int) v.getTag();
            refer.setSharedPreference(Levels.this, category + ("CURRENT"), level);
            Log.e("the value of aaaaa", String.valueOf(level));
        }
    };

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.sound_switch) ;
        {
            if (isChecked) {
                refer.setSharedPreference(Levels.this, "sound", 1);
            } else {
                refer.setSharedPreference(Levels.this, "sound", 0);
            }
        }
        if (buttonView.getId() == R.id.music_switch) ;
        {
            if (isChecked) {
                refer.setSharedPreference(Levels.this, "music", 1);
            } else {
                refer.setSharedPreference(Levels.this, "music", 0);
            }
        }
    }

    private void levelFont() {
        TextView tx = (TextView) findViewById(R.id.levelFont);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/DFPop91.ttf");

        tx.setTypeface(custom_font);
    }

    public void level_Button() {
        LinearLayout level = (LinearLayout) findViewById(R.id.levelButton);
        level.removeAllViews();
        int width = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams button = new LinearLayout.LayoutParams(width / 5, width / 5);
        RelativeLayout.LayoutParams tick_button_layoutparam = new RelativeLayout.LayoutParams(width/14, width/14);
        tick_button_layoutparam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        button.setMargins(width / 25, width / 25, 0, 0);
        int counter = 0;
        int currentLevel = refer.getSharedPreference(Levels.this, category + ("level"), 3);
        Log.e("currentLevel",""+currentLevel);
        Log.e("Current level value ", String.valueOf(currentLevel));
        int count = 0;
        for (int i = 0; i < 50; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            for (int j = 0; j < 4; j++) {
                count++;
                //Log.e("count value", String.valueOf(count));
               RelativeLayout  relativeLayout= new RelativeLayout(this);
               TextView levelButton =new TextView(this);
               ImageView tick_button = new ImageView(this);
               relativeLayout.setLayoutParams(button);
                int coinValue = refer.getSharedPreference(Levels.this, (category + ("alreadyPlayed") + count), 0);
                levelButton.setTag(count);
                levelButton.setText("" + count);
                levelButton.setTextColor(Color.WHITE);
                levelButton.setGravity(CENTER);
                levelButton.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                if (counter < currentLevel) {
                    levelButton.setOnClickListener(listener);
                    levelButton.setBackgroundResource(R.drawable.level_active);
                    if(coinValue>0){
                        tick_button.setBackgroundResource(R.drawable.tick);
                        tick_button.setLayoutParams(tick_button_layoutparam);
                    }

                    counter++;
                } else {
                    levelButton.setBackgroundResource(R.drawable.level_de_active);
                }

                relativeLayout.addView(levelButton);
                relativeLayout.addView(tick_button);
                rowLayout.addView(relativeLayout);

            }
            level.addView(rowLayout);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            if (buttonClickSound != null) {
                buttonClickSound.stop();
                buttonClickSound.reset();
                buttonClickSound.release();
                buttonClickSound = null;
            }
        } catch (Exception e) {

        }
    }
}
