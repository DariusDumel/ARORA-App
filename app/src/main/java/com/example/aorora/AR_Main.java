package com.example.aorora;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import com.google.ar.core.Session;
import com.google.ar.sceneform.animation.AnimationEngine;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AR_Main extends AppCompatActivity  {
    final Handler handler = new Handler();
    private ModelAnimator modelAnimator;
    private int i;
    private Session session;
    private ArFragment arFragment;
    private Scene scene;
    private Camera camera;
    private int butterfliesLeft = 0;
    private Point point;
    private TextView butterfliesLeftTxt,questText,messageText;
    private SoundPool soundPool;
    private int sound;
    private boolean messageToggle,questToggle;
    ObjectAnimator questSlider,messagessSlider,inventorySlider;
    Button Messages,Inventory,Quests;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        Display display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getRealSize(point);
        setContentView(R.layout.activity_ar__main);

        Intent intent = new Intent(this, ButterflyMenu.class);

        messageToggle = true;
        Messages = findViewById(R.id.Message);
        Messages.setOnClickListener((View v) -> {
            slideMessages();

        });
        questToggle = true;
        Quests = findViewById(R.id.Quests);
        Quests.setOnClickListener((View v) ->
        {
            slideQuest();
        });

        Inventory = findViewById(R.id.Inventory);
        Inventory.setOnClickListener((View v) ->
        {
            startActivity(intent);
        });
        //loadSounds();

        messageText = findViewById(R.id.messageText);
        messageText.setSelected(false);

        questText = findViewById(R.id.questText);
        questText.setSelected(false);


        butterfliesLeftTxt = findViewById(R.id.butterflyCntTxt);
        CustomArFragment arFragment =
                (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);


        scene = arFragment.getArSceneView().getScene();
        camera = scene.getCamera();
        addButterflies();
    }

    public void questText()
    {
        if (questText.getVisibility() == TextView.VISIBLE)
        {
            questText.setVisibility(TextView.GONE);
            questText.setSelected(false);
        }
        else
        {
            questText.setVisibility(TextView.VISIBLE);
            questText.setSelected(true);
        }
    }


    Handler handler_butterfly = new Handler();
    Runnable runnable;
    int delay = 5000; //Delay for 15 seconds.  One second = 1000 milliseconds.
    protected void onResume(ModelRenderable renderable) {
        //start handler as activity become visible
        handler_butterfly.postDelayed( runnable = new Runnable() {
            public void run() {
                animateModel(renderable);
                handler_butterfly.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }


    @Override
    protected void onPause() {
        handler_butterfly.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    public void slideMessages()
    {
        if(messageToggle == true)
        {
            handler.postDelayed(new Runnable() {
                public void run() {
                    messageText();
                }
            }, 500);
            messagessSlider = ObjectAnimator.ofFloat(Messages, "translationX", 0f, 420f);
            messagessSlider.setDuration(500);
            messagessSlider.start();
            messageToggle = false;
        }

        else
        {
            messagessSlider = ObjectAnimator.ofFloat(Messages,"translationX",420f,0f);
            messagessSlider.setDuration(500);
            messagessSlider.start();
            messageToggle = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    messageText();
                }
            }, 50);
        }
    }

    public void messageText()
    {
        if (messageText.getVisibility() == TextView.VISIBLE)
        {
            messageText.setVisibility(TextView.GONE);
            messageText.setSelected(false);
        }
        else
        {
            messageText.setVisibility(TextView.VISIBLE);
            messageText.setSelected(true);
            messageText.setVisibility(TextView.GONE);
            messageText.setSelected(false);
            messageText.setVisibility(TextView.VISIBLE);
            messageText.setSelected(true);
        }
    }

    public void slideInventory()
    {
        inventorySlider = ObjectAnimator.ofFloat(Inventory,"translationX",0f,100f);
        inventorySlider.setDuration(2000);
        inventorySlider.start();

    }

    public void slideQuest()
    {
        if (questToggle == true)
        {
            questSlider = ObjectAnimator.ofFloat(Quests,"translationX",0f,420f);
            questSlider.setDuration(500);
            questSlider.start();
            questToggle = false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questText();
                }
            }, 500);
        }
        else
        {
            questSlider = ObjectAnimator.ofFloat(Quests,"translationX",420f,0f);
            questSlider.setDuration(500);
            questSlider.start();
            questToggle = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questText();
                }
            }, 50);
        }
    }

    public void update()
    {

    }


    private void addButterflies() {

        ModelRenderable
                .builder()
                .setSource(this, Uri.parse("animButterfly_noMask01.sfb"))
                .build()
                .thenAccept(renderable -> {

                    for (int i = 0;i < 5;i++) {
                        butterfliesLeft++;
                        butterfliesLeftTxt.setText("Butterflies Left: " + butterfliesLeft);
                        Node node = new Node();
                        node.setRenderable(renderable);
                        scene.addChild(node);
                        node.setOnTapListener((v, event)-> {
                            butterfliesLeft--;
                            butterfliesLeftTxt.setText("Butterflies Left: " + butterfliesLeft);
                            scene.removeChild(node);
                        });


                        Random random = new Random();
                        int x = random.nextInt(20+1)-5;
                        int z = random.nextInt(10);
                        int y = random.nextInt(5+1)-10;

                        z = -z;

                        node.setWorldPosition(new Vector3(
                                0.01f,
                                0.01f,
                                (float) z
                        ));


                    }
                    animateModel(renderable);
                });

    }

    private void animateModel(ModelRenderable renderable)
    {
        if(modelAnimator != null && modelAnimator.isRunning())
            modelAnimator.end();
        int animationCount = renderable.getAnimationDataCount();

        if(i== animationCount)
        {
            i = 0;
        }

        AnimationData animationData = renderable.getAnimationData(i);
        modelAnimator = new ModelAnimator(animationData, renderable);
        modelAnimator.start();
        onResume(renderable);

    }
}
