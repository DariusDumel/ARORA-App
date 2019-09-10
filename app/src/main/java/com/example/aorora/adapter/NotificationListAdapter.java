package com.example.aorora.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aorora.CommunityPage;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.QuestReport;
import com.example.aorora.network.GetDataService;

import java.util.List;

public class NotificationListAdapter //extends ButterflyLike implements View.OnClickListener
{
    private List<QuestReport> dataList;
    private Context myContext;
    List<Integer> quest_type_ids;
    List<String> usernames;
    List<Integer> user_butterfly_types;
    String[] accomplishment_description;
    GetDataService myService;


}
