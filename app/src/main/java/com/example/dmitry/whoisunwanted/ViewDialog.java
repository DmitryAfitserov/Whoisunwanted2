package com.example.dmitry.whoisunwanted;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Dmitry on 01.04.2017.
 */

public class ViewDialog {

    Utilities utilities;
    Typeface tf;
    CallBackDialogs callBackDialogs;

    public ViewDialog(Utilities utilities, Context context, CallBackDialogs callBackDialogs){
        this.utilities = utilities;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");
        this.callBackDialogs = callBackDialogs;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void showNextDialog(Activity activity, String msg, Drawable drawable){
        final Dialog dialog = new Dialog(activity, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setContentView(R.layout.dialog_next);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView description = (TextView)dialog.findViewById(R.id.text_description);
        description.setTypeface(tf);
        description.setText(msg);

        TextView luckYou = (TextView)dialog.findViewById(R.id.text_title);
        luckYou.setTypeface(tf);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 40 );
        shape.setStroke(40, activity.getResources().getColor(R.color.border));
        Button button;
        button = (Button)dialog.findViewById(R.id.btn_dialog);
        button.setTypeface(tf);

        Drawable buttonDraw = utilities.getDrawable("btn_next.png");
        Bitmap bitmap =  utilities.roundedCorners(buttonDraw, 1.5f);
        Drawable d = new BitmapDrawable(activity.getResources(), bitmap);
        button.setBackground(d);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callBackDialogs.callBackNextDialog();
            }
        });
        imageView.setBackground(shape);
        imageView.setImageDrawable(drawable);

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showExitDialog(Activity activity, int place){
        final Dialog dialog = new Dialog(activity, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text_exit = (TextView)dialog.findViewById(R.id.text_exit);
        text_exit.setTypeface(tf);

        TextView text_place = (TextView)dialog.findViewById(R.id.text_place);
        text_place.setText(activity.getResources()
                .getString(R.string.text_with_place, String.valueOf(place)));

        Button buttonOK;
        Button buttonCancel;
        buttonOK = (Button)dialog.findViewById(R.id.btn_ok);
        buttonOK.setTypeface(tf);

        buttonCancel = (Button)dialog.findViewById(R.id.btn_cancel);
        buttonCancel.setTypeface(tf);

        Drawable buttonDraw = utilities.getDrawable("btn_next.png");
        Bitmap bitmap =  utilities.roundedCorners(buttonDraw, 1.5f);
        Drawable d = new BitmapDrawable(activity.getResources(), bitmap);
        buttonOK.setBackground(d);
        buttonCancel.setBackground(d);

        final boolean[] isPressedTrue = new boolean[1];
        isPressedTrue[0] = false;

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPressedTrue[0] = true;
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPressedTrue[0] = false;
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callBackDialogs.callBackExitDialog(isPressedTrue[0]);
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showReturnDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_returt_lives);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text_if_you_dont_have = (TextView)dialog.findViewById(R.id.text_if_you_dont_have);
        text_if_you_dont_have.setTypeface(tf);

        TextView zero_lives_text = (TextView)dialog.findViewById(R.id.zero_lives_text);
        zero_lives_text.setTypeface(tf);

        Button buttonReturn;
        Button buttonClose;
        buttonReturn = (Button)dialog.findViewById(R.id.button_retunt_lives);
        buttonReturn.setTypeface(tf);

        buttonClose = (Button)dialog.findViewById(R.id.button_close);
        buttonClose.setTypeface(tf);

        Drawable buttonDraw = utilities.getDrawable("btn_next.png");
        Bitmap bitmap =  utilities.roundedCorners(buttonDraw, 1.5f);
        Drawable d = new BitmapDrawable(activity.getResources(), bitmap);
        buttonClose.setBackground(d);
        buttonReturn.setBackground(d);

        final boolean[] isPressedTrue = new boolean[1];
        isPressedTrue[0] = false;
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "не наботает", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showResultDialog(Activity activity, int score){
        final Dialog dialog = new Dialog(activity, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_result);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text_with_result = (TextView)dialog.findViewById(R.id.text_with_result);
        text_with_result.setText(activity.getResources()
                .getString(R.string.text_with_place_result, String.valueOf(score)));

        Button buttonCancel = (Button)dialog.findViewById(R.id.button_cancel);
        buttonCancel.setTypeface(tf);

        Drawable buttonDraw = utilities.getDrawable("btn_next.png");
        Bitmap bitmap =  utilities.roundedCorners(buttonDraw, 1.5f);
        Drawable d = new BitmapDrawable(activity.getResources(), bitmap);

        buttonCancel.setBackground(d);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
