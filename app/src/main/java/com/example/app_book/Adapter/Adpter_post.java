package com.example.app_book.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Detail_Post;
import com.example.app_book.Model.Mode_Post;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.R;
import com.example.app_book.User_Dky_LichKhamDT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Adpter_post extends RecyclerView.Adapter<Adpter_post.Myholder> {
    Context context;
    List<Mode_Post> userList;
    int countcmt = 0;
    private DatabaseReference benhanh;
    private DatabaseReference user;
    public Adpter_post(Context context, List<Mode_Post> userList) {
        this.context = context;
        this.userList = userList;
        benhanh = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");

    }
    @NonNull
    @Override
    public Adpter_post.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);

        return new Adpter_post.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpter_post.Myholder holder, int i) {
        Mode_Post Model_LichKhamDT = userList.get(i);
        if (Model_LichKhamDT == null){
            return;
        }
        //get data

        holder.title.setText(userList.get(i).getTitle());
        holder.content.setText(userList.get(i).getNoidung());

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(userList.get(i).getTime()));
        String datetime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
        holder.time.setText(datetime);
        try {
            Picasso.get().load(userList.get(i).getImage()).placeholder(R.drawable.ic_image).into(holder.hehe);
        }catch (Exception e){

        }
        holder.gfdgfdg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Post.class);
                intent.putExtra("maposst",userList.get(i).getTime());
                intent.putExtra("iddoctor",userList.get(i).getIddoctor());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (userList != null){
            return userList.size();
        }

        return 0;
    }

    class Myholder extends RecyclerView.ViewHolder {
        TextView title, content, time ;
        CardView gfdgfdg;
        ImageView hehe;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.nametv_user);
            content = itemView.findViewById(R.id.theloai_user);
            time = itemView.findViewById(R.id.diachi_user);
            hehe = itemView.findViewById(R.id.avatar_user);
            gfdgfdg = itemView.findViewById(R.id.kkkl);

        }
    }

}

