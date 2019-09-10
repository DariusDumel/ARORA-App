package com.example.aorora.network;

import android.content.Context;
import android.content.Intent;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.aorora.CommunityPage;
import com.example.aorora.MainActivity;
import com.example.aorora.ProfilePage;
import com.example.aorora.SurveyPage;
import com.example.aorora.model.Butterfly;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.DailyTask;
import com.example.aorora.model.DailyTaskReturn;
import com.example.aorora.model.MoodReportIdReturn;
import com.example.aorora.model.QuesrtReportCreateReturn;
import com.example.aorora.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aorora.MainActivity.user_info;

public class NetworkCalls {

    public static GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    public static void updateUserCurrentButterfly(int user_id, int user_butterfly_id, final Context context) {

        Call call = service.updateUserCurrentButterfly(user_id, user_butterfly_id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {
                    //Toast.makeText(context, "butterfly Id Updated Successfuly", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateUserCurrentPoints(int user_id, int user_pollen, final Context context) {

        Call call = service.updateUserPollen(user_id, user_pollen);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {
                    //Toast.makeText(context, " POLLEN UPDATED Updated Successfuly", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getUserInfo(int user_id, final Context context)
    {
        Call<UserInfo> call = service.getUserInfo(user_id);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {
                    user_info = response.body();
                    //Toast.makeText(context, "User Info Gathered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void createMoodReport(int user_id, int q1_response, int q2_response, final Context context)
    {
        Call<MoodReportIdReturn> call = service.createMoodReport(user_id,q1_response,q2_response);
        call.enqueue(new Callback<MoodReportIdReturn>() {
            @Override
            public void onResponse(Call<MoodReportIdReturn> call, Response<MoodReportIdReturn> response) {
                if(response.isSuccess())
                //response.body().getUsername()
                {

                    //Toast.makeText(context, "Mood Report Created", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MoodReportIdReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getDailyTaskOfUser(int user_id, final Context context)
    {
        Call<DailyTask> call = service.getDailyTask(user_id);
        call.enqueue(new Callback<DailyTask> () {

            @Override
            public void onResponse(Call<DailyTask>  call, Response<DailyTask> response) {
                //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                MainActivity.daily_task = response.body();
            }

            @Override
            public void onFailure(Call<DailyTask>  call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateDailyTaskM1(int user_id, int update, final Context context)
    {

        Call<DailyTaskReturn> call = service.updateDailyTaskM1(user_id,user_id,update);
        call.enqueue(new Callback<DailyTaskReturn>() {
            @Override
            public void onResponse(Call<DailyTaskReturn> call, Response<DailyTaskReturn> response) {
                //Toast.makeText(context, "M1 Achieved: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<DailyTaskReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateDailyTaskM2(int user_id, int update, final Context context)
    {

        Call<DailyTaskReturn> call = service.updateDailyTaskM2(user_id,user_id,update);
        call.enqueue(new Callback<DailyTaskReturn>() {
            @Override
            public void onResponse(Call<DailyTaskReturn> call, Response<DailyTaskReturn> response) {
                //Toast.makeText(context, "M1 Achieved: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<DailyTaskReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateDailyTaskM3(int user_id, int update, final Context context)
    {

        Call<DailyTaskReturn> call = service.updateDailyTaskM3(user_id,user_id,update);
        call.enqueue(new Callback<DailyTaskReturn>() {
            @Override
            public void onResponse(Call<DailyTaskReturn> call, Response<DailyTaskReturn> response) {
                //Toast.makeText(context, "M1 Achieved: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<DailyTaskReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void createQuestReport(int quest_id, int user_id, final Context context)
    {
        Call<QuesrtReportCreateReturn> call = service.createQuestReport(quest_id,user_id);
        call.enqueue(new Callback<QuesrtReportCreateReturn>() {
            @Override
            public void onResponse(Call<QuesrtReportCreateReturn> call, Response<QuesrtReportCreateReturn> response) {
                //Toast.makeText(context, "Quest Report Created ID: " + response.body(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<QuesrtReportCreateReturn> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * To be used for when the user hits the like button on a notification
     * @param user_id
     * @param quest_id
     * @param context
     */
    public static void createLike( int user_id, int quest_id , final Context context)
    {

        Call<ButterflyLike> call = service.createLike( user_id, quest_id);
    }

    /**
     * To be used for when the user hits the like button on a notification
     * @param user_id
     * @param quest_id
     * @param context
     */
    public static void getLike( int butterfly_id, int user_id, int quest_id, final Context context)
    {

        //Call<ButterflyLike> call = service.createLike(butterfly_id, user_id, quest_id);
    }

    //public static void updateLike(int )
}
