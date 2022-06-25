package com.example.app_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app_book.ChatActivity;
import com.example.app_book.Model.Model_UserChat;
import com.example.app_book.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_userOnline extends RecyclerView.Adapter<Adapter_userOnline.Myholder> {
    Context context;
    List<Model_UserChat> userList;

    public Adapter_userOnline(Context context, List<Model_UserChat> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_useronline, parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int i) {
        String hisUID = userList.get(i).getUid();
        String userimage = userList.get(i).getAvatar();

        String online = userList.get(i).getOnlineStatus();
        if (online.equals("Online")){
            holder.Onlinestatus.setImageResource(R.drawable.bg_online);
        }else{
            holder.Onlinestatus.setImageResource(R.drawable.bg_offline);
        }

        try {
            Picasso.get().load(userimage)
                    .placeholder(R.drawable.ic_face)
                    .into(holder.avatar);
        }catch (Exception e){

        }
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()  {
        return userList.size();
    }

    class Myholder extends RecyclerView.ViewHolder{
        ImageView avatar, Onlinestatus;
        TextView namechat, lastmessagechat;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_useruc);
            Onlinestatus = itemView.findViewById(R.id.notiOnline);
        }
    }
}
