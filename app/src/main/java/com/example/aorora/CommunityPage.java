package com.example.aorora;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aorora.R;
import com.example.aorora.adapter.CustomAdapter;
import com.example.aorora.adapter.GridViewAdapter;
import com.example.aorora.interfaces.OnItemClickListener;
import com.example.aorora.model.Quest;
import com.example.aorora.model.QuestReport;
import com.example.aorora.model.RetroPhoto;
import com.example.aorora.model.UserInfo;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class CommunityPage extends AppCompatActivity implements View.OnClickListener {

    private com.example.aorora.adapter.CustomAdapter linearAdapter;
    private com.example.aorora.adapter.GridViewAdapter gridAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    Button friends_tab_button;
    Button notifications_tab_button;
    Context communityPage;
    GestureDetector gestureDetector;
    ImageView friends_underline;
    ImageView notifications_underline;
    TextView community_page_title_tv;
    LinearLayout tabs_ll;
    LinearLayout bar_ll;
    LinearLayout popup_menu_button;
    boolean is_menu_popped;
    public View popup_menu;
    GetDataService service;
    //TEMPORARY VARIABLE
    public HolderCommunityPage holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);

        recyclerView = findViewById(R.id.customRecyclerView);
        communityPage = this;
        is_menu_popped = false;
        progressDoalog = new ProgressDialog(communityPage);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        holder = new HolderCommunityPage();
        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        community_button_bottombar.setImageResource(R.drawable.community_button_filled);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);

        popup_menu = (View) findViewById(R.id.include_popup_quick_access_menu_community_page);
        popup_menu_button = (LinearLayout) findViewById(R.id.popup_quick_access_community_page);

        popup_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(is_menu_popped);
            }
        });

        friends_tab_button =  (Button) findViewById(R.id.friends_tabs_button);
        notifications_tab_button = (Button) findViewById(R.id.notifications_tabs_button);

        community_page_title_tv = (TextView) findViewById(R.id.community_string_tv);
        tabs_ll = (LinearLayout) findViewById(R.id.community_page_ll);
        bar_ll = (LinearLayout) findViewById(R.id.community_page_line_ll);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);

        friends_underline = findViewById(R.id.underline_friends);
        notifications_underline = findViewById(R.id.underline_notifications);

        gestureDetector = new GestureDetector(this, new MyGestureListener());

        recyclerView.setOnTouchListener(touchListener);

        popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_navigate = new Intent(communityPage, PollenStoreDailyQuestPage.class);
                to_navigate.putExtra("NavigatedFrom", 1);
                startActivity(to_navigate);
            }
        });

        /*Create handle for the RetrofitInstance interface*/

        friends_tab_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {

                friends_underline.setVisibility(View.VISIBLE);
                notifications_underline.setVisibility(View.INVISIBLE);
                progressDoalog.setMessage("Friends Loading....");
                progressDoalog.show();

                Call<List<UserInfo>> call = service.getCommunity();
                call.enqueue(new Callback<List<UserInfo>>() {
                    @Override
                    public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                        if(response.isSuccess())//response.body().getUsername()
                            {
                                List<UserInfo> users = response.body();
                                generateDataListGrid(users);
                            }
                            else
                            {
                                Toast.makeText(communityPage, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                    }
                    @Override
                    public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                        Toast.makeText(communityPage, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR CAUSE", "" + t.getMessage() +  " Cause  " + t.getCause());
                    }
                });
            }
        });

        notifications_tab_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {

                friends_underline.setVisibility(View.INVISIBLE);
                notifications_underline.setVisibility(View.VISIBLE);

                progressDoalog.setMessage("Notifications Loading....");
                progressDoalog.show();

                setNotifications();
            }
        });

        if(getIntent().hasExtra("notification"))
        {
            notifications_tab_button.performClick();

        }
        else
        {
            //friends_tab_button.performClick();
            notifications_tab_button.performClick();
        }
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return gestureDetector.onTouchEvent(event);

        }
    };

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataListLinear(List<QuestReport> questList,
                                        List<Integer> quest_type_ids,
                                        List<String> usernames,
                                        List<Integer> user_butterfly_types)
    {
        linearAdapter = new com.example.aorora.adapter.CustomAdapter(this,questList,quest_type_ids,usernames, user_butterfly_types, getResources().getStringArray(R.array.mindfulness_description));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CommunityPage.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(linearAdapter);
    }

    private void generateDataListGrid(List<UserInfo> community) {
        gridAdapter = new GridViewAdapter(this, community, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.e("ItemClicked", "Item Clicked at Position " + position);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(communityPage, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(communityPage, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(communityPage, HomeScreen.class);
            startActivity(to_navigate);
        }
    }

    public void toggle(boolean toggle)
    {
        int visibility = 0;
        ImageView popup_quick_acces_image = (ImageView) popup_menu_button.findViewById(R.id.pollen_score_layout_imageview);
        if(toggle)
        {
            visibility = View.VISIBLE;
            popup_quick_acces_image.setImageResource(R.drawable.half_pollen);
            popup_menu.setVisibility(View.INVISIBLE);
            is_menu_popped = false;

        }
        else
        {
            visibility = View.INVISIBLE;
            popup_quick_acces_image.setImageResource(R.drawable.pollen);
            popup_menu.setVisibility(View.VISIBLE);
            is_menu_popped = true;
        }
        popup_menu_button.findViewById(R.id.pollen_score_layout_tv).setVisibility(visibility);
        community_page_title_tv.setVisibility(visibility);
        tabs_ll.setVisibility(visibility);
        bar_ll.setVisibility(visibility);
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if (event1 != null && event2 != null) {
                if (event1.getX() - event2.getX() > 150) {
                    Intent homePage = new Intent(communityPage, ProfilePage.class);
                    startActivity(homePage);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                } else {
                    return true;
                }
            }
            else {
                Log.e("Event is NULL", "NULL");
            }
            return false;
        }
    }

    public void setNotifications()
    {
        Call<List<QuestReport>> call = service.getAllQuestsInCommunity();
        call.enqueue(new Callback<List<QuestReport>>() {

            @Override
            public void onResponse(Call<List<QuestReport>> call, Response<List<QuestReport>> response) {
                if(response.isSuccess())
                {
                    progressDoalog.dismiss();
                    int quest_type;
                    final String user_name;
                    int user_butterfly_type_id;
                    final List<QuestReport> questReportList = response.body();

                    for (int i = 0; i < questReportList.size(); i++)
                    {
                        holder.setQuest_type(questReportList.get(i).getQuest_type_id());

                        getUserInfo(questReportList.get(i).getUser_id());
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Integer> quest_type_ids = holder.getQuest_type();
                            List<String> usernames = holder.getUsername();
                            List<Integer> user_butterfly_types = holder.getUser_butterfly_id();

                            Log.e("List Size", "" + usernames.size());
                            Log.e("Report List", "" + questReportList.size());
                            Collections.reverse(quest_type_ids);
                            Collections.reverse(usernames);
                            Collections.reverse(user_butterfly_types);

                            generateDataListLinear(questReportList, quest_type_ids, usernames, user_butterfly_types);
                        }
                    },500);

                }
                else
                {
                    Toast.makeText(CommunityPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QuestReport>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(CommunityPage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserInfo(int user_id) {

        Call<UserInfo> call = service.getUserInfo(user_id);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {
                    String u_name = response.body().getUser_name();
                    if(u_name == null)
                    {
                        Log.e("NULLLLLLL", " USERNAME IS NOT AVAILABLE" );
                        Toast.makeText(CommunityPage.this, "User Name is not available", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("USERNAMES!!!!!!!!!!!!!", " " + u_name);
                    int u_b_id = response.body().getUser_current_butterfly();

                    holder.setUsername(u_name);
                    holder.setUser_butterfly_id(u_b_id);
                }
                else
                {
                    Toast.makeText(CommunityPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(CommunityPage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
