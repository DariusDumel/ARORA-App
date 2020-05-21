package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnClickListener {
    Context homeScreen;
    GestureDetector gestureDetector;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton ar_game_button;
    ImageButton quest_walking_button;
    ImageButton quest_breathing_button;
    ImageButton quest_meditation_button;
    ImageButton pop_up_twobuttons_button;
    TextView notification_tv;
    TextView label_ar_game_button;
    TextView label_quest_button;
    Boolean isButtonsPoppedUp;
    Animation notification_anim;
    Vibrator myVibrate;
    public LayoutInflater layoutInflater;
    public View speck1;
    ConstraintLayout speck_holder_cl;
    MediaPlayer ring;
    MediaPlayer spec_alert;
    MediaPlayer buttonClick;
    boolean page_left;
    ImageView profile_butterfly;
    View popup_quick_access;
    public View quick_menu;
    boolean is_menu_inflated;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        homeScreen = this;
        isButtonsPoppedUp = false;
        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        ar_game_button = (ImageButton) findViewById(R.id.ar_game_button);
        quest_walking_button = (ImageButton) findViewById(R.id.quest_walking_button);
        quest_breathing_button = (ImageButton) findViewById(R.id.quest_breathing_button);
        quest_meditation_button = (ImageButton) findViewById(R.id.quest_meditation_button);
        profile_butterfly = (ImageView) findViewById(R.id.user_butterfly_imageView);
        label_ar_game_button = (TextView) findViewById(R.id.label_ar_button);
        popup_quick_access = (LinearLayout) findViewById(R.id.popup_quick_access);
        speck_holder_cl = (ConstraintLayout) findViewById(R.id.speck_holder_cl);
        quick_menu = (LinearLayout) findViewById(R.id.include_popup_quick_access_menu);
        is_menu_inflated = false;



        quick_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreen.this, "Pollen Shop is underdevelopment", Toast.LENGTH_SHORT).show();
               /* ---------------- Not working, underdevelopment --------------
                Intent to_navigate = new Intent(homeScreen, PollenStoreDailyQuestPage.class);
                to_navigate.putExtra("NavigatedFrom", 1);
                startActivity(to_navigate);*/
            }
        });
        popup_quick_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                try {
                    if (buttonClick.isPlaying()) {
                        buttonClick.stop();
                        buttonClick.release();
                      buttonClick = MediaPlayer.create(getBaseContext(), R.raw.button1);
                    } buttonClick.start();
                } catch(Exception e) { e.printStackTrace(); }

                buttonClick = MediaPlayer.create(getBaseContext(), R.raw.button1);
                */

                /*ImageView popup_quick_acces_image = (ImageView) popup_quick_access.findViewById(R.id.pollen_score_layout_imageview);
                if(is_menu_inflated)
                {
                    popup_quick_access.findViewById(R.id.pollen_score_layout_tv).setVisibility(View.VISIBLE);

                    popup_quick_acces_image.setImageResource(R.drawable.half_pollen);
                    quick_menu.setVisibility(View.INVISIBLE);
                    is_menu_inflated = false;

                }
                else
                {
                    popup_quick_access.findViewById(R.id.pollen_score_layout_tv).setVisibility(View.INVISIBLE);
                    popup_quick_acces_image.setImageResource(R.drawable.pollen);
                    quick_menu.setVisibility(View.VISIBLE);
                    /*
                    View block1 = quick_menu.findViewById(R.id.quick_access_block_1);
                    TextView tv1 = block1.findViewById(R.id.text_view_description_quest_block);
                    tv1.setText("Mindfulness Breathing");
                    block1
                    View block2 = quick_menu.findViewById(R.id.quick_access_block_1);
                    View block3 = quick_menu.findViewById(R.id.quick_access_block_1);

                    is_menu_inflated = true;
                }
                /*
                ConstraintSet constraints = new ConstraintSet();
                LayoutInflater inflater =  LayoutInflater.from(homeScreen);
                quick_menu = inflater.inflate(R.layout.quick_access_menu, null);
                constraints.clone(speck_holder_cl);
                constraints.connect(quick_menu.getId(), ConstraintSet.LEFT, speck_holder_cl.getId(), ConstraintSet.LEFT, 600);
                constraints.connect(quick_menu.getId(), ConstraintSet.BOTTOM, speck_holder_cl.getId(), ConstraintSet.BOTTOM, 1000);
                constraints.applyTo(speck_holder_cl);
                */

            }
        });

        gestureDetector = new GestureDetector(homeScreen, HomeScreen.this);
        notification_tv = (TextView) findViewById(R.id.notification_body_homescreen);
        myVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        layoutInflater = LayoutInflater.from(homeScreen);
        speck1 = layoutInflater.inflate(R.layout.speck_notification, null);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        ar_game_button.setOnClickListener(this);
        quest_walking_button.setOnClickListener(this);
        quest_meditation_button.setOnClickListener(this);
        quest_breathing_button.setOnClickListener(this);
        notification_tv.setOnClickListener(this);
        speck1.setOnClickListener(this);
        notification_tv.setVisibility(View.INVISIBLE);
        ring = MediaPlayer.create(homeScreen,R.raw.notify_2);
        spec_alert = MediaPlayer.create(homeScreen,R.raw.notify_wav);

        //to stop music
        page_left = false;

        // Constraints to inflate random specks on layout

        /*
        new CountDownTimer(7000, 100) {
            ConstraintSet constraints = new ConstraintSet();
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                speck_holder_cl.addView(speck1, 0);
                final int random = new Random().nextInt(701) + 100;
                constraints.clone(speck_holder_cl);
                constraints.connect(speck1.getId(), ConstraintSet.LEFT, speck_holder_cl.getId(), ConstraintSet.LEFT, random);
                constraints.connect(speck1.getId(), ConstraintSet.BOTTOM, speck_holder_cl.getId(), ConstraintSet.BOTTOM, 1000);
                constraints.applyTo(speck_holder_cl);
                if(!page_left) {
                    spec_alert.start();
                }
            }
        }.start();
*/
        notification_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_reverse);

        notification_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notification_tv.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
/*
        pop_up_twobuttons_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {


                if(!isButtonsPoppedUp) {
                    pop_up_twobuttons_button.setImageDrawable(getResources().getDrawable(R.drawable.menu_button_unfilled));
                    quest_button.setVisibility(View.VISIBLE);
                    ar_game_button.setVisibility(View.VISIBLE);
                    label_ar_game_button.setVisibility(View.VISIBLE);
                    label_quest_button.setVisibility(View.VISIBLE);
                    isButtonsPoppedUp = true;
                    ar_game_button.setClickable(TRUE);
                    quest_button.setClickable(TRUE);
                }
                else{
                    pop_up_twobuttons_button.setImageDrawable(getResources().getDrawable(R.drawable.menu_button_filled));
                    quest_button.setVisibility(View.INVISIBLE);
                    ar_game_button.setVisibility(View.INVISIBLE);
                    label_ar_game_button.setVisibility(View.INVISIBLE);
                    label_quest_button.setVisibility(View.INVISIBLE);
                    isButtonsPoppedUp = false;
                    ar_game_button.setClickable(FALSE);
                    quest_button.setClickable(FALSE);
                }
            }
        });*/

        /*
        new CountDownTimer(10000, 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                notification_tv.startAnimation(notification_anim);

                if(!page_left) {
                    ring.start();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        myVibrate.vibrate(VibrationEffect.createOneShot(500, VibrationEfect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        myVibrate.vibrate(500);
                    }
                }

            }
        }.start();
        */

    }
    @Override
    public boolean onFling (MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y)
    {
        page_left = true;
        if (motionEvent1.getX() - motionEvent2.getX() > 150) {
            Intent profilePage = new Intent(homeScreen, MindfullnessSelection.class);
            startActivity(profilePage);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            return true;
        }
        if (motionEvent2.getX() - motionEvent1.getX() > 150) {
            Intent mindfullness = new Intent(homeScreen, ProfilePage.class);
            startActivity(mindfullness);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            return true;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    // We don't need to implement those unless otherwise told. They just need to be there
    // because we are implementing the GestureDetector class
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(ring.isPlaying()) {
            ring.stop();
        }
        if(spec_alert.isPlaying())
        {
            spec_alert.stop();
        }
        page_left = true;

        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(homeScreen, ProfilePage.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            to_navigate = new Intent(homeScreen, CommunityPage.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())

        {
            //to_navigate = new Intent(homeScreen, homeScreen);
        }
        else if (view_id == speck1.getId())
        {
            to_navigate = new Intent(homeScreen, CommunityPage.class);
            to_navigate.putExtra("notification", true);
            startActivity(to_navigate);
        }
        else if(view_id == notification_tv.getId())
        {
            to_navigate = new Intent(homeScreen, MindfullnessBreathing.class);
            startActivity(to_navigate);
        }
        else if(view_id == ar_game_button.getId())
        {
            to_navigate = new Intent(homeScreen, AR_Main.class);
            startActivity(to_navigate);
        }
        else if(view_id == quest_breathing_button.getId())
        {
            to_navigate = new Intent(homeScreen, MindfullnessBreathing.class);
            startActivity(to_navigate);
        }
        else if(view_id == quest_meditation_button.getId())
        {
            to_navigate = new Intent(homeScreen, MindfullnessMeditation.class);
            startActivity(to_navigate);
        }
        else if(view_id == quest_walking_button.getId())
        {
            to_navigate = new Intent(homeScreen, MindfullnessWalking.class);
            startActivity(to_navigate);
        }

    }
}
