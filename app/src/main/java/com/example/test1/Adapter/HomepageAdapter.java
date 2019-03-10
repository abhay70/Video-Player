package com.example.test1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test1.Activity.VideoPlayerActivity;
import com.example.test1.Database.ChatDBHelper;
import com.example.test1.Database.ChatDBUtility;
import com.example.test1.Model.DataResponse;
import com.example.test1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomepageAdapter extends  RecyclerView.Adapter<HomepageAdapter.MyViewHolder>  {

    private Context context;
    LayoutInflater vi;
    ArrayList<DataResponse> dataResponse;


    ChatDBHelper chatDBHelper;
    ChatDBUtility chatDBUtility;
    
    int layout_id;

    public HomepageAdapter(Context context, ArrayList<DataResponse> dataResponse,int layout_id) {
        this.dataResponse = dataResponse;
        this.context = context;
        this.layout_id=layout_id;

        vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        chatDBUtility = new ChatDBUtility();
        chatDBHelper = chatDBUtility.CreateChatDB(context);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView3,textView;
        ImageView imageView;



        public MyViewHolder(View itemView) {
            super(itemView);


            this.textView3 = (TextView) itemView.findViewById(R.id.textView3);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.imageView=(ImageView)itemView.findViewById(R.id.imageView);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = null;
        if(layout_id==1)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_homepage, parent, false);
        }else if(layout_id==2)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_video_player, parent, false);
        }
   



        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return dataResponse.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextView title = holder.textView;
        TextView description = holder.textView3;
        ImageView image = holder.imageView;


        holder.setIsRecyclable(false);


        title.setText(dataResponse.get(position).getTitle());
        description.setText(dataResponse.get(position).getDescription());
        Picasso.get().load(dataResponse.get(position).getThumb()).fit().into(image);



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("id",dataResponse.get(position).getId());
                intent.putExtra("url",dataResponse.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        //TextView childName = holder.childName;



    }

}
