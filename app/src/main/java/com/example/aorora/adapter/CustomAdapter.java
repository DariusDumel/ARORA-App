package com.example.aorora.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.QuestReport;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.min;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<QuestReport> dataList;
    private Context context;
    List<Integer> quest_type_ids;
    List<String> usernames;
    List<Integer> user_butterfly_types;
    String[] accomplishment_description;
    GetDataService myService;
    private int indexOfClickedItem = 0;
    //Need to determine which button was pressed inside the specific RecyclerView
    private OnItemClickListener myLikeListener;


    public interface OnItemClickListener
    {
        void onItemClick(int position );
    }

    public CustomAdapter(Context context,List<QuestReport> dataList,
                         List<Integer> quest_type_ids,
                         List<String> usernames,
                         List<Integer> user_butterfly_types,
                         String[] accomplishment_description
                         ){
        this.context = context;
        this.dataList = dataList;
        this.quest_type_ids = quest_type_ids;
        this.usernames = usernames;
        this.user_butterfly_types = user_butterfly_types;
        this.accomplishment_description = accomplishment_description;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTitle;
        TextView username_tv;
        TextView time_published;
        ImageView coverImage;
        ImageView like_button;
        boolean isLiked;
        int questPlacement;

        public CustomViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.notification_content_tv);
            username_tv = itemView.findViewById(R.id.user_name_notification_tv);
            time_published = itemView.findViewById(R.id.time_published_tv);
            coverImage = itemView.findViewById(R.id.coverImage);
            like_button = itemView.findViewById(R.id.like_button);

            if( like_button.getDrawable().equals(R.drawable.heart_filled))
            {
                isLiked = true;
            }
            else
            {
                isLiked = false;
            }

            questPlacement = -1;
            myService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        }

        /**
         * Purpose: Will like or un-like the notification the user has specified
         * Algorithm: Takes in the user_id as well as the whole list of ButterflyLikes currently in the DB,
         *            as well as the current amount of QuestReports in order to determine if there is a
         *            server-side and client-side match of a like
         * @param pos : not needed as of this moment, may remove
         */
        void bindItem( int pos )
        {
            final int myUserId = MainActivity.user_info.getUser_id();
            Call<List<ButterflyLike>> myCall = myService.getAllLikes();
            myCall.enqueue(new Callback<List<ButterflyLike>>()
                           {

                               @Override
                               public void onResponse(Call<List<ButterflyLike>> call, Response<List<ButterflyLike>> response)
                               {

                                   int questIter;

                                   if( like_button.getDrawable().equals(R.drawable.heart_filled))
                                   {
                                       isLiked = true;
                                   }

                                   if( response.isSuccess() )
                                   {
                                       final List<ButterflyLike> likeList = response.body();

                                       //Goes through entire list of ButterflyLikes
                                       for( ButterflyLike curLike : likeList)
                                       {
                                           for(questIter = 0; questIter < dataList.(); questIter++)
                                           {
                                               //Check to see if user id and the quest report id are found together
                                               if( !isLiked )
                                               {
                                                   if( ( (curLike.getUser_id() == myUserId) && curLike.getQuestReportId() == dataList.get(questIter).getQuest_report_id()))
                                                   {
                                                       isLiked = true;
                                                       like_button.setImageResource(R.drawable.heart_filled);
                                                       questPlacement = questIter;
                                                   }
                                               }
                                               else
                                               {
                                                   isLiked = false;
                                                   like_button.setImageResource(R.drawable.heart_unfilled);
                                               }
                                           }
                                       }

                                   }

                               }

                               @Override
                               public void onFailure(Call<List<ButterflyLike>> call, Throwable t)
                               {
                                   Log.e("ERROR: ", "There was a problem in receiving likes.");
                               }
                           }
            );


            if( isLiked && questPlacement != -1 )
            {

                NetworkCalls.createLike( myUserId, dataList.get(questPlacement).getQuest_report_id(), like_button.getContext());
            }
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_comments_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder( view, myLikeListener);
        return holder;
    }


/*-----------Dont
    @Override
    public void onBindViewHolder( final CustomViewHolder holder, final int position, List<Object> payloads)
    {
        String desc = accomplishment_description[quest_type_ids.get(position)-1];
        holder.txtTitle.setText(desc);
        holder.username_tv.setText(usernames.get(position));
        int butterfly_id = user_butterfly_types.get(position);
        Log.e("Butterfly ID : " , " " + butterfly_id);
        switch (butterfly_id){
            case 0:
                holder.coverImage.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                holder.coverImage.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                holder.coverImage.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                holder.coverImage.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                holder.coverImage.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                holder.coverImage.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                holder.coverImage.setImageResource(R.drawable.purple_butterfly_image);
                break;
        }

        //holder.bindItem( position );

        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexOfClickedItem = position;
                notifyItemChanged( position );
                holder.bindItem( position );
            }
        });

    }
//*/
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        String desc = accomplishment_description[quest_type_ids.get(position)-1];
        //holder.setIsRecyclable(false);
        holder.txtTitle.setText(desc);
        holder.username_tv.setText(usernames.get(position));
        int butterfly_id = user_butterfly_types.get(position);
        Log.e("Butterfly ID : " , " " + butterfly_id);
        switch (butterfly_id){
            case 0:
                holder.coverImage.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                holder.coverImage.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                holder.coverImage.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                holder.coverImage.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                holder.coverImage.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                holder.coverImage.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                holder.coverImage.setImageResource(R.drawable.purple_butterfly_image);
                break;
        }

        //holder.bindItem( position );

        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexOfClickedItem = position;
                notifyItemChanged( position );
                holder.bindItem( position );
            }
        });


    }



    @Override
    public int getItemCount() {

        return min(dataList.size(),20);
    }

    /**
     * Potentially needs a getViewAtPosition()
     */
}
