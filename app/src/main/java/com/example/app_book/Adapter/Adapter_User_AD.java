package com.example.app_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Detail_User_AD;
import com.example.app_book.Details_Lichkham_Doctor;
import com.example.app_book.Model.Model_User;
import com.example.app_book.Model.Model_doctor;
import com.example.app_book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_User_AD extends RecyclerView.Adapter<Adapter_User_AD.Myholder> {
    Context context;
    List<Model_User> userList;
    String myUid;
    private DatabaseReference benhanh;
    private DatabaseReference user;


    public Adapter_User_AD(Context context, List<Model_User> userList) {
        this.context = context;
        this.userList = userList;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }


    @NonNull
    @Override
    public Adapter_User_AD.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new Adapter_User_AD.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_AD.Myholder holder, int i) {
        Model_User model_doctor = userList.get(i);
        if (model_doctor == null) {
            return;
        }
        //get data
        String uid = userList.get(i).getUid();
        String userimage = userList.get(i).getAvatar();
        String username = userList.get(i).getName();
        String phone = userList.get(i).getSTD();
        String diachi = userList.get(i).getDiachi();
        holder.nametv.setText(username);
        holder.phone_user.setText(phone);
        holder.chuyenkhoa.setVisibility(View.GONE);
        holder.diachi.setText(diachi);
        holder.theloai.setVisibility(View.GONE);

        try {
            Picasso.get().load(userimage)
                    .placeholder(R.drawable.ic_face)
                    .into(holder.avatar);
        } catch (Exception e) {

        }
        holder.follow.setText("chi tiết");
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_User_AD.class);
                intent.putExtra("uid", uid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }

        return 0;
    }


    class Myholder extends RecyclerView.ViewHolder {
        ImageView avatar, block_us;
        Button follow;
        TextView nametv, phone_user, chuyenkhoa, diachi, theloai;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_user);
            nametv = itemView.findViewById(R.id.nametv_user);
            phone_user = itemView.findViewById(R.id.phone_user);
            chuyenkhoa = itemView.findViewById(R.id.Chuyenkhoan);
            theloai = itemView.findViewById(R.id.theloai_user);
            diachi = itemView.findViewById(R.id.diachi_user);
            follow = itemView.findViewById(R.id.follow);
        }
    }
}
