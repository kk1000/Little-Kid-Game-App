package com.example.android.animation_assignment4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView arrowUp;
    private ImageView arrowDown;
    private ImageView arrowLeft;
    private ImageView arrowRight;

    private ImageView blank_1;
    private ImageView blank_2;
    private ImageView blank_3;
    private ImageView blank_4;
    private ImageView blank_5;

    private Intent intent;
    private boolean serviceOn;

    private Animation animation;
    private ImageView ballView;
    private ImageView backgroundView;

    private LinearLayout options;

    private int mainLevel;

    private MyCountDownTimer mc;
    private TextView time_tv;
    private long timeLeft;

    private int stage;


    private DateBaseHelper mydb;
    private String username;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb=new DateBaseHelper(this);
        Intent myRcv=getIntent();
        username=myRcv.getStringExtra("username");

//        intent = new Intent("com.example.Android.MUSIC");
        intent = new Intent(this, MusicServer.class);
        startService(intent);
        serviceOn = true;

        sharedPreferences=getApplication().getSharedPreferences(username, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        arrowUp = (ImageView) findViewById(R.id.option_up);
        arrowUp.setOnTouchListener(new ArrowTouchListner());

        arrowDown = (ImageView) findViewById(R.id.option_down);
        arrowDown.setOnTouchListener(new ArrowTouchListner());

        arrowLeft = (ImageView) findViewById(R.id.option_left);
        arrowLeft.setOnTouchListener(new ArrowTouchListner());

        arrowRight = (ImageView) findViewById(R.id.option_right);
        arrowRight.setOnTouchListener(new ArrowTouchListner());

        blank_1 = (ImageView) findViewById(R.id.direction_1);
        blank_1.setOnDragListener(new BlankDragListener());

        blank_2 = (ImageView) findViewById(R.id.direction_2);
        blank_2.setOnDragListener(new BlankDragListener());

        blank_3 = (ImageView) findViewById(R.id.direction_3);
        blank_3.setOnDragListener(new BlankDragListener());

        Drawable blankbox = getResources().getDrawable(R.drawable.choice);
        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pixels, pixels);
        blank_4 = new ImageView(this);
        blank_4.setLayoutParams(params);
        blank_4.setBackground(blankbox);
        blank_4.setOnDragListener(new BlankDragListener());

        blank_5 = new ImageView(this);
        blank_5.setLayoutParams(params);
        blank_5.setBackground(blankbox);
        blank_5.setOnDragListener(new BlankDragListener());

        ballView = (ImageView) findViewById(R.id.ball);
        backgroundView = (ImageView) findViewById(R.id.pic);
        options = (LinearLayout) findViewById(R.id.options);
        mainLevel = 1;
//        ballView.setX(0);
//        ballView.setY(150);

        time_tv=(TextView)findViewById(R.id.time_tv);
        mc = new MyCountDownTimer(90000, 1000);//90 seconds
        mc.start();
        stage=0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(intent);
        serviceOn = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startService(intent);
        serviceOn = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((serviceOn)) {
            startService(intent);
        } else {
            stopService(intent);
        }
        switch (mainLevel) {
            case 1:
                Drawable stage1 = getResources().getDrawable(R.drawable.stage1);
                backgroundView.setBackground(stage1);
                break;
            case 2:
                options.addView(blank_4);
                Drawable stage2 = getResources().getDrawable(R.drawable.stage2);
                backgroundView.setBackground(stage2);//SET LEVEL 2 BACKGROUND
                break;
            case 3:
                options.addView(blank_5);
                Drawable stage3 = getResources().getDrawable(R.drawable.stage3);
                backgroundView.setBackground(stage3);//SET LEVEL 3 BACKGROUND
                break;
            default:
                break;

        }
    }

    public void redo(View v) {
        emptyChoice();
    }

    public void emptyChoice() {
        Drawable background = getResources().getDrawable(R.drawable.choice);

        blank_1.setTag(null);
        blank_1.setBackground(background);

        blank_2.setTag(null);
        blank_2.setBackground(background);

        blank_3.setTag(null);
        blank_3.setBackground(background);
        switch (mainLevel) {
            case 1:
                break;
            case 2:
                blank_4.setTag(null);
                blank_4.setBackground(background);
                break;
            case 3:
                blank_4.setTag(null);
                blank_4.setBackground(background);
                blank_5.setTag(null);
                blank_5.setBackground(background);
                break;
            default:
                break;
        }
    }

    public void music(View v) {
        if (serviceOn) {
            stopService(intent);
            serviceOn = false;
        } else {
            startService(intent);
            serviceOn = true;
        }
    }

    public void start(View v) {
        game();
    }
    public void game() {
        if (blank_1.getTag() == null || blank_2.getTag() == null || blank_3.getTag() == null) {
            dropArrowDialog();
            return;
        }
        int a = (int) blank_1.getTag();
        int b = (int) blank_2.getTag();
        int c = (int) blank_3.getTag();
        playLogfile(mainLevel);
        switch (mainLevel) {
            case 1:
                if (a == arrowRight.getId() &&
                        b == arrowDown.getId() &&
                        c == arrowRight.getId()) {
                    mc.cancel();
                    calcStage();
                    animation = AnimationUtils.loadAnimation(this, R.anim.stage1);
                    ballView.startAnimation(animation);
                   setMyAnimationListener();
                    boolean result=mydb.updateByUsername(username,mainLevel,stage);
                    Log.v("CCR","DB Update "+result);

                } else {
                    showLoseDialog();
                }
                break;
            case 2:
                if (blank_4.getTag() == null) {
                    dropArrowDialog();
                    return;
                }
                int d = (int) blank_4.getTag();
                if (a == arrowRight.getId() &&
                        b == arrowDown.getId() &&
                        c == arrowRight.getId() &&
                        d == arrowDown.getId()) {
                    mc.cancel();
                    calcStage();
                    animation = AnimationUtils.loadAnimation(this, R.anim.stage2);
                    ballView.startAnimation(animation);
                    setMyAnimationListener();
                    boolean result=mydb.updateByUsername(username,mainLevel,stage);
                    Log.v("CCR","DB Update "+result);

                } else {
                    showLoseDialog();
                }
                break;
            case 3:
                if (blank_4.getTag() == null || blank_5.getTag() == null) {
                    dropArrowDialog();
                    return;
                }
                int d3 = (int) blank_4.getTag();
                int e = (int) blank_5.getTag();
                if (a == arrowRight.getId() &&
                        b == arrowDown.getId() &&
                        c == arrowRight.getId() &&
                        d3 == arrowDown.getId() &&
                        e == arrowLeft.getId()) {
                    mc.cancel();
                    calcStage();
                    animation = AnimationUtils.loadAnimation(this, R.anim.stage3);
                    ballView.startAnimation(animation);
                   setMyAnimationListener();
                    boolean result=mydb.updateByUsername(username,mainLevel,stage);
                    Log.v("CCR","DB Update "+result);

                } else {
                    showLoseDialog();
                }
                break;
            default:
                break;
        }

    }

    public void setMyAnimationListener(){
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showWinDialog();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //The more time is left, the higher stage is.
    public void calcStage(){
        if (timeLeft<=30){
            stage=1; //1 star for stage 1
        }else{
            if (timeLeft<=60){
                stage=2;//2 stars for stage 2
            }
            else stage=3; //3 stars for stage 3
        }
    }

    public void showWinDialog() {
        AlertDialog.Builder passDia;
        passDia = new AlertDialog.Builder(this).setTitle("Congratulation!");

        switch (mainLevel) {
            case 1:
                passDia.setMessage("You win level " + mainLevel + "! "+"You got "+stage+" stars on this level!")
                        .setNegativeButton("Re-Play", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                emptyChoice();
                                mc.start();
                            }
                        });
                passDia.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                emptyChoice();
                                mainLevel++;
                                options.addView(blank_4);
                                Drawable stage2 = getResources().getDrawable(R.drawable.stage2);
                                backgroundView.setBackground(stage2);
                                mc.start();
                            }
                        }
                );
                passDia.show();
                break;
            case 2:
                passDia.setMessage("You win level " + mainLevel + "! "+"You got "+stage+" stars on this level!")
                        .setNegativeButton("Re-Play", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                emptyChoice();
                                mc.start();
                            }
                        });
                passDia.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                emptyChoice();
                                mainLevel++;
                                options.addView(blank_5);
                                Drawable stage3 = getResources().getDrawable(R.drawable.stage3);
                                backgroundView.setBackground(stage3);
                                mc.start();
                            }
                        }
                );
                passDia.show();
                break;
            case 3:
                passDia.setMessage("You win all levels! "+"You got "+stage+" stars on this level!");
                passDia.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        stopService(intent);
                        serviceOn=false;
                        MainActivity.this.finishAffinity();
                    }
                });
                passDia.setPositiveButton("Replay From Begin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        emptyChoice();
                        mainLevel = 1;
                        options.removeView(blank_4);
                        options.removeView(blank_5);
                        Drawable stage1 = getResources().getDrawable(R.drawable.stage1);
                        backgroundView.setBackground(stage1);
                        mc.start();
                    }
                });
                passDia.setNegativeButton("Replay This Level", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        emptyChoice();
                        mc.start();
                    }
                });
                passDia.show();
                break;
            default:
                break;
        }
    }

    public void showLoseDialog() {
        AlertDialog.Builder loseDia;
        loseDia = new AlertDialog.Builder(this).setTitle("Sorry!");
        loseDia.setMessage("It's wrong. Please retry!");
        loseDia.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                emptyChoice();
                mc.start();
            }
        });
        loseDia.show();
    }

    public void showTimeOutDialog(){
        AlertDialog.Builder timeOutDia;
        timeOutDia=new AlertDialog.Builder(this).setTitle("Time Out!");
        timeOutDia.setMessage("Time Out. Please retry!");
        timeOutDia.setPositiveButton("OK.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();;
                emptyChoice();
                mc.start();
            }
        });
        timeOutDia.show();
    }

    public void dropArrowDialog() {
        AlertDialog.Builder dia;
        dia = new AlertDialog.Builder(this).setTitle("Empty!");
        dia.setMessage("Fill drag and drop arrows!");
        dia.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dia.show();
    }

    public void exit(View view) {
        stopService(intent);
        serviceOn=false;
        this.finishAffinity();
    }

    public void playLogfile(int level){
        Date date=new Date();
        editor.putString(date.toString(), " playing game: Level "+level+"\n");
        editor.commit();
    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("MainActivity", millisUntilFinished + "");
            time_tv.setText(millisUntilFinished/1000+" s left...");
            timeLeft=millisUntilFinished/1000;
        }

        @Override
        public void onFinish() {
            showTimeOutDialog();
        }
    }
}
