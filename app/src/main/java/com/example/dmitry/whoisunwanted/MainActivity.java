package com.example.dmitry.whoisunwanted;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dmitry.whoisunwanted.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackDialogs{

    ActivityMainBinding binding;
    List<ImageView> listImageView = new ArrayList<>();
    float scale = 0f;
    int numberQuestion;
    String[] answer;
    Answers answers;
    Utilities utilities;
    boolean isShowingExitDialog = false;
    ViewDialog viewDialog;
    private final String KEY_SCORE = "key_score";
    private final String KEY_SCALE = "key_scale";
    private final int CALL_BROWSER = 3;
    private boolean[] isPressedImage = new boolean[4];
    MySharedPreferences preferences;
    int countLostLives = 0;
    int score;
    int lives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
       // binding.setIsLoaded(false);
        listImageView.add(binding.imageView1);
        listImageView.add(binding.imageView2);
        listImageView.add(binding.imageView3);
        listImageView.add(binding.imageView4);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        binding.text.setTypeface(tf);

        preferences = new MySharedPreferences(getApplicationContext());
        scale = preferences.getFloat(KEY_SCALE);
        score = preferences.getInt(KEY_SCORE);
        lives = preferences.getIntForLives();
        binding.setCountLives(lives);
        binding.setCountScore(score);
        Log.d("EEE", "fd" + score);

        answers = new Answers();
        utilities = new Utilities(getApplicationContext());
        viewDialog = new ViewDialog(utilities, getApplicationContext(), this);

        createButtonRating();
        createListener();
        startNewGame();
    }

    private void startNewGame(){
        Arrays.fill(isPressedImage, Boolean.FALSE);
        binding.setIsTrueAnswer(true);
        int temp;
            while(true){
                temp = 1 + (int)(Math.random() * answers.getLengt() -1);
                if(numberQuestion != temp){
                    numberQuestion = temp;
                    break;
                }
            }
        answer = answers.getAnswers(numberQuestion);

        fillImageView();
    }


    private void fillImageView(){
        for(int i = 0; i < listImageView.size() ; i++){
            int posImage = i + 1;
            Drawable drawable = utilities.getDrawable("image_game/" + numberQuestion + "." + posImage + ".jpg");
            if(scale == 0f){
                listImageView.get(i).setScaleType(ImageView.ScaleType.FIT_CENTER);
                listImageView.get(i).setImageDrawable(drawable);
                return;
            }
            listImageView.get(i).setScaleType(ImageView.ScaleType.FIT_CENTER);
            listImageView.get(i).setImageBitmap(utilities.roundedCorners(drawable, scale));
        }
    }

     private void createListener(){
         for(int i = 0; i < listImageView.size(); i++ ){
             final int finalI = i;
             listImageView.get(i).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     pressedImage(finalI + 1);
                 }
             });
         }
     }

     private void pressedImage(int numberPressedImage){
        if(lives != 0){
             if(answer[0].equals(String.valueOf(numberPressedImage))){
                 controlStatistics(true);
                 viewDialog.showNextDialog(this,
                         answer[1], listImageView.get(numberPressedImage - 1).getDrawable());
             } else {
                 if(!isPressedImage[numberPressedImage - 1]){
                     isPressedImage[numberPressedImage - 1] = true;
                     controlStatistics(false);
                     binding.setIsTrueAnswer(false);
                     Drawable drawableOverlay = utilities.getDrawable("icon_false.png");
                     Bitmap bitmapOverlay = ((BitmapDrawable)drawableOverlay).getBitmap();

                     Bitmap bitmap = utilities.blurRenderAndOverlay(getApplicationContext(),
                             ((BitmapDrawable)listImageView.get(numberPressedImage - 1)
                                     .getDrawable()).getBitmap(),
                             bitmapOverlay);
                     listImageView.get(numberPressedImage - 1).setImageBitmap(bitmap);
                     countLostLives ++;
                     if(countLostLives == CALL_BROWSER){
                         countLostLives = 0;
                         Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("https://www.adriver.ru/doc/ban/examples/examples_854.html"));
                         startActivity(browserIntent);
                     }
                 }
             }
        } else viewDialog.showReturnDialog(this);
     }

    private void controlStatistics(boolean isTrueAnswer){
        if(isTrueAnswer){
            score += 150;
        } else {
            if(score > 50){
                score -= 50;
            }
            if(lives > 0){
                lives -= 1;
            }
        }
        binding.setCountScore(score);
        binding.setCountLives(lives);
    }

    private void createButtonRating(){
        LinearLayout linearLayout = binding.layoutRating;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog.showResultDialog(MainActivity.this, score);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(scale == 0f){
            scale = binding.imageView1.getWidth() / 300f;
            fillImageView();
            preferences.putFloat(scale, KEY_SCALE);
        }

    }

    @Override
    public void onBackPressed() {
        if(!isShowingExitDialog){
            viewDialog.showExitDialog(this, score);
            isShowingExitDialog = true;
        }
    }

    @Override
    public void callBackNextDialog() {
        startNewGame();
    }

    @Override
    public void callBackExitDialog(boolean isExit) {
        if(isExit){
            finish();
        } else {
            isShowingExitDialog = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.putInt(score, KEY_SCORE);
        preferences.putIntForLives(lives);
    }


}
