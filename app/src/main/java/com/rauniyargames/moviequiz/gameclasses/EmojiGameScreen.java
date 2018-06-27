package com.rauniyargames.moviequiz.gameclasses;

import android.Manifest;
import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rauniyargames.moviequiz.dialougeClasses.Answer_display;
import com.rauniyargames.moviequiz.dialougeClasses.Ask_friend;
import com.rauniyargames.moviequiz.dialougeClasses.GreetingPopUp;
import com.rauniyargames.moviequiz.dialougeClasses.Hint1;
import com.rauniyargames.moviequiz.dialougeClasses.Hint2;
import com.rauniyargames.moviequiz.dialougeClasses.Insufficient_coins;
import com.rauniyargames.moviequiz.dialougeClasses.Show_answer;
import com.rauniyargames.moviequiz.helperclasses.Questions;
import com.rauniyargames.moviequiz.helperclasses.ReferenceWrapper;
import com.rauniyargames.moviequiz.helperclasses.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import moviequiz.rauniyargames.com.moviequiz.R;

import static android.view.Gravity.CENTER;

public class EmojiGameScreen extends AppCompatActivity {

    private ReferenceWrapper refer;
    private Questions questions;
    private String category;
    private TextView coin_update;
    private Button skip;
    int currentlevel;
    private int storage_permission_code = 1;
    Boolean alreadyPlayed = false;
    private MediaPlayer buttonClickSound;
    private MediaPlayer playRightWrongSound;
    FloatingActionButton fab, fab_hint1, fab_hint2, fab_hint3, fab_hint4;
    Boolean isOPen = false;
    Boolean isExist = false;
    private View   fabBGLayout;
    //    Activity activity;

    //  public(Activity activity,String category)

    public void alredy_played() {
        int coinValue = refer.getSharedPreference(EmojiGameScreen.this, (category + ("alreadyPlayed") + currentlevel), 0);

        if (coinValue > 0) {
            alreadyPlayed = true;
        } else {
            alreadyPlayed = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_game_screen);
        refer = ReferenceWrapper.getReferenceWrapper(EmojiGameScreen.this);
        category = getIntent().getStringExtra("CATEGORY");
        //   timer();
        addOnClicklistener();
        current_level();
        coin_updater();
        emoji_display_setup();
        emoji_answer_setup();
        emoji_answer_key_setup();
        // hint1();
        alredy_played();
        refer.setSharedPreference(EmojiGameScreen.this, category + ("game"), 0);
    }

    private void playRightWrongSound(int rawFileName) {

        int playSound = refer.getSharedPreference(this, "sound", 1);
        if (playSound > 0) {

            try {
                if (playRightWrongSound != null) {
                    playRightWrongSound.stop();
                    playRightWrongSound.reset();
                    playRightWrongSound.release();
                    playRightWrongSound = null;
                }
            } catch (Exception ex) {
                Log.e("MediaPlayRightWrong", ex.toString());
            }
            playRightWrongSound = null;

            playRightWrongSound = MediaPlayer.create(this, rawFileName);
            playRightWrongSound.start();
        }
    }

    private void playClickSound() {
        int clickSound = refer.getSharedPreference(this, "sound", 1);
        if (clickSound > 0) {
            try {
                if (buttonClickSound != null) {
                    buttonClickSound.stop();
                    buttonClickSound.reset();
                    buttonClickSound.release();
                    buttonClickSound = null;
                }
            } catch (Exception ex) {
                Log.e("MediaPlayClickSound", ex.toString());
            }
            buttonClickSound = null;

            buttonClickSound = MediaPlayer.create(this, R.raw.on_click_sound);
            buttonClickSound.start();
        }

    }

    private void addOnClicklistener() {
        coin_update = (TextView) findViewById(R.id.coin);
        skip = (Button) findViewById(R.id.skip);
        Button back = (Button) findViewById(R.id.backGame);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_hint1 = (FloatingActionButton) findViewById(R.id.fab_hint1);
        fab_hint2 = (FloatingActionButton) findViewById(R.id.fab_hint2);
        fab_hint3 = (FloatingActionButton) findViewById(R.id.fab_hint3);
        fab_hint4 = (FloatingActionButton) findViewById(R.id.fab_hint4);
        fabBGLayout=(View) findViewById(R.id.fabBGLayout);

        fab.setOnClickListener(levels_listener);
        fab_hint1.setOnClickListener(levels_listener);
        fab_hint2.setOnClickListener(levels_listener);
        fab_hint3.setOnClickListener(levels_listener);
        fab_hint4.setOnClickListener(levels_listener);
        skip.setOnClickListener(levels_listener);
        // getHint.setOnClickListener(levels_listener);
        back.setOnClickListener(levels_listener);
        fabBGLayout.setOnClickListener(levels_listener);

    }

    View.OnClickListener levels_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playClickSound();
            switch (v.getId()) {
                case R.id.skip: {
                    if(isOPen){
                       fabMenueClose();
                    }
                    int level = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
                    int currentPosition = refer.getSharedPreference(EmojiGameScreen.this, category + ("level"), 3);
                    Log.e("level in skip", String.valueOf(level));
                    Log.e("current pos in skip", String.valueOf(currentPosition));
                    if (level == currentPosition) {
 //                       skip.setClickable(false);
                        Toast.makeText(EmojiGameScreen.this, "level locked", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("current level nxt", String.valueOf(level));
                        int game = refer.getSharedPreference(EmojiGameScreen.this, category + ("game"), 0);
                        nextLevel();
                    }
                }
                break;
                case R.id.backGame: {
                    EmojiGameScreen.this.finish();
                    if (isOPen) {
                        fabMenueClose();
                    }
                }
                break;
                case R.id.fab: {
                    if (isOPen) {
                        fabMenueClose();
                    } else {
                        fabMenueOpen();
                    }
                }
                break;
                case R.id.fab_hint1: {
                    new Ask_friend(EmojiGameScreen.this).show();
                    fabMenueClose();

                }
                break;
                case R.id.fab_hint2: {
                    new Hint1(EmojiGameScreen.this).show();
                    fabMenueClose();
                }
                break;
                case R.id.fab_hint3: {
                    new Hint2(EmojiGameScreen.this).show();
                    fabMenueClose();
                }
                break;
                case R.id.fab_hint4: {
                    new Show_answer(EmojiGameScreen.this).show();
                    fabMenueClose();
                }
                break;
                case R.id.fabBGLayout:{
                    fabMenueClose();
                }
            }
        }
    };

    private void fabMenueOpen() {
        isOPen = true;
        fabBGLayout.setVisibility(View.VISIBLE);
        fab_hint1.setVisibility(View.VISIBLE);
        fab_hint2.setVisibility(View.VISIBLE);
        fab_hint3.setVisibility(View.VISIBLE);
        fab_hint4.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(360);
        fab_hint1.animate().translationX(getResources().getDimension(R.dimen.dimention_45));
        fab_hint2.animate().translationX(getResources().getDimension(R.dimen.dimention_95));
        fab_hint3.animate().translationX(getResources().getDimension(R.dimen.dimention_145));
        fab_hint4.animate().translationX(getResources().getDimension(R.dimen.dimention_195));

        fab_hint1.setClickable(true);
        fab_hint2.setClickable(true);
        fab_hint3.setClickable(true);
        fab_hint4.setClickable(true);
    }

    private void fabMenueClose() {

        isOPen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-360);
        fab_hint1.animate().translationX(0);
        fab_hint2.animate().translationX(0);
        fab_hint3.animate().translationX(0);
        fab_hint4.animate().translationX(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isOPen) {
                    fab_hint1.setVisibility(View.GONE);
                    fab_hint2.setVisibility(View.GONE);
                    fab_hint3.setVisibility(View.GONE);
                    fab_hint4.setVisibility(View.GONE);

                }
            }


            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //    timer();
        current_level();
        alredy_played();
        //currentLevel();
        int sound = refer.getSharedPreference(this, "music", 1);
        if (sound > 0) {


            refer.startSound(EmojiGameScreen.this);
        } else {

            refer.stopSound(EmojiGameScreen.this);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        int sound = refer.getSharedPreference(EmojiGameScreen.this, "music", 1);
        Log.e("sound at pause", "" + sound);
        if (sound > 0) {

            refer.stopSound(EmojiGameScreen.this);
        }
    }

    @Override
    public void onBackPressed() {
        if (isOPen) {
            fabMenueClose();
        } else {
            super.onBackPressed();
        }
    }

    public void nextLevel() {
        int level1 = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
        int currentPosition = refer.getSharedPreference(EmojiGameScreen.this, category + ("level"), 3);
        if (level1 == currentPosition) {
            skip.setClickable(false);
            Toast.makeText(EmojiGameScreen.this, "level locked", Toast.LENGTH_SHORT).show();

        } else {
            isExist = true;
            refer.setSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0) + 1);
            int level = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
            Log.e("current level nxt", String.valueOf(level));
            int game = refer.getSharedPreference(EmojiGameScreen.this, category + ("game"), 0);
            int currentLevel = refer.getSharedPreference(EmojiGameScreen.this, category + ("level"), 3);
            if (level >= currentLevel) {
                isExist = false;
                Toast.makeText(EmojiGameScreen.this, "Level Locked", Toast.LENGTH_SHORT).show();
                skip.setClickable(false);
            }
            current_level();
            emoji_display_setup();
            emoji_answer_setup();
            emoji_answer_key_setup();
            // timer();
            alredy_played();
        }
    }

    public void currentLevel() {
        //refer.setSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0) + 1);
        int level = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
        Log.e("current level nxt", String.valueOf(level));
        int game = refer.getSharedPreference(EmojiGameScreen.this, category + ("game"), 0);
        int currentLevel = refer.getSharedPreference(EmojiGameScreen.this, category + ("level"), 3);
        Log.e(" valllll  levelllll", String.valueOf(currentLevel));
        if (level == currentLevel) {
            Log.e("levelllll valllll", String.valueOf(currentLevel));
            isExist = false;
            Toast.makeText(EmojiGameScreen.this, "Level Locked", Toast.LENGTH_SHORT).show();
            skip.setClickable(false);

        }
    }

    public void emoji_display_setup() {
        currentlevel = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
        questions = database.readFromdatabaseEmoji(currentlevel, EmojiGameScreen.this, category + ("_emoji_database.txt"));

        TextView question = (TextView) findViewById(R.id.question_display);
        String que = questions.getQuestion();
        question.setText(que);
        String picname = questions.getEmojiNAme();           //emoji frame
        Log.e("the value of picnamee", picname);
        String[] pic = picname.split(",");
        emoji_display(pic);
        alredy_played();
    }

    public void emoji_answer_setup() {
        currentlevel = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
        questions = database.readFromdatabaseEmoji(currentlevel, EmojiGameScreen.this, category + ("_emoji_database.txt"));
        String ans = questions.getAnswer();                   // answer frame
        Log.e("the answer is ", ans);
        final String[] ans_length = ans.split(" ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                emoji_answer(ans_length);
            }
        }, 300);
    }

    public void emoji_answer_key_setup() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                currentlevel = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
                questions = database.readFromdatabaseEmoji(currentlevel, EmojiGameScreen.this, category + ("_emoji_database.txt"));
                String anss = questions.getAnswer();                    // answer keys frame
                String answerValue = anss.replaceAll(" ", "");
                int answerLength = answerValue.length();
                final int anwerlength;

                if (answerLength <= 7) {
                    anwerlength = 10 - answerLength;
                } else if (answerLength <= 14) {
                    anwerlength = 20 - answerLength;
                } else {
                    anwerlength = 30 - answerLength;
                }
                if (alreadyPlayed) {
                    String str = "";
                    for (int i = 0; i < answerValue.length(); i++) {
                        str += " ";
                    }
                    answerValue = str;
                }
                String answer = shuffle(answerValue.toUpperCase() + getRandomString(anwerlength));
                emoji_answer_keys(answer);
                Log.e("answer value ", answer);
            }
        }, 300);
    }

    public String shuffle(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    private String getRandomString(int strLength) {
        String str = "";
        String options = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < strLength; i++) {
            Random random = new Random();
            int ran = random.nextInt(25);
            str += options.charAt(ran);
        }
        return str;
    }

    private void current_level() {
        TextView currentLevel = (TextView) findViewById(R.id.level_count);
        int level = refer.getSharedPreference(EmojiGameScreen.this, category + ("CURRENT"), 0);
        currentLevel.setText("" + level);
    }


    private void coin_updater() {
        //refer.setSharedPreference(EmojiGameScreen.this, "coin", 30);
        int coin = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
        Log.e("the value of coin", String.valueOf(coin));
        //TextView coin_update = (TextView) findViewById(R.id.coin);
        coin_update.setText("" + coin);
    }

    public void coin_set(int coinss) {
        coin_update.setText("" + coinss);
    }

    public void emoji_display(final String[] emoji_lenghth) {
        final LinearLayout emoji_display = (LinearLayout) findViewById(R.id.emoji_display);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = getResources().getDisplayMetrics().widthPixels;
                int cellwidth = width / 6;
                Log.e(" value of emojisije", String.valueOf(cellwidth));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cellwidth, cellwidth);
                int cellpadding = cellwidth / 12;
                emoji_display.removeAllViews();
                layoutParams.setMargins(cellpadding, cellpadding, 0, 0);
                //  LinearLayout rowLayout = new LinearLayout(EmojiGameScreen.this);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //   rowLayout.setLayoutParams(layoutParams1);
                for (int i = 0; i < emoji_lenghth.length; i++) {
                    View emoji = new View(EmojiGameScreen.this);
                    LinearLayout view1 = new LinearLayout(EmojiGameScreen.this);
                    view1.setGravity(CENTER);
                    view1.setBackgroundResource(R.drawable.emoji_background);
                    view1.setLayoutParams(layoutParams);
                    emoji_display.setGravity(CENTER);
                    view1.setLayoutParams(layoutParams);
                    emoji.setLayoutParams(layoutParams1);
                    view1.addView(emoji);
                    Log.e("emoji_lenghth[i]", emoji_lenghth[i]);
                    int resourceId = EmojiGameScreen.this.getResources().getIdentifier(emoji_lenghth[i].trim(), "drawable", getPackageName());
                    Log.e("resource id is", String.valueOf(resourceId));
                    emoji.setBackgroundResource(resourceId);
                    // rowLayout.addView(emoji);
                    emoji_display.addView(view1);
                }
            }
        }, 800);
    }

    public void emoji_answer(String[] emoji_ans_length) {
        LinearLayout emoji_answer = (LinearLayout) findViewById(R.id.answer_display);
        int width = getResources().getDisplayMetrics().widthPixels;
        int c = width / 90;
        Log.e("the value of c", String.valueOf(c));
        emoji_answer.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 12, width / 10);
        layoutParams.setMargins(width / 90, width / 90, width / 90, width / 90);
        for (int i = 0; i < emoji_ans_length.length; i++) {
            LinearLayout rowlayout = new LinearLayout(this);
            for (int j = 0; j < emoji_ans_length[i].length(); j++) {
                Button btn = new Button(this);
                btn.setBackgroundResource(R.drawable.emoji_button);
                btn.setLayoutParams(layoutParams);
                rowlayout.setGravity(CENTER);
                btn.setGravity(CENTER);
                rowlayout.addView(btn);
                if (alreadyPlayed) {
                    btn.setBackgroundResource(R.drawable.emoji_button_correct);
                    btn.setText(("" + emoji_ans_length[i].charAt(j)).toUpperCase());
                } else {
                    btn.setOnClickListener(emoji_listener);

                }
            }
            emoji_answer.addView(rowlayout);
        }
    }

    private void emoji_answer_keys(String answerkeys) {
        LinearLayout emoji_answer = (LinearLayout) findViewById(R.id.answer_key);
        int width = getResources().getDisplayMetrics().widthPixels;
        int c = width / 90;
        Log.e("the value of c", String.valueOf(c));
        emoji_answer.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 12, width / 12);
        layoutParams.setMargins(width / 90, width / 90, 0, width / 90);
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (count >= answerkeys.length()) break;
            LinearLayout rowlayout = new LinearLayout(this);
            rowlayout.setGravity(CENTER);
            for (int j = 0; j < 10; j++) {
                TextView btn = new TextView(this);
                btn.setBackgroundResource(R.drawable.emoji_button);
                if (alreadyPlayed) {
                    btn.setVisibility(View.INVISIBLE);
                }
                if (!alreadyPlayed)
                    btn.setLayoutParams(layoutParams);
                //View.OnClickListener listener;

                btn.setOnClickListener(listener);
                btn.setTag("" + answerkeys.charAt(count));
                btn.setText("" + answerkeys.charAt(count));
                count++;
                btn.setGravity(CENTER);
                rowlayout.addView(btn);
                if (count >= answerkeys.length()) break;
            }
            emoji_answer.addView(rowlayout);
        }
    }

    View.OnClickListener emoji_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playClickSound();
            TextView button = (TextView) v;
            int breaks = 0;
            buttonClickable(true);
            if (!button.getText().toString().isEmpty()) {
                String text = button.getText().toString();
                LinearLayout layout = (LinearLayout) findViewById(R.id.answer_key);
                Log.e("the value of text", text);
                int key_board_child = layout.getChildCount();
                for (int i = 0; i < key_board_child; i++) {
                    LinearLayout view = (LinearLayout) layout.getChildAt(i);
                    for (int j = 0; j < view.getChildCount(); j++) {
                        if (view.getChildAt(j).getVisibility() == view.INVISIBLE && view.getChildAt(j).getTag().equals(text)) {
                            view.getChildAt(j).setVisibility(View.VISIBLE);
                            button.setText("");
                            breaks = 1;
                            break;
                        }
                    }
                    if (breaks == 1) break;
                }
            }
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playClickSound();
            Log.e("clr", "clicked");
            String tag = v.getTag().toString();
            String a = "";
            int count = 0;
            int breaks = 0;
            String userFilledAnswer = "";
            a += tag;
            Log.e("the value of a", a);
            LinearLayout answerboard = (LinearLayout) findViewById(R.id.answer_display);
            int answerBoardChild = answerboard.getChildCount();
            Log.e("valueanswer board child", String.valueOf(answerBoardChild));
            for (int i = 0; i < answerBoardChild; i++) {
                LinearLayout layout = (LinearLayout) answerboard.getChildAt(i);
                if (i > 0) {
                    userFilledAnswer += " ";
                    count++;
                }
                for (int j = 0; j < layout.getChildCount(); j++) {
                    Log.e("you reach here bfr", String.valueOf(j));
                    Log.e("i,j", "i= " + i + "j=" + j);
                    TextView textView = ((TextView) layout.getChildAt(j));
                    Log.e("the value of break ", String.valueOf(breaks));
                    if (textView.getText().toString().isEmpty() && breaks != 1) {
                        Log.e("you reach here aft", String.valueOf(j));
                        textView.setText(tag);
                        textView.setTextColor(Color.BLACK);
                        v.setVisibility(View.INVISIBLE);
                        breaks = 1;
                    }
                    userFilledAnswer += textView.getText().toString();
                }
            }
            Log.e("the value of userfill 1", userFilledAnswer);
            Log.e(" all filled method val", String.valueOf(check_all_filled()));

            if (check_all_filled())
                if (userFilledAnswer.equalsIgnoreCase(questions.getAnswer())) {
                    buttonClickable(false);
                    Toast.makeText(EmojiGameScreen.this, "correct answer", Toast.LENGTH_SHORT).show();
                    playRightWrongSound(R.raw.beep_right);
                    changeButtonColor(R.drawable.emoji_button_correct);
                    new GreetingPopUp(EmojiGameScreen.this, isExist).show();
                    refer.setSharedPreference(EmojiGameScreen.this, (category + ("alreadyPlayed") + currentlevel), 1);
                    refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) + 10);
                    /*refer.setSharedPreference(EmojiGameScreen.this, "level", refer.getSharedPreference(EmojiGameScreen.this, "level", 3) + 1);*/
                    refer.setSharedPreference(EmojiGameScreen.this, "game", refer.getSharedPreference(EmojiGameScreen.this, "game", 0) + 1);
                    int game = refer.getSharedPreference(EmojiGameScreen.this, "game", 0);
                    Log.e("GAME VALUE +1 EVERYTIME", String.valueOf(game));
                    int gameValue = refer.getSharedPreference(EmojiGameScreen.this, "gamevalue", 2);
                    Log.e(" int gamevaluebefore if", String.valueOf(gameValue));
                    if (gameValue == game) {
                        Log.e("GAME VALUE +1 afterIF", String.valueOf(game));
                        Log.e("gamevalue before ++ if", String.valueOf(gameValue));
                        refer.setSharedPreference(EmojiGameScreen.this, category + ("level"), refer.getSharedPreference(EmojiGameScreen.this, category + ("level"), 3) + 1);
                        int currentLevel = refer.getSharedPreference(EmojiGameScreen.this, category + ("level"), 3);
                        Log.e("levell value", String.valueOf(currentLevel));
                        refer.setSharedPreference(EmojiGameScreen.this, "gamevalue", refer.getSharedPreference(EmojiGameScreen.this, "gamevalue", 2) + 1);
                        //refer.setSharedPreference(EmojiGameScreen.this,"");
                        Log.e("game value after ++", String.valueOf(gameValue));
                        Toast.makeText(EmojiGameScreen.this, "new level unlocked", Toast.LENGTH_SHORT).show();
                        skip.setClickable(true);
                    }
                    int coins = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
                    // TextView coin1=(TextView)findViewById(R.id.coin);
                    coin_update.setText("" + coins);
                    // activity.finish()
                } else {
                    buttonClickable(false);
                    playRightWrongSound(R.raw.beep_wrong);
                    refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) - 10);
                    int coins = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
                    refer.setSharedPreference(EmojiGameScreen.this, (category + ("alreadyPlayed") + currentlevel), 0);
                    // TextView coin1=(TextView)findViewById(R.id.coin);
                    coin_update.setText("" + coins);
                    Toast.makeText(EmojiGameScreen.this, "wrong answer try again", Toast.LENGTH_SHORT).show();
                    changeButtonColor(R.drawable.emoji_button_wrong);
                    if (coins < 10) {
                        refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) * 0);
                        coin_update.setText("0");
                        new Insufficient_coins(EmojiGameScreen.this, category).show();
                    }
                }
        }
    };

    private boolean check_all_filled() {
        LinearLayout answer_display = (LinearLayout) findViewById(R.id.answer_display);
        int child_answer_display = answer_display.getChildCount();
        for (int i = 0; i < child_answer_display; i++) {
            LinearLayout layout = (LinearLayout) answer_display.getChildAt(i);
            for (int j = 0; j < layout.getChildCount(); j++) {
                TextView textView = (TextView) layout.getChildAt(j);
                Log.e("the value of child(j)", String.valueOf(j));
                if (textView.getText().toString().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }


    private void changeButtonColor(int drawable) {
        LinearLayout answerFrame = (LinearLayout) findViewById(R.id.answer_display);
        int answerframeChild = answerFrame.getChildCount();


        for (int i = 0; i < answerframeChild; i++) {

            LinearLayout layout = (LinearLayout) answerFrame.getChildAt(i);

            for (int j = 0; j < layout.getChildCount(); j++) {
                Log.e("I,j", "i=" + i + ", J=" + j);

                TextView textView = ((TextView) layout.getChildAt(j));
                textView.setBackgroundResource(drawable);
                //isClickable=true;
            }
        }
    }

    /*Boolean isClickable=false;*/
    private void buttonClickable(boolean clicakble) {
        LinearLayout answerFrame = (LinearLayout) findViewById(R.id.answer_key);
        int answerframeChild = answerFrame.getChildCount();

        for (int i = 0; i < answerframeChild; i++) {

            LinearLayout layout = (LinearLayout) answerFrame.getChildAt(i);

            for (int j = 0; j < layout.getChildCount(); j++) {
                Log.e("I,j", "i=" + i + ", J=" + j);

                TextView textView = ((TextView) layout.getChildAt(j));
                // if(isClickable){
                //  textView.setClickable(true);
                //}else{
                textView.setClickable(clicakble);
            }//}
        }
    }

    public void emoji_hint1() {
        playClickSound();
        int coins = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
        if (coins >= 10) {
            refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) - 10);
            int coin_value = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
            coin_update.setText("" + coin_value);
            String r = null;
            r = questions.getHint();
            Log.e("hint1111 value", r);
            new Answer_display(EmojiGameScreen.this, questions.getHint()).show();
            //      new (EmojiGameScreen.this, questions.getHint()).sh
        }
        if (coins < 10) {
            new Insufficient_coins(EmojiGameScreen.this, category).show();
        }
        if (coins < 4) {
            refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) * 0);
            coin_update.setText("" + coins);
        }
    }

    public void hint2() {
        playClickSound();
        int coins = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
        if (coins >= 10) {
            refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) - 30);
            int coin_Value = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
            coin_update.setText("" + coin_Value);
            manageHint2();
        }
        if (coins < 29) {
           /* refer.setsharedpreference(Emoji_Game_Screen.this, getResources().getString(R.string.coin), refer.getsharedpreference(Emoji_Game_Screen.this, getResources().getString(R.string.coin), 30) * 0);
            coin.setText("0");*/
            new Insufficient_coins(EmojiGameScreen.this, category).show();
        }
        // int coins = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
        if (coins < 4) {
            refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) * 0);
            coin_update.setText("" + coins);
        }
    }


    private void manageHint2() {
        String answer = questions.getAnswer().replace(" ", "");
        Log.e("answer", answer);
        LinearLayout keysFrame = (LinearLayout) findViewById(R.id.answer_key);
        for (int i = 0; i < answer.length(); i++) {
            int breaks = 0;
            Log.e("answerKey", "" + answer.charAt(i));
            for (int j = 0; j < keysFrame.getChildCount() && breaks == 0; j++) {
                LinearLayout layout = (LinearLayout) keysFrame.getChildAt(j);
                for (int k = 0; k < layout.getChildCount(); k++) {
                    String tag = layout.getChildAt(k).getTag().toString();
                    Log.e("tag", tag);
                    Log.e("char at i", "" + answer.charAt(i));
                    if (tag.equalsIgnoreCase("" + answer.charAt(i)) && layout.getChildAt(k).getId() != 0) {
                        Log.e("answerInnerKey", "" + answer.charAt(i));
                        layout.getChildAt(k).setBackgroundColor(Color.parseColor("#955FDC39"));
                        //layout.getChildAt(k).setBackgroundResource(R.drawable.green_key_button);
                        layout.getChildAt(k).setId(0);
                        breaks = 1;
                        break;
                    }
                }
            }
        }
    }

    public void showAnswer() {
        playClickSound();
        final int coinsValue = refer.getSharedPreference(EmojiGameScreen.this, "coin", 30);
        Log.e("coins valueeeee", String.valueOf(coinsValue));
        if (coinsValue < 50) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //  Animation leftToRight = AnimationUtils.loadAnimation(Emoji_Game_Screen.this, R.anim.slideright);
                    new Insufficient_coins(EmojiGameScreen.this, category).show();
                }
            }, 200);
        } else {
            refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) - 50);
            int coinValue = (refer.getSharedPreference(EmojiGameScreen.this, "coin", 30));
            coin_update.setText("" + coinValue);
            new Answer_display(EmojiGameScreen.this, questions.getAnswer()).show();
        }

    }

    public void rate_US() {
        playClickSound();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        startActivity(intent);
        refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) + 30);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int coin_Value = (refer.getSharedPreference(EmojiGameScreen.this, "coin", 30));
                coin_update.setText("" + coin_Value);
            }
        }, 5000);
        refer.setSharedPreference(EmojiGameScreen.this, "clicked", 1);
        int clicked = refer.getSharedPreference(EmojiGameScreen.this, "clicked", 0);
        Log.e("the value of rate us", String.valueOf(clicked));
        if (clicked > 0) {
            Log.e("the value of rate us af", String.valueOf(clicked));
            findViewById(R.id.rate_us_layout).setVisibility(View.INVISIBLE);
        }
    }

    public void like_Over_Facebook() {
        playClickSound();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.facebook.com/goodwilltechnologyservices/"));
        startActivity(intent);
        refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) + 30);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int coin_Value = (refer.getSharedPreference(EmojiGameScreen.this, "coin", 30));
                coin_update.setText("" + coin_Value);
            }
        }, 5000);
        refer.setSharedPreference(EmojiGameScreen.this, "clicked1", 1);
        int clicked = refer.getSharedPreference(EmojiGameScreen.this, "clicked1", 0);
        Log.e("the value facebook like", String.valueOf(clicked));
        if (clicked > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.facebook_like_layout).setVisibility(View.GONE);
                }
            }, 2000);
        }
    }

    public void share_to_friends() {
        playClickSound();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, \n I Am Playing This Very Challenging \n Movie Quiz its Really AMAZING!\n Play For Free on   \n https://play.google.com/store/apps/details?id=" + getPackageName());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this site!");
        startActivity(Intent.createChooser(intent, "Share"));
        refer.setSharedPreference(EmojiGameScreen.this, "coin", refer.getSharedPreference(EmojiGameScreen.this, "coin", 30) + 10);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int coin_Value = (refer.getSharedPreference(EmojiGameScreen.this, "coin", 30));
                coin_update.setText("" + coin_Value);
            }
        }, 5000);
    }

    public Bitmap loadBitmapFromView() {

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.myLayout);
        layout.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void ask_To_Friend() {
        playClickSound();
        Bitmap bitmap = loadBitmapFromView();

        String myFormat = "dd-MM-yy-HH-mm-ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar myCalendar = Calendar.getInstance();
        String dateTime = sdf.format(myCalendar.getTime());


        File exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "FilmyQuiz", "");

        long freeBytesInternal = new File(this.getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
        long megAvailable = freeBytesInternal / 1048576;

        if (megAvailable < 0.1) {
            System.out.println("Please check" + megAvailable);
        } else {

            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            try {
                String fileNme = "report-" + dateTime + ".jpg";
                Log.e("fileName", fileNme);
                File file = new File(exportDir, fileNme);
                file.createNewFile();

                OutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();

                Uri bmpUri = Uri.parse(exportDir + "/" + fileNme);
                final Intent emailIntent1 = new Intent(Intent.ACTION_SEND);
                emailIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent1.putExtra(Intent.EXTRA_STREAM, bmpUri);
                emailIntent1.setType("image/png");
                emailIntent1.putExtra(Intent.EXTRA_TEXT, "Hey, \n I Stuck On This Question While Playing Movie Quiz \n Can you Please Help Me in Answer This Question Given Above?\n ::Download Filmy Quiz \nhttps://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(emailIntent1, "Share  To...."));


            } catch (Exception e) {
                Log.e("Image Exception", e.toString());
            }
        }

    }


    public void permission_Granted() {

        if (ContextCompat.checkSelfPermission(EmojiGameScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(Emoji_Game_Screen.this, "You Have Already Granted This Permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permiss  ion needed")
                    .setMessage("You need to allow permission to share questions to your friends else you can't!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(EmojiGameScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, storage_permission_code);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, storage_permission_code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == storage_permission_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
/*


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (playRightWrongSound != null) {
                playRightWro
                \ ngSound.stop();
                playRightWrongSound.reset();
                playRightWrongSound.release();
                playRightWrongSound = null;
            }
            if (buttonClickSound != null) {
                buttonClickSound.stop();
                buttonClickSound.reset();
                buttonClickSound.release();
                buttonClickSound = null;
            }
        } catch (Exception e) {

        }
    }
   */

