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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class Adapter_ChatList extends RecyclerView.Adapter<Adapter_ChatList.Myholder>{
     Context context;
     List<Model_UserChat> userList;
    private DatabaseReference user;
     private HashMap<String, String> lastMessageMap;
     String myUid;

    public Adapter_ChatList(Context context, List<Model_UserChat> userList) {
        this.context = context;
        this.userList = userList;
        lastMessageMap = new HashMap<>();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference("Users");
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_chat, parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int i) {
        String hisUID = userList.get(i).getUid();
        String userimage = userList.get(i).getAvatar();
        String username = userList.get(i).getName();
        String lastMessage = lastMessageMap.get(hisUID);
        holder.namechat.setText(username);

        if (lastMessage==null || lastMessage.equals("default")){
            holder.lastmessagechat.setVisibility(View.GONE);
        }else{
            holder.lastmessagechat.setVisibility(View.VISIBLE);
            holder.lastmessagechat.setText(lastMessage);
        }

        try {
            Picasso.get().load(userimage)
                    .placeholder(R.drawable.ic_face)
                    .into(holder.avatar);
        }catch (Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });
      checkOnline(holder, i);

    }




    private void checkOnline(Myholder myholder ,int i) {
        String online = userList.get(i).getOnlineStatus();
        if (online.equals("Online")){
                myholder.Onlinestatus.setImageResource(R.drawable.bg_online);
        }else{
            myholder.Onlinestatus.setImageResource(R.drawable.bg_offline);
        }
    }
    public void setLastMessageMap(String userId, String lastMessage){
        lastMessageMap.put(userId, lastMessage);
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    class Myholder extends RecyclerView.ViewHolder{
        ImageView avatar, Onlinestatus;
        TextView namechat, lastmessagechat;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_useruc);
            Onlinestatus = itemView.findViewById(R.id.notiOnline);
            namechat = itemView.findViewById(R.id.nametv_useruc);
            lastmessagechat = itemView.findViewById(R.id.lasmessgerchat);
        }
    }
}
