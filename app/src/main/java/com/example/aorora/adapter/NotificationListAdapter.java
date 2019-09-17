package com.example.aorora.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import com.example.aorora.CommunityPage;
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
}
