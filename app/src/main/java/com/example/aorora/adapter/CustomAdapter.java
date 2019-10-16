package com.example.aorora.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.CommunityPage;
import com.example.aorora.R;
import com.example.aorora.interfaces.OnItemClickListener;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.QuestReport;
import com.example.aorora.interfaces.OnLikeListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.example.aorora.model.RetroPhoto;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.RetrofitClientInstance;
import com.example.aorora.network.NetworkCalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aorora.MainActivity.user_info;
import static java.lang.Math.min;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<QuestReport> dataList;
    private Context context;
    List<Integer> quest_type_ids;
    List<String> usernames;
    List<Integer> user_butterfly_types;
    String[] accomplishment_description;
    GetDataService myService;
    private int indexOfClickedItem = -1;
    //Need to determine which button was pressed inside the specific RecyclerView
    private OnItemClickListener myLikeListener;

/*
    public interface OnItemClickListener
    {
        void onItemClick(int position );
    }
*/
    public CustomAdapter(Context context,List<QuestReport> dataList,
                         List<Integer> quest_type_ids,
                         List<String> usernames,
                         List<Integer> user_butterfly_types,
                         String[] accomplishment_description,
                         OnItemClickListener listener
                         ){
        this.context = context;
        this.dataList = dataList;
        this.quest_type_ids = quest_type_ids;
        this.usernames = usernames;
        this.user_butterfly_types = user_butterfly_types;
        this.accomplishment_description = accomplishment_description;
        myLikeListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTitle;
        TextView username_tv;
        TextView time_published;
        ImageView coverImage;
        ImageView like_button;
        //RelativeLayout rv_rel_layout_like_button;

        public CustomViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.notification_content_tv);
            username_tv = itemView.findViewById(R.id.user_name_notification_tv);
            time_published = itemView.findViewById(R.id.time_published_tv);
            coverImage = itemView.findViewById(R.id.coverImage);
            like_button = itemView.findViewById(R.id.like_button);
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

                               int u_name, u_like;
                               @Override
                               public void onResponse(Call<List<ButterflyLike>> call, Response<List<ButterflyLike>> response)
                               {
                                   boolean isLiked = false;
                                   int likeCount = 0;

                                   if( like_button.getDrawable().equals(R.drawable.heart_filled))
                                   {
                                       isLiked = true;
                                   }

                                   if( response.isSuccess() )
                                   {
                                       final List<ButterflyLike> likeList = response.body();

                                       u_name = response.body().get(likeCount).getUser_id();

                                       Log.e("USERNAME", " " + u_name);

                                       for( ButterflyLike curLike : likeList)
                                       {
                                           for( int questReportIter = 0; !isLiked && questReportIter < dataList.size(); questReportIter++)
                                           {
                                               //Check to see if user id and the quest report id are found together
                                               if( !isLiked && ( (curLike.getUser_id() == myUserId)))
                                               {
                                                   if( curLike.getQuestReportId() == dataList.get(questReportIter).getQuest_report_id())
                                                   {
                                                       isLiked = true;
                                                       like_button.setImageResource(R.drawable.heart_filled);
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
                               }
                           }
            );
        }



    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_comments_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder( view, myLikeListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
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
        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLikeListener.onItemClick(v, position);
                //Log.e("ItemClicked", "Item Clicked at Position " + position);
                /*indexOfClickedItem = position;
                notifyItemChanged( position );
                holder.bindItem( position );*/

            }
        });


    }



    @Override
    public int getItemCount() {

        return min(dataList.size(),20);
    }

    /**
     *
     * @param position
     * @return the quest report ID at the item's position.
     */
    public int getItemQuestId( int position )
    {
        return dataList.get( dataList.size()-position-1 ).quest_report_id;
    }

    /**
     * Purpose: Will set the like status of the notification in question
     * Algorithm:
     * @param position : The index of the specific item that in the notification layout.
     * @param status  : Tells us whether the specific notification needs a like (false)
     *                  or needs to remove one (true).
     */
    public void setLikeStatus( int position, boolean status, int secondary_id )
    {
        final int myUserId = MainActivity.user_info.getUser_id();
     /* Might not be needed to create and remove likes.
        ---- Keeping it in just in case so I don't have to rewrite.

        Call<List<ButterflyLike>> myCall = myService.getAllLikes();

        myCall.enqueue(new Callback<List<ButterflyLike>>()
        {
            @Override
            public void onResponse(Call<List<ButterflyLike>> call, Response<List<ButterflyLike>> response)
            {

            }

            @Override
            public void onFailure(Call<List<ButterflyLike>> call, Throwable t)
            {

            }
        });

      */

     //if true, then remove like from DB and update notification at said position
        //else, add like to DB and update notification at said location
     if( !status )
     {
         NetworkCalls.createLike(myUserId, secondary_id);
         Log.i("LIKE CREATED", " "+myUserId+", "+secondary_id);
     }
     else
     {
         if(secondary_id >= 0)
         {
             NetworkCalls.removeLike(secondary_id);
             Log.i("LIKE REMOVED", "" + secondary_id);
         }
         else
         {
             Log.e("NO LIKE", " Like could not be removed (incorrect index)");
         }
     }

    }

    /**
     * Potentially needs a getViewAtPosition()
     */
}
