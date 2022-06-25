package com.example.app_book.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_book.Detail_LichHen;
import com.example.app_book.Model.Model_LichKhamDT;
import com.example.app_book.Model.Model_doctor;
import com.example.app_book.R;
import com.example.app_book.User_Dky_LichKhamDT;
import com.example.app_book.fragement_doctor.Fragment_MN_lichkham_DT;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class Adapter_LichkhamOfDT extends RecyclerView.Adapter<Adapter_LichkhamOfDT.Myholder> {
    Context context;
    String uid;
    List<Model_LichKhamDT> userList;
    int countcmt = 0;
    private DatabaseReference benhanh;
    private DatabaseReference user;
    public Adapter_LichkhamOfDT(Context context, List<Model_LichKhamDT> userList) {
        this.context = context;
        this.userList = userList;
        benhanh = FirebaseDatabase.getInstance().getReference("SoLuongĐKy");

    }
    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lichkham_doctor, parent,false);

        return new Adapter_LichkhamOfDT.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int i) {
        Model_LichKhamDT Model_LichKhamDT = userList.get(i);
        if (Model_LichKhamDT == null){
            return;
        }
        //get data
        uid = userList.get(i).getUid();
        String ghichu = userList.get(i).getGhichu();
        String ngaythang = userList.get(i).getNgaythang();
        String tinhtrang = userList.get(i).getTrangthai();
        String pid = userList.get(i).getMaLichKham();
        holder.ngaykham.setText(ngaythang);
        holder.ghichu.setText(ghichu);

        benhanh.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    countcmt = (int) snapshot.getChildrenCount();
                    holder.soluong.setText("Số lượng bệnh nhân đăng ký: " + Integer.toString(countcmt));
                }else{
                    holder.soluong.setText("Số lượng bệnh nhân đăng ký: " + "0");

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deklllichkham(pid);
            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, User_Dky_LichKhamDT.class);
                intent.putExtra("malichkham",pid);
                context.startActivity(intent);
            }
        });
        if(tinhtrang.equals("old")){
            holder.huy.setVisibility(View.GONE);
            holder.chitiet.setVisibility(View.GONE);
            holder.dakham.setVisibility(View.VISIBLE);
            holder.dakham.setText("Đã hoàn thành");
        }




    }

    private void deklllichkham(String pid) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Đang Xoá lịch khám...");
        pd.show();
        Query fquery = FirebaseDatabase.getInstance().getReference("Lichkham_Doctor").orderByChild("MaLichKham").equalTo(pid);
        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                }
                if(countcmt > 0){
                    delsoluongdky(pid);

                }

                Toast.makeText(context, "Xoá thành công.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Xoá thất bại.", Toast.LENGTH_SHORT).show();
                ProgressDialog pd = new ProgressDialog(context);
                pd.dismiss();
            }
        });
    }






    private void delsoluongdky(String pid) {
        Query fquery1 = FirebaseDatabase.getInstance().getReference("LichHenchitiet").orderByChild("MaLichKhamofDT").equalTo(pid);
        fquery1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query fquer2= FirebaseDatabase.getInstance().getReference("SoLuongĐKy").child(pid);
        fquer2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){


                    String idtake = ""+ ds.child("iduser").getValue().toString();
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("idsender", uid);
                    hashMap.put("idTake", idtake);
                    hashMap.put("Ma_Noti", timestamp);
                    hashMap.put("title", "Thông báo hủy lịch hẹn ");
                    hashMap.put("ippost", " ");
                    hashMap.put("type", "1");
                    hashMap.put("content", "Vừa có một lịch hẹn bị hủy");
                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Notification");
                    ref3.child(timestamp).setValue(hashMap);

                    ds.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        Button huy, chitiet;
        TextView ngaykham, ghichu, soluong , dakham;

               public Myholder(@NonNull View itemView) {
                   super(itemView);
                   huy = itemView.findViewById(R.id.huylichkham);
                   chitiet = itemView.findViewById(R.id.follow);
                   dakham = itemView.findViewById(R.id.follow2);
                   ngaykham = itemView.findViewById(R.id.ngaykham);
                   ghichu = itemView.findViewById(R.id.ghichu);
                   soluong = itemView.findViewById(R.id.songuoidatlich);
               }
            }

}
