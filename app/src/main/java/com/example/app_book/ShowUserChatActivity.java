package com.example.app_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_book.Adapter.Adapter_ChatList;
import com.example.app_book.Adapter.Adapter_userOnline;
import com.example.app_book.Model.Model_ChatList;
import com.example.app_book.Model.Model_UserChat;
import com.example.app_book.Model.Model_chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowUserChatActivity extends AppCompatActivity {

    private RecyclerView rcv_user_chat1;
    private Adapter_ChatList adapterChatList;
    private List<Model_ChatList> modelChatLists;
    private List<Model_UserChat> userList;
    private List<Model_UserChat> userList2;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    private FirebaseUser user;
    String MyUid;
    String mUID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    RecyclerView recycleruseronline;
    Adapter_userOnline adapterUserOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_chat);

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("User");
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        user = firebaseAuth.getCurrentUser();
//        MyUid = user.getUid();

        ImageButton backs = findViewById(R.id.backc);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        modelChatLists = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();
        reference = FirebaseDatabase.getInstance().getReference("ChatsList").child(MyUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelChatLists.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Model_ChatList chatList = ds.getValue(Model_ChatList.class);
                    modelChatLists.add(chatList);
                }
                 loadChats();
                 loadChatsOnline();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        final Query ref = databaseReference.orderByChild("uid").equalTo(MyUid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String image = ""+ ds.child("avatar").getValue();
                    ImageView im = findViewById(R.id.avatar_chatss);

                    try {
                        Picasso.get().load(image).into(im);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.hi).into(im);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void loadChatsOnline() {
        userList = new ArrayList<>();
        recycleruseronline = findViewById(R.id.rcv_useronline);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowUserChatActivity.this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recycleruseronline.setLayoutManager(layoutManager);


        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Model_UserChat model_user = ds.getValue(Model_UserChat.class);
                    for (Model_ChatList chatlist: modelChatLists){
                        if (model_user.getUid() != null && model_user.getUid().equals(chatlist.getId())){
                            if (model_user.getOnlineStatus().equals("Online")){
                                userList.add(model_user);
                                break;
                            }
                        }
                    }
                    adapterUserOnline = new Adapter_userOnline(ShowUserChatActivity.this, userList);
                    recycleruseronline.setAdapter(adapterUserOnline);
                    adapterUserOnline.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadChats() {
        userList2 = new ArrayList<>();

        rcv_user_chat1 = findViewById(R.id.rcv_user_chat1);
        LinearLayoutManager layoutMan = new LinearLayoutManager(ShowUserChatActivity.this);
        layoutMan.setOrientation(RecyclerView.VERTICAL);
        layoutMan.setStackFromEnd(true);
        layoutMan.setReverseLayout(true);
        rcv_user_chat1.setLayoutManager(layoutMan);

         reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList2.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Model_UserChat modelus = ds.getValue(Model_UserChat.class);
                    for (Model_ChatList chatlist: modelChatLists){
                        if (modelus.getUid() != null && modelus.getUid().equals(chatlist.getId())){
                            userList2.add(modelus);
                            break;
                        }
                    }
                    adapterChatList = new Adapter_ChatList(ShowUserChatActivity.this, userList2);
                    rcv_user_chat1.setAdapter(adapterChatList);
                    for (int i = 0; i < userList2.size(); i++){
                        lastMessage(userList2.get(i).getUid());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void lastMessage(String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String theLastMessage ="default";
                for (DataSnapshot ds: snapshot.getChildren()){
                    Model_chat chat = ds.getValue(Model_chat.class);
                    if (chat ==null){
                        continue;
                    }
                    String sender = chat.getSender();
                    String receiver = chat.getReceiver();
                    if (sender == null || receiver == null){
                        continue;
                    }
                    if (chat.getReceiver().equals(MyUid) &&
                            chat.getSender().equals(userid) || chat.getReceiver().equals(userid)
                            && chat.getSender().equals(MyUid)){
                        if (chat.getType().equals("image")){
                            theLastMessage = "Đã gửi một hình ảnh.";
                        }else{
                            theLastMessage = chat.getMessage();
                        }
                    }
                }
                adapterChatList.setLastMessageMap(userid, theLastMessage);
                adapterChatList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        checkStatusOnline(timestamp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStatusOnline("Online");

    }

    private void checkStatusOnline(String status){
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users").child(MyUid);
        HashMap<String, Object> hashMapp = new HashMap<>();
        hashMapp.put("OnlineStatus", status);
        reference.updateChildren(hashMapp);
     }

    @Override
    protected void onStart() {
        checkUserStatus();
        checkStatusOnline("Online");
        super.onStart();

    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            MyUid = user.getUid();

        }else{
            startActivity(new Intent(ShowUserChatActivity.this, PharmacyActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}