package com.example.aorora.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.aorora.CommunityPage;
import com.example.aorora.HolderCommunityPage;
import com.example.aorora.R;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.QuestReport;
import com.example.aorora.network.GetDataService;

import java.util.List;

public class NotificationListAdapter extends ArrayAdapter<QuestReport> implements View.OnClickListener
{
    private List<QuestReport> dataList;
    private Context myContext;
    List<Integer> quest_type_ids;
    List<String> usernames;
    List<Integer> user_butterfly_types;
    String[] accomplishment_description;
    GetDataService myService;

    //View lookup cache
    private static class ViewHolder
    {
        TextView txtTitle;
        TextView username_tv;
        TextView time_published;
        ImageView coverImage;
        ImageView like_button;
    }



    public NotificationListAdapter( Context context,List<QuestReport> dataList,
                                    List<Integer> quest_type_ids,
                                    List<String> usernames,
                                    List<Integer> user_butterfly_types,
                                    String[] accomplishment_description
    ){
        super( context, R.layout.custom_comments_row, R.id.CommentTextView, dataList);
        this.myContext = context;
        this.dataList = dataList;
        this.quest_type_ids = quest_type_ids;
        this.usernames = usernames;
        this.user_butterfly_types = user_butterfly_types;
        this.accomplishment_description = accomplishment_description;
    }


    @Override
    public void onClick( View v )
    {

    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent)
    {
        //Get specific data for the item at this position
        QuestReport myReport = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (itemView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            itemView = inflater.inflate(R.layout.custom_comments_row, parent, false);
            viewHolder.txtTitle = itemView.findViewById(R.id.notification_content_tv);
            viewHolder.username_tv = itemView.findViewById(R.id.user_name_notification_tv);
            viewHolder.time_published = itemView.findViewById(R.id.time_published_tv);
            viewHolder.coverImage = itemView.findViewById(R.id.coverImage);
            viewHolder.like_button = itemView.findViewById(R.id.like_button);

            result=itemView;

            itemView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) itemView.getTag();
            result = itemView;
        }

        return result;
    }
}
