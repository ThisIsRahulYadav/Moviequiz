package com.rauniyargames.moviequiz.gameclasses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.rauniyargames.moviequiz.helperclasses.ReferenceWrapper;

import moviequiz.rauniyargames.com.moviequiz.R;

public class HomeScreen extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public Activity activity;
    private View setting_popup;
    private ReferenceWrapper refer;
    private int storage_permission_code = 1;
    private MediaPlayer buttonClickSound;
    boolean isPopUp = false;
    private Button hollyButton,bollyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        addOnClickListener();
        refer = ReferenceWrapper.getReferenceWrapper(this);

        hollyButton.getPaint().setShader(textColor());
        bollyButton.getPaint().setShader(textColor());
    }

    @Override
    protected void onResume() {
        super.onResume();
        permission_Granted();
        heading();
              Switch sound_switch = (Switch) findViewById(R.id.sound_switch);
        Switch music_switch = (Switch) findViewById(R.id.music_switch);

        sound_switch.setOnCheckedChangeListener(HomeScreen.this);
        music_switch.setOnCheckedChangeListener(HomeScreen.this);

        int sound = refer.getSharedPreference(this, "music", 1);

        Log.e("sound at resume", "" + sound);

        if (sound > 0) {
            music_switch.setChecked(true);

            refer.startSound(HomeScreen.this);
        } else {
            music_switch.setChecked(false);
            refer.stopSound(HomeScreen.this);
        }


        int clickSound = refer.getSharedPreference(this, "sound", 1);
        //  buttonClickSound = MediaPlayer.create(this, R.raw.on_click_sound);
        if (clickSound > 0) {
            refer.setSharedPreference(this, "sound", 1);
            sound_switch.setChecked(true);

        } else {
            sound_switch.setChecked(false);

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
                }
            } catch (Exception ex) {

            }
            buttonClickSound = null;

            buttonClickSound = MediaPlayer.create(this, R.raw.on_click_sound);
            buttonClickSound.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        int sound = refer.getSharedPreference(HomeScreen.this, "music", 1);
        Log.e("sound at pause", "" + sound);
        if (sound > 0) {

            refer.stopSound(HomeScreen.this);
        }
    }


    public void addOnClickListener() {
        hollyButton = (Button) findViewById(R.id.holly);
        bollyButton = (Button) findViewById(R.id.bolly);
        Button setting = (Button) findViewById(R.id.setting);
        setting_popup = findViewById(R.id.setting_popup);
        Button cross = (Button) findViewById(R.id.crosss);
        Button shareFrnd = (Button) findViewById(R.id.share);
        Button rateUS = (Button) findViewById(R.id.rate_us);
        Button facebook = (Button) findViewById(R.id.facebook_like);
        Button help = (Button) findViewById(R.id.help);
        Button helpCross = (Button) findViewById(R.id.helpCross);

        hollyButton.setOnClickListener(listener);
        bollyButton.setOnClickListener(listener);
        setting.setOnClickListener(listener);
        cross.setOnClickListener(listener);
        shareFrnd.setOnClickListener(listener);
        rateUS.setOnClickListener(listener);
        facebook.setOnClickListener(listener);
        help.setOnClickListener(listener);
        helpCross.setOnClickListener(listener);

    }

    Boolean isExist = false;

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        if (isPopUp) {
            setting_popup.setVisibility(View.GONE);
            isPopUp = false;
            return;
        }
        if (!isExist) {
            Toast.makeText(HomeScreen.this, String.valueOf("Do You Really Want To Exit"), Toast.LENGTH_LONG).show();
            isExist = true;
        } else {
            HomeScreen.this.finish();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isExist = false;
            }
        }, 4000);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playClickSound();
            switch (v.getId()) {
                case R.id.holly: {
                    if (isPopUp) break;
                    Intent myintent = new Intent(HomeScreen.this, Levels.class);
                    myintent.putExtra("CATEGORY", "holly");
                    startActivity(myintent);
                    //sound();
                }
                break;
                case R.id.bolly: {
                    if (isPopUp) break;
                    Intent myintent = new Intent(HomeScreen.this, Levels.class);
                    myintent.putExtra("CATEGORY", "bolly");
                    startActivity(myintent);
                    // sound();
                }
                break;
                case R.id.setting: {
                    setting_popup.setVisibility(View.VISIBLE);
                    isPopUp = true;
                    // sound();
                }
                break;
                case R.id.crosss: {
                    setting_popup.setVisibility(View.GONE);
                    isPopUp = false;
                    // sound();
                }
                break;
                case R.id.facebook_like: {
                    setting_popup.setVisibility(View.GONE);
                    isPopUp = false;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/goodwilltechnologyservices/"));
                    startActivity(intent);
                    //sound();
                }
                break;
                case R.id.share: {
                    setting_popup.setVisibility(View.GONE);
                    isPopUp = false;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey, \n I Am Playing This Very Challenging \n 3 in 1 Filmy Quiz its Really AMAZING!\n Play For Free on   \n https://play.google.com/store/apps/details?id=" + getPackageName());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this site!");
                    startActivity(Intent.createChooser(intent, "Share"));
                    //    sound();
                }
                break;
                case R.id.help: {
                    isPopUp = false;
                    setting_popup.setVisibility(View.GONE);
                    //               setting_popup.setVisibility(View.GONE);
                    View help_popup = (View) findViewById(R.id.help_popup);
                    help_popup.setVisibility(View.VISIBLE);

                    WebView view = (WebView) findViewById(R.id.emoji_view);
                    String text;
                    text = "<html><body style='background-color:#1F73CE;opacity:.95;'><p style='text-align:justify;color:#ff0;'>";
                    text += "In this Emoji's category, you will get the different Emojis. From these emojis you have to identify correct movie name and you have to type all the characters which comes in that movie name one by one from the keyboard given below. For this you will get 30 points for each correct answer and 10 points will be deducted for each wrong answer";
                    text += "</p></body></html>";
                    view.loadData(text,
                            "text/html", "utf-8");
                    //      sound();
                }
                break;
                case R.id.helpCross: {
                    isPopUp = false;
                    View help_popup = (View) findViewById(R.id.help_popup);
                    help_popup.setVisibility(View.GONE);
                    //          sound();
                }
                break;
                case R.id.rate_us: {
                    isPopUp = false;
                    setting_popup.setVisibility(View.GONE);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(intent);
                    //        sound();
                }
                break;
            }
        }
    };

    private Shader textColor(){
        Shader textShader=new LinearGradient(0, 0, 0, 60,
                new int[]{Color.parseColor("#b402ae"),Color.parseColor("#FFAEABAB")},
                new float[]{0, 90}, Shader.TileMode.CLAMP);

     return textShader ;
    }

    private void heading() {
        TextView tx = (TextView) findViewById(R.id.heading);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SYENCILED.otf");

        tx.setTypeface(custom_font);
    }

    public void permission_Granted() {

        if (!(ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.sound_switch) {
            if (isChecked) {
                refer.setSharedPreference(HomeScreen.this, "sound", 1);

            } else {
                refer.setSharedPreference(HomeScreen.this, "sound", 0);

            }
        }

        if (buttonView.getId() == R.id.music_switch) {
            Log.e("Switch State=", "" + isChecked);
            if (isChecked) {
                refer.setSharedPreference(HomeScreen.this, "music", 1);
                refer.startSound(HomeScreen.this);
            } else {
                refer.setSharedPreference(HomeScreen.this, "music", 0);
                refer.stopSound(HomeScreen.this);
                //game_bg = null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
        System.gc();


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
