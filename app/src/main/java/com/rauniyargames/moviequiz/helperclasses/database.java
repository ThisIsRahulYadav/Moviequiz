package com.rauniyargames.moviequiz.helperclasses;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lenovo on 3/28/2018.
 */

public class database extends AppCompatActivity {
    public static String getLine(int linenumber, Activity activity, String filename) {
        BufferedReader line = null;
        int count = 1;
        try {
            line = new BufferedReader(new InputStreamReader(activity.getAssets().open(filename)));
            String read = null;
            while ((read = line.readLine()) != null) {
                if (count == linenumber) {
                    return read;
                } else {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Questions readFromdatabaseEmoji(int linenumber, Activity activity, String filename) {
        String read = getLine(linenumber, activity, filename);
        if (read != null) {

            Questions question =new Questions();
            String splited[]=read.split(";");
            question.setQuestion(splited[2].trim());
            question.setAnswer(splited[1].trim());
            question.setHint(splited[3].trim());
            question.setEmojiNAme(splited[0].trim());
            return question;
        }
    return null;
    }


  /*  public static Questions readFromDatabaseQuotes(int lineNumber, Activity activity, String filename) {

        String read = getLine(lineNumber, activity, filename);
        if (read != null) {
            Questions question = new Questions();
            String splited[] = read.split(";");
            question.setDisplay(splited[0].trim());
            question.setOpA(splited[1].trim());
            question.setOpB(splited[2].trim());
            question.setOpC(splited[3].trim());
            question.setOpD(splited[4].trim());
            question.setAnswer(splited[5].trim());
            question.setHint1(splited[6].trim());
            question.setQuestion(splited[7].trim());

            return question;
        }
        return null;
    }
*/


}
