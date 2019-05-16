package com.example.aorora;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.aorora.interfaces.OnItemClickListener;
import com.example.aorora.model.RetroPhoto;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.RetrofitClientInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class CommunityPage extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnClickListener {

    private com.example.aorora.adapter.CustomAdapter linearAdapter;
    private com.example.aorora.adapter.GridViewAdapter gridAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    Button friends_tab_button;
    Button social_tab_button;
    Button notifications_tab_button;
    Context communityPage;
    GestureDetector gestureDetector;
    ImageView friends_underline;
    ImageView social_underline;
    ImageView notifications_underline;
    TextView community_page_title_tv;
    LinearLayout tabs_ll;
    LinearLayout bar_ll;
    ImageButton popup_menu_button;
    boolean is_menu_popped;
    public View popup_menu;
    TextView minimized_quick_view_pollens_count_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);


        communityPage = this;
        is_menu_popped = false;
        progressDoalog = new ProgressDialog(communityPage);

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        community_button_bottombar.setImageResource(R.drawable.community_button_filled);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);

        popup_menu = (View) findViewById(R.id.include_popup_quick_access_menu_community_page);
        popup_menu_button = (ImageButton) findViewById(R.id.popup_quick_access_community_page);
        minimized_quick_view_pollens_count_tv = (TextView) findViewById(R.id.minimized_quick_view_pollens_count_tv);

        popup_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(is_menu_popped);
            }
        });
        friends_tab_button =  (Button) findViewById(R.id.friends_tabs_button);
        social_tab_button = (Button) findViewById(R.id.social_tabs_button);
        notifications_tab_button = (Button) findViewById(R.id.notifications_tabs_button);

        community_page_title_tv = (TextView) findViewById(R.id.community_string_tv);
        tabs_ll = (LinearLayout) findViewById(R.id.community_page_ll);
        bar_ll = (LinearLayout) findViewById(R.id.community_page_line_ll);

        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);

        friends_underline = findViewById(R.id.underline_friends);
        social_underline = findViewById(R.id.underline_social);
        notifications_underline = findViewById(R.id.underline_notifications);

        gestureDetector = new GestureDetector(communityPage, CommunityPage.this);

        popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_navigate = new Intent(communityPage, DailyQuestPage.class);
                startActivity(to_navigate);
            }
        });

        /*Create handle for the RetrofitInstance interface*/

        friends_tab_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {

                friends_underline.setVisibility(View.VISIBLE);
                social_underline.setVisibility(View.INVISIBLE);
                notifications_underline.setVisibility(View.INVISIBLE);
                progressDoalog.setMessage("Friends Loading....");
                progressDoalog.show();

                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {

                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                        progressDoalog.dismiss();
                        generateDataListGrid(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(CommunityPage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        social_tab_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                friends_underline.setVisibility(View.INVISIBLE);
                social_underline.setVisibility(View.VISIBLE);
                notifications_underline.setVisibility(View.INVISIBLE);
                progressDoalog.setMessage("Social Feed Loading....");
                progressDoalog.show();

                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {

                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                        progressDoalog.dismiss();
                        generateDataListLinear(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(CommunityPage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        notifications_tab_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {

                friends_underline.setVisibility(View.INVISIBLE);
                social_underline.setVisibility(View.INVISIBLE);
                notifications_underline.setVisibility(View.VISIBLE);

                progressDoalog.setMessage("Notifications Loading....");
                progressDoalog.show();

                com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {

                    @Override
                    public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                        progressDoalog.dismiss();
                        generateDataListLinear(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(CommunityPage.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if(getIntent().hasExtra("notification"))
        {
            notifications_tab_button.performClick();

        }
        else
        {
            friends_tab_button.performClick();

        }
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataListLinear(List<com.example.aorora.model.RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        linearAdapter = new com.example.aorora.adapter.CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CommunityPage.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(linearAdapter);
    }

    private void generateDataListGrid(List<com.example.aorora.model.RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        gridAdapter = new com.example.aorora.adapter.GridViewAdapter(this, photoList, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // do something in future
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gridAdapter);
    }

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
    public boolean onFling (MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y)
    {
        if (motionEvent1.getX() - motionEvent2.getX() > 50) {
            Intent homePage = new Intent(communityPage, HomeScreen.class);
            startActivity(homePage);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            return true;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
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
        if(toggle)
        {
            visibility = View.VISIBLE;
            popup_menu.setVisibility(View.INVISIBLE);
            is_menu_popped = false;

        }
        else
        {
            visibility = View.INVISIBLE;
            popup_menu.setVisibility(View.VISIBLE);
            is_menu_popped = true;
        }
        minimized_quick_view_pollens_count_tv.setVisibility(visibility);
        community_page_title_tv.setVisibility(visibility);
        tabs_ll.setVisibility(visibility);
        bar_ll.setVisibility(visibility);
    }
}
