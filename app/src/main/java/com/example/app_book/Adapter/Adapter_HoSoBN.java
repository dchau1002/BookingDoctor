package com.example.app_book.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Choose_HoSoBenhNhan;
import com.example.app_book.Dat_LichKhamchitiet;
import com.example.app_book.Details_Lichkham_Doctor;
import com.example.app_book.Model.Model_HosoBN;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adapter_HoSoBN extends RecyclerView.Adapter<Adapter_HoSoBN.Myholder> {
    public static String tenhoso ="null", Mahoso;
    Context context;
    List<Model_HosoBN> userList;
    public Adapter_HoSoBN(Context context, List<Model_HosoBN> userList) {
        this.context = context;
        this.userList = userList;

    }
    @NonNull
    @Override
    public Adapter_HoSoBN.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hosobennhan, parent,false);

        return new Adapter_HoSoBN.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_HoSoBN.Myholder holder, int i) {
        Model_HosoBN model_HosoBN = userList.get(i);
        if (model_HosoBN == null){
            return;
        }
        //get data
        String uid = userList.get(i).getUid();
        String ghichu = userList.get(i).getTenHoSo();
        String mahoso = userList.get(i).getMaHoSo();
        holder.nametv.setText(ghichu);
        holder.kkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.kkk.setBackgroundResource(R.drawable.skip_button1);
                tenhoso = ghichu;
                Mahoso = mahoso;
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
        RelativeLayout kkk;
        TextView nametv;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            kkk = itemView.findViewById(R.id.bkkasa);
            nametv = itemView.findViewById(R.id.nametv_useruc);

        }

    }
}